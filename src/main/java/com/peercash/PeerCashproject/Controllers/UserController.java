package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.DeleteUserResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.GetAllUsersDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peer-cash/auth")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser (
            @Valid
            @RequestBody RegisterRequestDto requestDto){
        RegisterResponseDto responseDto = this.userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


}
