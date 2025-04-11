package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Request.AuthRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.TokenResponseDto;

public interface IAuthService {
    TokenResponseDto authenticate(AuthRequestDto authRequestDto );
}
