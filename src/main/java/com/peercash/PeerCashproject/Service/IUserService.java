package com.peercash.PeerCashproject.Service;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.DeleteUserResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.GetAllUsersDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

  RegisterResponseDto registerUser (RegisterRequestDto requestDto,
                                    MultipartFile documentUrl,
                                    MultipartFile bankAccountUrl);

}
