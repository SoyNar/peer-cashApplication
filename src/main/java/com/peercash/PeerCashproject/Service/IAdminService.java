package com.peercash.PeerCashproject.Service;

import com.peercash.PeerCashproject.Dtos.Response.DeleteUserResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.GetAllUsersDto;

import java.util.List;

public interface IAdminService {
    List<GetAllUsersDto> getAllUsers();
    DeleteUserResponseDto deleteUser(Long id);
}
