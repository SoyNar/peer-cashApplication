package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Response.GetAllLoanPendingDto;
import com.peercash.PeerCashproject.Enums.StatusLoan;
import com.peercash.PeerCashproject.Exceptions.Custom.IBadRequestExceptions;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.Investor;
import com.peercash.PeerCashproject.Models.Loans;
import com.peercash.PeerCashproject.Repository.InvestorRepository;
import com.peercash.PeerCashproject.Repository.LoanRepository;
import com.peercash.PeerCashproject.Service.IService.IEmailService;
import com.peercash.PeerCashproject.Service.IService.InvestorUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestorUserServiceImpl implements InvestorUserService

{
    private final LoanRepository loanRepository;
    private final InvestorRepository investorRepository;
    private  final IEmailService emailService;


    @Override
    @Transactional
    public List<GetAllLoanPendingDto> getAllLoanPending() {
        List<Loans> loans = this.loanRepository.findLoansByStatusLoan(StatusLoan.PENDING);
        return loans.stream().map(this::mapToGetLoanPending).collect(Collectors.toList());
    }

    @Override
    public String investInALoan(Long loanId, Long investorId) {

        Loans loan = this.loanRepository.findById(loanId).orElseThrow(()
                -> new IBadRequestExceptions("No encontrado"));

        if(!loan.getStatusLoan().equals(StatusLoan.PENDING)) {
            throw new IBadRequestExceptions("solo puede invetir en prestamos pendientes");
        }

        Investor  investor = this.investorRepository.findByRoleName("ROLE_INVESTOR",investorId).orElseThrow(()
                -> new UserNotFondException("Erro: no encontrado o no tiene el rol investor"));

        if(!investor.isActive()){
            throw new IBadRequestExceptions("El usuario no puede invetir, porque su cuenta no ha sido activada");

        }

        loan.setStatusLoan(StatusLoan.APPROVED);
        this.loanRepository.save(loan);
        // generar los dias de pago semanales

        // enviar correo electronico a usuarios
       emailService.sendEmail(investor.getEmail(),
               "Inversion en prestamo","Invertiste en un prestamo");


        return "Prestamo aprobado";
    }

    private GetAllLoanPendingDto mapToGetLoanPending(Loans loans) {
        return GetAllLoanPendingDto.builder()
                .amount(loans.getRequestAmount().doubleValue())
                .reason(loans.getReason())
                .applicantId(loans.getApplicant().getId())
                .numberOfInstallments(loans.getNumberOfInstallment())
                .status(loans.getStatusLoan().toString())
                .build();
}
}
