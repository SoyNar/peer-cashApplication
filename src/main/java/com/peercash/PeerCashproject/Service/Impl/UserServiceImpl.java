package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.SeeAllLoansResponseDto;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.RoleNotFoundException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserAlreadyExistException;
import com.peercash.PeerCashproject.Models.*;
import com.peercash.PeerCashproject.Repository.LoanRepository;
import com.peercash.PeerCashproject.Repository.RoleRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IService.IUserService;
import com.peercash.PeerCashproject.Utils.Auditable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;

    @Auditable(action = "REGISTER_USER", entity = "User")
    @Transactional
    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto requestDto,
                                            MultipartFile documentImage,
                                            MultipartFile bankStatementImage) {

      var user =   this.userRepository.findByEmail(requestDto.getEmail());

      if(user.isPresent()) {
          throw new UserAlreadyExistException("Error: Usuario ya existe");
      }
        if(this.userRepository.findByDocument(
                requestDto.getDocument()
        ).isPresent()){ throw new IBadRequestExceptions("Error: documento ya existe");}
        if(this.userRepository
                .findByBankAccount(requestDto
                        .getBankAccount())
                .isPresent()){throw new IBadRequestExceptions("Error:esta cuenta ya esta registrada");}


        confirmData(requestDto);
      validateFile(documentImage);
      validateFile(bankStatementImage);

        try {
            Map documentResult = cloudinaryService.uploadFile(documentImage, "user_document");
            Map bankStatementResult = cloudinaryService.uploadFile(bankStatementImage, "bank_statements");

            String documentUrl = documentResult.get("url").toString();
            String bankStatementUrl = bankStatementResult.get("url").toString();

            List<Role> rolesUser = requestDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElseThrow(() -> new RoleNotFoundException("Error: Rol no existe: " + roleName)))
                    .collect(Collectors.toList());

            User createUser;
            if (rolesUser.stream().anyMatch(role -> "ROLE_INVESTOR".equalsIgnoreCase(role.getName()))) {
                createUser = new Investor();
            } else if(rolesUser.stream().anyMatch(role -> "ROLE_APPLICANT".equalsIgnoreCase(role.getName()))) {
                createUser = new Applicant();
            } else {
                throw new IBadRequestExceptions("Error: solo puede inscribirse como aplicante o inversor");
            }
            String password = passwordEncoder.encode(requestDto.getPassword());

            createUser.setEmail(requestDto.getEmail());
            createUser.setActive(false);
            createUser.setBankAccount(requestDto.getBankAccount());
            createUser.setBirthday(requestDto.getBirthday());
            createUser.setDocument(requestDto.getDocument());
            createUser.setName(requestDto.getName());
            createUser.setLastname(requestDto.getLastname());
            createUser.setRoles(rolesUser);
            createUser.setCity(requestDto.getCity());
            createUser.setDocumentUrl(documentUrl);
            createUser.setAccountBankUrl(bankStatementUrl);
            createUser.setPassword(password);

            this.userRepository.save(createUser);

            return builderRegisterResponseDto(createUser);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar archivos: " + e.getMessage(), e);
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



}
