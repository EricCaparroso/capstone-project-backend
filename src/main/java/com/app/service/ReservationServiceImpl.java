package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dtos.reservation.ReservationRequestDto;
import com.app.dtos.reservation.ReservationResponseDto;
import com.app.entity.Reservation;
import com.app.entity.ScrapYardParts;
import com.app.entity.UserEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.mappers.reservation.ReservationMapper;
import com.app.mappers.scrapyardparts.ScrapYardPartsMapper;
import com.app.repository.ReservationRepository;
import com.app.repository.ScrapYardPartsRepository;
import com.app.repository.UserRepository;

/**
 * Service implementation for handling reservation logic.
 * Responsible for creating, retrieving, and cancelling part reservations.
 */
@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired ScrapYardPartsRepository scrapYardPartsRepository;
	@Autowired UserRepository userRepository;
	@Autowired ReservationRepository reservationRepository;
	@Autowired ReservationMapper reservationMapper;
	@Autowired ScrapYardPartsMapper scrapYardPartsMapper;

    /**
     * Creates a reservation for a given scrap yard part and user.
     * Ensures that the part is not already reserved before proceeding.
     *
     * @param reservationRequestDto the request DTO containing userId and scrapYardPartId
     * @return the created ReservationResponseDto
     */
	@Override
	@Transactional
	public ReservationResponseDto createReservation(ReservationRequestDto reservationRequestDto) {
		
		
		if (reservationRepository.findByScrapYardPart_ScrapYardPartId(reservationRequestDto.getScrapYardPartId()) != null) {
			throw new ResourceNotFoundException("ScrapYardPart already reserved");
		} else {
			
			UserEntity user = userRepository.findById(reservationRequestDto.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + reservationRequestDto.getUserId()));
			
			ScrapYardParts part = scrapYardPartsRepository.findById(reservationRequestDto.getScrapYardPartId())
					.orElseThrow(() -> new ResourceNotFoundException("ScrapYardPart not found with id: " + reservationRequestDto.getScrapYardPartId()));
			
			Reservation reservation = reservationMapper.toEntity(reservationRequestDto);
			reservation.setUser(user);
			reservation.setScrapYardPart(part);
			
			Reservation savedReservation = reservationRepository.save(reservation);
			
			part.setReservation(savedReservation);
			part.setReserved(true);
			scrapYardPartsRepository.save(part);
			
			return reservationMapper.toResponse(savedReservation);
		}

	}

    /**
     * Retrieves all reservations for a given user ID.
     *
     * @param userId the ID of the user
     * @return list of ReservationResponseDto objects
     */
	@Override
	public List<ReservationResponseDto> getAllReservations(Long userId) {

		UserEntity user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found" ));
		
		List<Reservation> reservationsResponse = reservationRepository.findByUserUserId(userId);
		
		List<ReservationResponseDto> reservations = reservationsResponse.stream()
			    .map(reservation -> {
			        ReservationResponseDto dto = reservationMapper.toResponse(reservation);
			        dto.setUserId(user.getUserId());
			        return dto;
			    })
			    .collect(Collectors.toList());
		
		return reservations;
	}
	
    /**
     * Cancels an existing reservation by ID.
     * Unlinks the reservation from the part and marks it as available.
     *
     * @param reservationId the ID of the reservation to cancel
     */
	@Override
	@Transactional
	public void cancelReservation(Long reservationId) {

	    ScrapYardParts part = scrapYardPartsRepository.findByReservation_ReservationId(reservationId);
	    if (part == null) {
	        throw new ResourceNotFoundException("Pieza no encontrada con esa reserva");
	    }

	    if (!part.isReserved()) {
	        throw new RuntimeException("La pieza no está reservada.");
	    }

	    part.setReservation(null);
	    part.setReserved(false);

	    scrapYardPartsRepository.save(part);
	    reservationRepository.deleteById(reservationId);
	}
	
    /**
     * Retrieves all reservations associated with a specific scrap yard.
     *
     * @param scrapYardId the ID of the scrap yard
     * @return list of ReservationResponseDto objects
     */
	@Override
	public List<ReservationResponseDto> getAllReservationsByScrapYard(Long scrapYardId) {

		
		List<Reservation> reservationsResponse = reservationRepository.findByScrapYardPart_ScrapYard_ScrapYardId(scrapYardId);
		
		List<ReservationResponseDto> reservations = reservationsResponse.stream()
			    .map(reservation -> {
			        ReservationResponseDto dto = reservationMapper.toResponse(reservation);
			        dto.setScrapYardPart(scrapYardPartsMapper.toResponse(reservation.getScrapYardPart()));
			        return dto;
			    })
			    .collect(Collectors.toList());
		
		return reservations;
	}
}
