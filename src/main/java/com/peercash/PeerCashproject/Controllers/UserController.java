package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/peer-cash/auth")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @PostMapping("register")
    public ResponseEntity<RegisterResponseDto> registerUser (
            @RequestBody RegisterRequestDto requestDto){
        RegisterResponseDto responseDto = this.userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
