package com.peercash.PeerCashproject.Service.IService;


import com.peercash.PeerCashproject.Dtos.Response.GetAllLoanPendingDto;
import com.peercash.PeerCashproject.Dtos.Response.InvestInALoanResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface InvestorUserService {

    List<GetAllLoanPendingDto> getAllLoanPending();
    /**
     * metodo para invetir en un prestamo
     * */
    InvestInALoanResponseDto investInALoan(Long loanId, Long investorId);
    String rejectLoan(Long loanId,Long investorId );

}
