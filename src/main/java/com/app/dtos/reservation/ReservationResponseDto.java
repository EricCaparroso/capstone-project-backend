package com.app.dtos.reservation;

import java.time.LocalDate;

import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationResponseDto {
	private Long ReservationId;
    private String userDni;
    private String userPhone;
    private Long userId;
    private LocalDate  date;
    private ScrapYardPartsResponseDto scrapYardPart;
}
