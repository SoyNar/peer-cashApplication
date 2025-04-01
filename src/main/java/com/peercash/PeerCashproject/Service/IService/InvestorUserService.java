package com.peercash.PeerCashproject.Service.IService;


import com.peercash.PeerCashproject.Dtos.Response.GetAllLoanPendingDto;

import java.util.List;

public interface InvestorUserService {

    List<GetAllLoanPendingDto> getAllLoanPending();
    /**
     * metodo para invetir en un prestamo
     * */
    String investInALoan(Long loanId, Long investorId);

}
