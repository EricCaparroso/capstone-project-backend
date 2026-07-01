package com.app.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.api.ApiResponseDto;
import com.app.dtos.carsdtos.CarResponseDto;
import com.app.service.CarServiceImpl;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CarController {
	
	private static final String CAR_RESOURCE = "/cars";



	@Autowired
	CarServiceImpl carService;
	
	
	
	/**
	 *  GET ALL CATEGORIES
+	 */
    @Operation(
        summary = "Get all cars",
        description = "Returns a list of all available cars in the system, each containing brand, model and engine information."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cars retrieved successfully",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiResponseDto.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	@GetMapping(value = CAR_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<CarResponseDto>>> getAllBrands() {
		List<CarResponseDto> brands = carService.getAllCars();

		ApiResponseDto<List<CarResponseDto>> response = new ApiResponseDto<>("Book fetched successfully",
				HttpStatus.OK.value(), brands);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
