package com.app.service;

import java.util.List;

import com.app.dtos.carsdtos.CarResponseDto;
import com.app.dtos.carsdtos.MotorResponseDto;

public interface CarService {
	
	List<CarResponseDto> getAllCars();
}
