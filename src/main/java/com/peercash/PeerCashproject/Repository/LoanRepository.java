package com.peercash.PeerCashproject.Repository;

import com.peercash.PeerCashproject.Enums.StatusLoan;
import com.peercash.PeerCashproject.Models.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loans, Long> {

    boolean existsByApplicantIdAndStatusLoan(Long applicantId, StatusLoan statusLoan);

}
