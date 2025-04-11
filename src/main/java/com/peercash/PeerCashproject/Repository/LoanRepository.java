package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Enums.StatusLoan;
import com.peercash.PeerCashproject.Models.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loans, Long> {

    boolean existsByApplicantIdAndStatusLoan(Long applicantId, StatusLoan statusLoan);
    List<Loans> findLoansByStatusLoan(StatusLoan statusLoan);
    Optional<Loans> findByApplicantId(Long applicantId);
    Optional<Loans> findById(Long id);

}
