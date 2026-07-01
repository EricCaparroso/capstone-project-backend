package com.app.controller;

import java.util.List;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.api.ApiResponseDto;
import com.app.dtos.reservation.ReservationRequestDto;
import com.app.dtos.reservation.ReservationResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.dtos.scrapyardpartsdto.ScrapYardPartsRequestDto;
import com.app.service.ReservationServiceImpl;
import com.app.service.ScrapYardServiceImpl;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	@Autowired
	ReservationServiceImpl reservationService;
	
	/**
	 *  GET ALL RESERVATIONS
	 */
	 @Operation(
		        summary = "Get all reservations for a user",
		        description = "Retrieves all reservations made by a specific user."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Reservations fetched successfully",
		            content = @Content(mediaType = "application/json",
		                schema = @Schema(implementation = ApiResponseDto.class))),
		        @ApiResponse(responseCode = "404", description = "User not found")
		    })
	@GetMapping(value = "/{userId}",  produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<ReservationResponseDto>>> getAllReservations(@PathVariable Long userId) {

		List<ReservationResponseDto> reservations = reservationService.getAllReservations(userId);
		
		ApiResponseDto<List<ReservationResponseDto>> response = new ApiResponseDto<>("parts fetched successfully",
				HttpStatus.OK.value(), reservations);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	/**
	 *  GET ALL RESERVATIONS
	 */
	 @Operation(
		        summary = "Get all reservations for a scrapyard",
		        description = "Retrieves all reservations made for a specific scrapyard."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Reservations fetched successfully",
		            content = @Content(mediaType = "application/json",
		                schema = @Schema(implementation = ApiResponseDto.class))),
		        @ApiResponse(responseCode = "404", description = "Scrapyard not found")
		    })
	@GetMapping(value = "/scrapyard/{scrapYardId}",  produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<ReservationResponseDto>>> getAllReservationsByScrapYard(@PathVariable Long scrapYardId) {

		List<ReservationResponseDto> reservations = reservationService.getAllReservationsByScrapYard(scrapYardId);
		
		ApiResponseDto<List<ReservationResponseDto>> response = new ApiResponseDto<>("parts fetched successfully",
				HttpStatus.OK.value(), reservations);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	/**
	 * CREATE NEW RESERVATION
	 * @param ReservationRequestDto
	 * @return
	 */
	 @Operation(
		        summary = "Create a new reservation",
		        description = "Creates a new reservation with the provided user and part information."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "201", description = "Reservation created successfully",
		            content = @Content(mediaType = "application/json",
		                schema = @Schema(implementation = ApiResponseDto.class))),
		        @ApiResponse(responseCode = "400", description = "Invalid reservation data")
		    })
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<ReservationResponseDto>> addReservartion(@RequestBody ReservationRequestDto reservationRequestDto) {
		
		ReservationResponseDto reservation = reservationService.createReservation(reservationRequestDto);
		ApiResponseDto<ReservationResponseDto> response = new ApiResponseDto<>("reservation created successfully",
				HttpStatus.OK.value(), reservation);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * CANCEL A RESERVATION
	 * @param ReservationRequestDto
	 * @return
	 */
	 @Operation(
		        summary = "Cancel a reservation",
		        description = "Deletes an existing reservation by its ID."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully"),
		        @ApiResponse(responseCode = "404", description = "Reservation not found")
		    })
	@DeleteMapping(value = "/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<ReservationResponseDto>> cancelReservation(@PathVariable Long reservationId) {
		
		reservationService.cancelReservation(reservationId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
