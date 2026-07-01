package com.app.mappers.car;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.dtos.carsdtos.CarResponseDto;
import com.app.dtos.carsdtos.CarRequestDto;
import com.app.entity.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {
	

	// Dto -> Entity
	@Mapping(target = "carId", ignore = true)
	Car toBook(CarRequestDto carRequestDto);
	
	// Entity -> Dto
	
	CarResponseDto toResponse(Car car);
}
