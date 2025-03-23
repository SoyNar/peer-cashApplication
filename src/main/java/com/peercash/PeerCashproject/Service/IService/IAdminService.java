package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Response.ActivateAccountUserDto;
import com.peercash.PeerCashproject.Dtos.Response.DeleteUserResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.GetAllUsersDto;
import com.peercash.PeerCashproject.Dtos.Response.GetAuditDto;

import java.util.List;

public interface IAdminService {
    List<GetAllUsersDto> getAllUsers();
    DeleteUserResponseDto deleteUser(Long id);
    List<GetAuditDto> getAllAudit();
    //metodo para validar un usuario, cambiar su estado de active false a active true cuando el administrador a comprobado los datos"
    ActivateAccountUserDto activateUserAccount (Long userId);
}
