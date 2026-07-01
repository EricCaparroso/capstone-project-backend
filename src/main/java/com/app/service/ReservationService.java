package com.app.service;

import java.util.List;

import com.app.dtos.reservation.ReservationRequestDto;
import com.app.dtos.reservation.ReservationResponseDto;

public interface ReservationService {
	ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto);
	List<ReservationResponseDto> getAllReservations(Long reservationId);
	void cancelReservation(Long reservationId);
	List<ReservationResponseDto> getAllReservationsByScrapYard(Long scrapYardId);
}
