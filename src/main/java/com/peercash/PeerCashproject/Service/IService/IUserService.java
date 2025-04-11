package com.peercash.PeerCashproject.Service.IService;

import com.peercash.PeerCashproject.Dtos.Request.RegisterRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.RegisterResponseDto;
import com.peercash.PeerCashproject.Dtos.Response.SeeAllLoansResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {

  RegisterResponseDto registerUser (RegisterRequestDto requestDto,
                                    MultipartFile documentUrl,
                                    MultipartFile bankAccountUrl);


}
