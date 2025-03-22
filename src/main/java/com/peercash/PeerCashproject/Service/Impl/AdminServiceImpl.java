package com.peercash.PeerCashproject.Service.Impl;
import com.peercash.PeerCashproject.Dtos.Response.DeleteUserResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.GetAllUsersDto;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.AuditRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IAdminService;
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
}
