package com.app.dtos.reservation;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationRequestDto {
    private String userDni;
    private String userPhone;
    private Long userId;
    private LocalDate  date;
    private Long scrapYardPartId;
}
