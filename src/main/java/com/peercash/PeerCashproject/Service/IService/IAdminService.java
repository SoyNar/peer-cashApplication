package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Response.*;

import java.util.List;

public interface IAdminService {
    List<GetAllUsersDto> getAllUsers();
    DeleteUserResponseDto deleteUser(Long id);
    List<GetAuditDto> getAllAudit();
    //metodo para validar un usuario, cambiar su estado de active false a active true cuando el administrador a comprobado los datos"
    ActivateAccountUserDto activateUserAccount (Long userId);
    List<RequestForApprovalResponseDto> getRequestForApproval();
}
