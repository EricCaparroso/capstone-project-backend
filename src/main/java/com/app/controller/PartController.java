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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.api.ApiResponseDto;
import com.app.dtos.partdto.PartResponseDto;
import com.app.service.CarServiceImpl;
import com.app.service.PartServiceImpl;

@RestController
@RequestMapping("/api/parts")
@CrossOrigin(origins = "http://localhost:5173")
public class PartController {
	
	private static final String PART_RESOURCE = "/{carId}/{categoryId}";


	@Autowired
	PartServiceImpl partService;
	
	
	
	/**
	 *  GET ALL PARTS BY CAR
+	 */
	 @Operation(
		        summary = "Get all parts by car and category",
		        description = "Returns a list of all available parts for a specific car and category."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Parts fetched successfully",
		            content = @Content(mediaType = "application/json",
		                schema = @Schema(implementation = ApiResponseDto.class))),
		        @ApiResponse(responseCode = "404", description = "Car or category not found"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    })
	@GetMapping(value = PART_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<PartResponseDto>>> getAllPartsByCar(@PathVariable long carId, @PathVariable long categoryId) {
		List<PartResponseDto> brands = partService.getAllPartsByCar(carId, categoryId);

		ApiResponseDto<List<PartResponseDto>> response = new ApiResponseDto<>("Book fetched successfully",
				HttpStatus.OK.value(), brands);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	/**
	 *  GET ALL PARTS
+	 */
	 @Operation(
		        summary = "Get all parts",
		        description = "Returns a complete list of all parts available in the system, regardless of car or category."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Parts fetched successfully",
		            content = @Content(mediaType = "application/json",
		                schema = @Schema(implementation = ApiResponseDto.class))),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<PartResponseDto>>> getAllPartsByCar() {
		List<PartResponseDto> parts = partService.getAllParts();

		ApiResponseDto<List<PartResponseDto>> response = new ApiResponseDto<>("parts fetched successfully",
				HttpStatus.OK.value(), parts);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
