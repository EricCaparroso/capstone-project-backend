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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.api.ApiResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardRequestDto;
import com.app.dtos.scrapyarddto.ScrapYardResponseDto;
import com.app.dtos.scrapyardpartsdto.ScrapYardPartsRequestDto;
import com.app.repository.ScrapYardPartsRepository;
import com.app.service.ScrapYardServiceImpl;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class ScrapYardController {

    private final ScrapYardPartsRepository scrapYardPartsRepository;

	private static final String PART_RESOURCE = "/scrapyardparts";
	private static final String PART_SY_ID_PATH = PART_RESOURCE + "/scrapyards/{scrapYardId}";
	private static final String PART_PART_ID_PATH = PART_RESOURCE + "/{partId}/{carId}";
	private static final String PART_RESERVATION_PATH = PART_RESOURCE + "/scrapyards/reservation/{scrapYardPartId}";
	private static final String PART_DELETE_PATH = PART_RESOURCE + "/scrapyards/delete/{scrapYardPartId}";
	private static final String SCRAPYARD_PART_ID_PATH = "/scrapyards/bypartid/{scrapYardPartId}";
	private static final String UPDATE_PART = PART_RESOURCE + "/update";

	
	@Autowired
	ScrapYardServiceImpl scrapYardService;


    ScrapYardController(ScrapYardPartsRepository scrapYardPartsRepository) {
        this.scrapYardPartsRepository = scrapYardPartsRepository;
    }

	
	/**
	 *  GET ALL PARTS
	 */
    @Operation(summary = "Get all parts by scrapyard ID", description = "Returns a list of parts available in a specific scrapyard.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Parts fetched successfully",
            content = @Content(schema = @Schema(implementation = ScrapYardPartsResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Scrapyard not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
	@GetMapping(value = PART_SY_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)	
	public ResponseEntity<ApiResponseDto<List<ScrapYardPartsResponseDto>>> getAllPartsByScrapYard(@PathVariable Long scrapYardId) {

		List<ScrapYardPartsResponseDto> scrapYardParts = scrapYardService.getAllPartsBySY(scrapYardId);
		ApiResponseDto<List<ScrapYardPartsResponseDto>> response = new ApiResponseDto<>("parts fetched successfully",
				HttpStatus.OK.value(), scrapYardParts);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }

	/**
	 *  GET PART BY SUBCATEGORY ID AND CAR ID
	 * @param partName
	 */
    @Operation(summary = "Get parts by subcategory and car", description = "Returns parts that match both the given subcategory ID and car ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Parts fetched successfully"),
        @ApiResponse(responseCode = "404", description = "No matching parts found")
    })
	@GetMapping(value = PART_PART_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<ScrapYardPartsResponseDto>>> getPartBySubcategoryId(@PathVariable Long partId, 
																								  @PathVariable Long carId) {		
		List<ScrapYardPartsResponseDto> parts = scrapYardService.getPartBySubcategoryIdAndCarId(partId, carId);
		
		ApiResponseDto<List<ScrapYardPartsResponseDto>> response = new ApiResponseDto<>("Book fetched successfully",
				HttpStatus.OK.value(), parts);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * GET SCRAPYARD BY SCRAPYARDPARTID
	 * @param partId
	 * @return
	 */
    @Operation(summary = "Get scrapyard by part ID", description = "Finds the scrapyard that owns a given part.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Scrapyard found successfully"),
        @ApiResponse(responseCode = "404", description = "Scrapyard not found")
    })
	@GetMapping(value = SCRAPYARD_PART_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<ScrapYardResponseDto>> getScrapYardByPartId(@PathVariable Long scrapYardPartId) {		
		ScrapYardResponseDto parts = scrapYardService.getScrapYardByPartId(scrapYardPartId);
		
		ApiResponseDto<ScrapYardResponseDto> response = new ApiResponseDto<>("Book fetched successfully",
				HttpStatus.OK.value(), parts);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 *  RESTOCK RESERVATION
	 * @param partName
	 */
    @Operation(summary = "Restock a scrapyard part", description = "Restores stock of a part after reservation cancellation.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Restocked successfully"),
        @ApiResponse(responseCode = "404", description = "Part not found")
    })
	@PutMapping(value = PART_RESERVATION_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Void>> restockScrapYardPart(@PathVariable Long scrapYardPartId) {	

	    
	    scrapYardService.restockScrapYardPart(scrapYardPartId);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 *  UPDATE PART
	 * @param partName
	 */
    @Operation(summary = "Update a scrapyard part", description = "Modifies data of a specific part in a scrapyard.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
	@PutMapping(value = UPDATE_PART, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Void>> updatePart(@RequestBody ScrapYardPartsRequestDto scrapyardPartsRequestDto) {	
	    
	    scrapYardService.updatePart(scrapyardPartsRequestDto);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
	 *  DELETE PART BY SCRAPYARDPARTSiD
	 * @param partName
	 */
    @Operation(summary = "Delete a part by ID", description = "Deletes a part from the scrapyard inventory.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleted successfully",
            content = @Content(schema = @Schema(hidden = true))),
        @ApiResponse(responseCode = "404", description = "Part not found")
    })
	@DeleteMapping(value = PART_DELETE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deletePart(@PathVariable Long scrapYardPartId) {		
		scrapYardService.deletePart(scrapYardPartId);
		
		
	    return ResponseEntity.noContent().build();
	}
	
	/**
	 * 
	 * @param scrapyardPartsRequestDto
	 * @return
	 */
    @Operation(summary = "Add a new part to a scrapyard", description = "Registers a new part in the scrapyard's stock.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data provided")
    })
	@PostMapping(value = PART_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addPart(@RequestBody ScrapYardPartsRequestDto scrapyardPartsRequestDto) {
		System.out.println(scrapyardPartsRequestDto);
		scrapYardService.addPart(scrapyardPartsRequestDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
