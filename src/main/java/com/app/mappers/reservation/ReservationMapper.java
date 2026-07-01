package com.app.mappers.reservation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.dtos.reservation.ReservationRequestDto;
import com.app.dtos.reservation.ReservationResponseDto;
import com.app.entity.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
	
	@Mapping(target = "reservationId", ignore = true)
	@Mapping(target = "scrapYardPart", ignore = true)
	@Mapping(target = "user", ignore = true)
	Reservation toEntity(ReservationRequestDto reservationRequestDto);

	@Mapping(target = "userId", ignore = true)
    @Mapping(source = "scrapYardPart", target = "scrapYardPart")
	ReservationResponseDto toResponse(Reservation reservation);

}
