package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Response.*;
import com.peercash.PeerCashproject.Service.IService.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peer-cash/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService adminService;


    @GetMapping("/users")
    public ResponseEntity<List<GetAllUsersDto>> getAllUsers(){
        List<GetAllUsersDto> getAllUsersDtos = this.adminService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(getAllUsersDtos);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DeleteUserResponseDto> deleteUser(@PathVariable Long id){
        DeleteUserResponseDto deteleUser = this.adminService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(deteleUser);
    }

    @GetMapping("/audit")
    public ResponseEntity<List<GetAuditDto>> getALlAudit(){
        List<GetAuditDto> getAuditDtos = this.adminService.getAllAudit();
        return ResponseEntity.status(HttpStatus.OK).body(getAuditDtos);
    }
    @GetMapping("/for-approval")
    public ResponseEntity<List<RequestForApprovalResponseDto>> getUserForApproval(){
        List<RequestForApprovalResponseDto> forApprovalResponseDto = this.adminService.getRequestForApproval();
        return ResponseEntity.status(HttpStatus.OK).body(forApprovalResponseDto);
    }
    @PostMapping("/activate-account/{userId}")
    public ResponseEntity<ActivateAccountUserDto> activateAccount(Long userId){
        ActivateAccountUserDto activateAccountDto = this.adminService.activateUserAccount(userId);
        return ResponseEntity.status(HttpStatus.OK).body(activateAccountDto);
    }
}
