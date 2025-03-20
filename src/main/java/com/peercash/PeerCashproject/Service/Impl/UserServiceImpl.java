package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Exceptions.Custom.RoleNotFoundException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserAlreadyExistException;
import com.peercash.PeerCashproject.Models.Role;
import com.peercash.PeerCashproject.Models.User;
import com.peercash.PeerCashproject.Repository.RoleRepository;
import com.peercash.PeerCashproject.Repository.UserRepository;
import com.peercash.PeerCashproject.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public RegisterResponseDto registerUser(RegisterRequestDto requestDto) {

      var user =   this.userRepository.findByEmail(requestDto.getEmail());
      if(user.isPresent()) {
          throw new UserAlreadyExistException("Error: Usuario ya existe");
      }

      String roleName = requestDto.getUserType().equals("INVESTOR") ? "ROLE_INVESTOR": "ROLE_APPLICANT";
      Role role = this.roleRepository.findByName(roleName).orElseThrow(()
              -> new RoleNotFoundException("Error: Rol no existe"));

      List<Role> rolesUser = new ArrayList<>();
      rolesUser.add(role);
      User createUser = User.builder()
              .email(requestDto.getEmail())
              .active(true)
              .bankAccount(requestDto.getBankAccount())
              .birthday(requestDto.getBirthday())
              .document(requestDto.getDocument())
              .email(requestDto.getEmail())
              .name(requestDto.getName())
              .lastname(requestDto.getLastname())
              .roles(rolesUser)
              .build();
      this.userRepository.save(createUser);

        return builderRegisterResponseDto(createUser);
    }

    private RegisterResponseDto builderRegisterResponseDto(User user){
       return  RegisterResponseDto.builder()
                .id(user.getId())
                .roles(user.getRoles().toString())
                .username(user.getEmail())
                .name(user.getName())
                .lastname(user.getLastname())
                .birthday(user.getBirthday())
                .document(user.getDocument())
                .build();
    }
}
