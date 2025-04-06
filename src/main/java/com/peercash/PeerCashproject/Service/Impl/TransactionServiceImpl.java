package com.peercash.PeerCashproject.Service.Impl;

import com.peercash.PeerCashproject.Dtos.Request.PaymentLoanRequestDto;
import com.peercash.PeerCashproject.Dtos.Response.PaymentLoanResponseDto;
import com.peercash.PeerCashproject.Enums.StatusT;
import com.peercash.PeerCashproject.Enums.TypeTransaction;
import com.peercash.PeerCashproject.Exceptions.Custom.EntityNotFoundException;
import com.peercash.PeerCashproject.Exceptions.Custom.InvalidOperationException;
import com.peercash.PeerCashproject.Exceptions.Custom.UnauthorizedActionException;
import com.peercash.PeerCashproject.Exceptions.Custom.UserNotFondException;
import com.peercash.PeerCashproject.Models.Applicant;
import com.peercash.PeerCashproject.Models.Loans;
import com.peercash.PeerCashproject.Models.Transactions;
import com.peercash.PeerCashproject.Repository.ApplicantRepository;
import com.peercash.PeerCashproject.Repository.LoanRepository;
import com.peercash.PeerCashproject.Repository.TransactionRepository;
import com.peercash.PeerCashproject.Service.IService.IEmailService;
import com.peercash.PeerCashproject.Service.IService.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final ApplicantRepository applicantRepository;
    private final LoanRepository loanRepository;
    private final IEmailService emailService;


    @Transactional
    @Override
    public PaymentLoanResponseDto paymentLoan(PaymentLoanRequestDto requestDto, Long userId, Long loanId) {
       /**
        * valifeature/010-Implement-loan-marketplace
        * -for-investorsdar el usuario que hara el pago
        * Validar si el usurio esta autenticado
        * Verificar si el préstamo pertenece al usuario que está haciendo el pago
        * Validar si el préstamo está activo  o en mora
        * Agregar número de cuota que se está pagando
        * Incluir saldo restante después del pago
        * Actualizar estado del préstamo
        *
        * */
        Applicant applicant = this.applicantRepository.findById(userId).orElseThrow(()
                -> new UserNotFondException("Usuario no encontrado"));
        Loans loan = this.loanRepository.findById(loanId).orElseThrow(() ->
                new EntityNotFoundException("Prestamo no encontrado"));
        if(!loan.getApplicant().getId().equals(applicant.getId())) {
            throw new UnauthorizedActionException("no tiene permiso para hacer esta transaccion");
        }

        if(loan.getPaidInstallments() == null){
            loan.setPaidInstallments(0);
        }

        if(loan.getPaidInstallments() > loan.getNumberOfInstallment()) {
            throw new InvalidOperationException("EL prestamos ya ha sido pagadp totalmente");
        }
        int currentInstallment = loan.getPaidInstallments() + 1;
        loan.setPaidInstallments(currentInstallment);



        BigDecimal newDue = loan.getTotalDue().subtract(loan.getMonthlyInstallment());
        loan.setTotalDue(newDue);
        loanRepository.save(loan);

        loan.setPaidInstallments(currentInstallment);


        //create transaction

        Transactions transaction = Transactions.builder()
                .loan(loan)
                .user(applicant)
                .dateTransaction(LocalDateTime.now())
                .amount(loan.getMonthlyInstallment())
                .description(requestDto.getDescription())
                .typeTransaction(TypeTransaction.LOAN_PAYMENT)
                .reference("Pago Cuota prestamo 00 " + currentInstallment + "prestamo N 00: " + loan.getId())
                .statusTransaction(StatusT.PENDING_PAYMENT_CONFIRMATION)
                .paymentMethod("transferencia bancaria")
                .build();

        if (currentInstallment >= loan.getNumberOfInstallment() || newDue.compareTo(BigDecimal.ZERO) <= 0) {
            transaction.setStatusTransaction(StatusT.COMPLETED_PAYMENT);
        }

        this.transactionRepository.save(transaction);

        CompletableFuture.runAsync(() -> {
            try {
                sendNotificationEmail(transaction);
            } catch (Exception e) {
                log.error("Error al enviar el email {}", e.getMessage());
            }
        });
        return mapToPaymentResponse(transaction);
    }

    private PaymentLoanResponseDto mapToPaymentResponse(Transactions transaction){

        LocalDateTime calculatedNextPaymentDate = calculateNextPaymentDate(transaction);

        return  PaymentLoanResponseDto.builder()
                .dateTransaction(transaction.getDateTransaction().toString())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .reference(transaction.getReference())
                .id(transaction.getId())
                .userId(transaction.getUser().getId())
                .statusTransaction(transaction.getStatusTransaction().toString())
                .nextPaymentDate(calculatedNextPaymentDate.toString())
                .paymentMethod(transaction.getPaymentMethod())
                .build();
    }

    private LocalDateTime calculateNextPaymentDate(Transactions transaction){
        LocalDateTime now = transaction.getDateTransaction();
        return now.plusWeeks(1);
    }

    private  void sendNotificationEmail(Transactions transaction){
        Map<String,Object> emailBody = new HashMap<>();
        emailBody.put("applicant",transaction.getUser().getName());
        emailBody.put("loanId", transaction.getLoan().getId());
        emailBody.put("amount",transaction.getAmount());
        emailBody.put("amountDue" , transaction.getLoan().getTotalDue().toString());
        emailBody.put("numberOfInstallments",transaction.getLoan().getNumberOfInstallment());
        emailBody.put("remainingInstallments",transaction.getLoan().getPaidInstallments());
        emailBody.put("nextPay",LocalDateTime.now().plusWeeks(1));

        emailService.sendEmail(transaction.getUser().getEmail(),
                "PAGO EXISTOSO",
                "payment-loan",
                emailBody);

    }
}
