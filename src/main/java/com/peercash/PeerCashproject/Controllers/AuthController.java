package com.peercash.PeerCashproject.Controllers;

import com.peercash.PeerCashproject.Dtos.Request.AuthRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.TokenResponseDto;
import com.peercash.PeerCashproject.Service.IService.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/peer-cash/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> authenticate(
            @RequestBody AuthRequestDto requestDto){
        TokenResponseDto tokenResponseDto = authService.authenticate(requestDto);
        return  ResponseEntity.status(HttpStatus.OK).body(tokenResponseDto);
    }
}
