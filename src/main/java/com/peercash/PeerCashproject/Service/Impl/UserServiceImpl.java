package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.RoleNotFoundException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserAlreadyExistException;
import com.peercash.PeerCashproject.Models.Role;
import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.RoleRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IService.IUserService;
import com.peercash.PeerCashproject.Utils.Auditable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;

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
      confirmData(requestDto);
      validateFile(documentImage);
      validateFile(bankStatementImage);
        try {
            Map documentResult = cloudinaryService.uploadFile(documentImage, "user_document");
            Map bankStatementResult = cloudinaryService.uploadFile(bankStatementImage, "bank_statements");

            String documentUrl = documentResult.get("url").toString();
            String bankStatementUrl = bankStatementResult.get("url").toString();

            String roleName = requestDto.getUserType().equals("INVESTOR") ? "ROLE_INVESTOR": "ROLE_APPLICANT";
            Role role = this.roleRepository.findByName(roleName).orElseThrow(()
                    -> new RoleNotFoundException("Error: Rol no existe"));

            List<Role> rolesUser = new ArrayList<>();
            rolesUser.add(role);
            User createUser = User.builder()
                    .email(requestDto.getEmail())
                    .active(false)
                    .bankAccount(requestDto.getBankAccount())
                    .birthday(requestDto.getBirthday())
                    .document(requestDto.getDocument())
                    .email(requestDto.getEmail())
                    .name(requestDto.getName())
                    .lastname(requestDto.getLastname())
                    .roles(rolesUser)
                    .city(requestDto.getCity())
                    .documentUrl(documentUrl)
                    .accountBankUrl(bankStatementUrl)
                    .build();
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

    /**
     * Un usuario aplicante solicita un prestamo
     * llena los datos y espera que alguien pueda invertir?
     *
     * */

}
