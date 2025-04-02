package com.peercash.PeerCashproject.Dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvestInALoanResponseDto {

    private List<LocalDate> dates;
    private Long investmentId;
}
