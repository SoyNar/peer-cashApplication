package com.peercash.PeerCashproject.Service;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;

public interface IUserService {

  RegisterResponseDto registerUser (RegisterRequestDto requestDto);
}
