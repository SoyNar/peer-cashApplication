package com.peercash.PeerCashproject.Service.Impl;
import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.RoleNotFoundException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserAlreadyExistException;
import com.peercash.PeerCashproject.Models.*;
import com.peercash.PeerCashproject.Repository.RoleRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IService.IUserService;
import com.peercash.PeerCashproject.Utils.Auditable;
import com.peercash.PeerCashproject.Utils.NotificationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationUtils notificationUtils;

    @Auditable(action = "REGISTER_USER", entity = "User")
    @Transactional
    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto requestDto,
                                            MultipartFile documentImage,
                                            MultipartFile bankStatementImage) {

        validateIfUserExists(requestDto);
        confirmData(requestDto);
        validateFile(documentImage);
        validateFile(bankStatementImage);

        try {
            Map<String, String> uploadedUrls = uploadUserFiles(documentImage, bankStatementImage);

            List<Role> roles = resolveUserRoles(requestDto.getRoles());
            User user = createUserByRole(roles);
            populateUserData(user, requestDto, uploadedUrls, roles);
            this.userRepository.save(user);
           this.notificationUtils.sendRegistrationEmailAsync(user.getId());
            return builderRegisterResponseDto(user);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar registro de usuario: " + e.getMessage(), e);
        }
    }


    private RegisterResponseDto builderRegisterResponseDto(User user){
       return  RegisterResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .lastname(user.getLastname())
                .birthday(user.getBirthday())
                .document(user.getDocument())
                .build();
    }

    private void confirmData(RegisterRequestDto requestDto) {
        int age = Period.between(requestDto.getBirthday(), LocalDate.now()).getYears();
        if (age < 18 || age > 120) {
            throw new IBadRequestExceptions("Edad inválida. Debe ser mayor de edad.");
        }
    }
    private void validateFile(MultipartFile file) {
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("El archivo excede el tamaño máximo permitido (5MB)");
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                !(contentType.equals("image/jpeg") ||
                        contentType.equals("image/png") ||
                        contentType.equals("application/pdf"))) {
            throw new RuntimeException("Tipo de archivo no permitido. Use JPG, PNG o PDF");
        }
    }
    private Map<String, String> uploadUserFiles(MultipartFile documentImage, MultipartFile bankStatementImage) {
        Map<String, String> urls = new HashMap<>();

        Map documentResult = cloudinaryService.uploadFile(documentImage, "user_document");
        Map bankStatementResult = cloudinaryService.uploadFile(bankStatementImage, "bank_statements");

        urls.put("documentUrl", documentResult.get("url").toString());
        urls.put("bankStatementUrl", bankStatementResult.get("url").toString());

        return urls;
    }

    private List<Role> resolveUserRoles(List<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RoleNotFoundException("Error: Rol no existe: " + roleName)))
                .collect(Collectors.toList());
    }

    private void populateUserData(User user, RegisterRequestDto requestDto,
                                  Map<String, String> uploadedUrls, List<Role> roles) {
        user.setEmail(requestDto.getEmail());
        user.setActive(false);
        user.setBankAccount(requestDto.getBankAccount());
        user.setBirthday(requestDto.getBirthday());
        user.setDocument(requestDto.getDocument());
        user.setName(requestDto.getName());
        user.setLastname(requestDto.getLastname());
        user.setRoles(roles);
        user.setCity(requestDto.getCity());
        user.setDocumentUrl(uploadedUrls.get("documentUrl"));
        user.setAccountBankUrl(uploadedUrls.get("bankStatementUrl"));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
    }
    private  void validateIfUserExists(RegisterRequestDto requestDto){
        userRepository.findByEmail(requestDto.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistException("Error: Usuario ya existe");
                });

       this.userRepository.findByDocument(
                requestDto.getDocument()
        ).ifPresent( user -> {
           throw new IBadRequestExceptions("Error: documento ya existe");
       });
        this.userRepository
                .findByBankAccount(requestDto
                        .getBankAccount())
                .ifPresent(user -> {
                    throw new IBadRequestExceptions("Error:esta cuenta ya esta registrada");
                });
    }

    private User createUserByRole(List<Role> roles) {
        if (hasRole(roles, "ROLE_INVESTOR")) {
            return new Investor();
        } else if (hasRole(roles, "ROLE_APPLICANT")) {
            return new Applicant();
        } else {
            throw new IBadRequestExceptions("Error: solo puede inscribirse como aplicante o inversor");
        }
    }

    private boolean hasRole(List<Role> roles, String roleName) {
        return roles.stream().anyMatch(role -> roleName.equalsIgnoreCase(role.getName()));
    }


}
