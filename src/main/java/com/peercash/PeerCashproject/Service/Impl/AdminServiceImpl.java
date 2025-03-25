package com.peercash.PeerCashproject.Service.Impl;
import com.peercash.PeerCashproject.Dtos.Response.*;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.AuditEntity;
import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.AuditRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IService.IAdminService;
import com.peercash.PeerCashproject.Utils.Auditable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {
    private final UserRepository userRepository;
    private final AuditRepository auditRepository;

    @Auditable(action = "GET_USERS", entity = "User")
    @Transactional
    @Override
    public List<GetAllUsersDto> getAllUsers() {

        List<User> user = this.userRepository.findAll();
        return user.stream()
                .map(this::mapToGetAllUsers)
                .collect(Collectors.toList());


    }

    private GetAllUsersDto mapToGetAllUsers(User user){
        return  GetAllUsersDto.builder()
                .active(user.isActive())
                .id(user.getId())
                .backAccount(user.getBankAccount())
                .document(user.getDocument())
                .email(user.getEmail())
                .name(user.getName())
                .lastname(user.getLastname())
                .build();
    }
    @Auditable(action = "DELETE_USERS", entity = "User")
    @Transactional
    @Override
    public DeleteUserResponseDto deleteUser(Long id) {

        User user = this.userRepository.findById(id).orElseThrow(()
                -> new UserNotFondException("usuario no encontrado"));
        this.userRepository.deleteById(user.getId());
        return DeleteUserResponseDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build();
    }
@Transactional
    @Override
    public List<GetAuditDto> getAllAudit() {
        List<AuditEntity> auditEntities = this.auditRepository.findAll();

        return auditEntities.stream().map(this::mapToGetAllAudit)
                .collect(Collectors.toList());
    }

    /**
     * buscar user por id
     * verificar que active sea false
     * manejar errores
     *
     * */
    @Transactional
    @Override
    public ActivateAccountUserDto activateUserAccount(Long userId) {

        User findUserById = this.userRepository.findById(userId).orElseThrow(()
                -> new UserNotFondException("Usuario no existe"));

       if(findUserById.isActive()){
           throw new IBadRequestExceptions("El usuario ya esta activo");
       }
        findUserById.setActive(true);
        this.userRepository.save(findUserById);

        return ActivateAccountUserDto.builder()
                .activeUser(findUserById.isActive())
                .userId(findUserById.getId())
                .email(findUserById.getEmail())
                .name(findUserById.getName())
                .message("Cuenta de usuario activada exitosamente")
                .build();
    }

    @Transactional
    @Override
    public List<RequestForApprovalResponseDto> getRequestForApproval() {
        List<User> userApproval = this.userRepository.findByActiveFalse();

        return userApproval.stream().map(this::mapToRequestForApproval).collect(Collectors.toList());
    }

    private RequestForApprovalResponseDto mapToRequestForApproval(User user){
        return  RequestForApprovalResponseDto.builder()
                .birthday(user.getBirthday().toString())
                .active(user.isActive())
                .bankAccount(user.getBankAccount())
                .document(user.getDocument())
                .email(user.getEmail())
                .urlBankAccount(user.getAccountBankUrl())
                .urlDocument(user.getDocumentUrl())
                .name(user.getName())
                .lastname(user.getLastname())
                .build();
    }

    private GetAuditDto mapToGetAllAudit(AuditEntity auditEntity){
        return GetAuditDto.builder()
                .description(auditEntity.getDescription())
                .id(auditEntity.getId())
                .details(auditEntity.getDetails())
                .status(auditEntity.getStatus())
                .updateAt(auditEntity.getUpdateAt().toString())
                .ipAddress(auditEntity.getIpAddress())
                .userAgent(auditEntity.getUserAgent())
                .action(auditEntity.getAction())
                .entity(auditEntity.getNameEntity())
                .build();
    }
}
