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
import com.app.dtos.categorydto.CategoryResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.service.CategoryServiceImpl;
import com.app.service.ScrapYardServiceImpl;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CategoryController {
	
	private static final String CATEGORY_RESOURCE = "/categories";
	private static final String CATEGORY_CAR = CATEGORY_RESOURCE + "/{carId}";
	
	@Autowired
	CategoryServiceImpl categoryService;
	
	/**
	 *  GET ALL CATEGORIES BY CAR
	 *  @param carID
	 */
	 @Operation(
		        summary = "Get all categories by car ID",
	    description = "Returns a list of all part categories available for a given car."
	)
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Categories fetched successfully",
	        content = @Content(mediaType = "application/json",
	            schema = @Schema(implementation = ApiResponseDto.class))),
	    @ApiResponse(responseCode = "404", description = "Car ID not found"),
	    @ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@GetMapping(value = CATEGORY_CAR, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getAllCategoriesByCar(@PathVariable long carId) {
		List<CategoryResponseDto> categories = categoryService.getAllCategoriesByCar(carId);

		ApiResponseDto<List<CategoryResponseDto>> response = new ApiResponseDto<>("Book fetched successfully",
				HttpStatus.OK.value(), categories);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	/**
	 *  GET ALL CATEGORIES
	 *  @param carID
	 */
	 @Operation(
		        summary = "Get all categories",
		        description = "Returns a list of all part categories available in the system."
		    )
		    @ApiResponses(value = {
		        @ApiResponse(responseCode = "200", description = "Categories fetched successfully",
		            content = @Content(mediaType = "application/json",
		                schema = @Schema(implementation = ApiResponseDto.class))),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    })
	@GetMapping(value = CATEGORY_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<CategoryResponseDto>>> getAllCategories() {
		List<CategoryResponseDto> categories = categoryService.getAllCategories();

		ApiResponseDto<List<CategoryResponseDto>> response = new ApiResponseDto<>("Book fetched successfully",
				HttpStatus.OK.value(), categories);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
