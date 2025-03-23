package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

  RegisterResponseDto registerUser (RegisterRequestDto requestDto,
                                    MultipartFile documentUrl,
                                    MultipartFile bankAccountUrl);

}
