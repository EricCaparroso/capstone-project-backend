package com.app.service;

import java.util.List;

import com.app.dtos.partdto.PartResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardRequestDto;
import com.app.dtos.scrapyarddto.ScrapYardResponseDto;
import com.app.dtos.scrapyardpartsdto.ScrapYardPartsRequestDto;
import com.app.entity.ScrapYard;
import com.app.entity.ScrapYardParts;

public interface ScrapYardService {

	List<ScrapYardPartsResponseDto> getPartByName(String partName);

	List<ScrapYardPartsResponseDto> getAllPartsBySY(Long scrapyardId);

	ScrapYard findById(Long idEntity);

	void deletePart(Long scrapYardPartId);
	
	void addPart(ScrapYardPartsRequestDto scrapYardPartRequestDto);

	ScrapYardResponseDto getScrapYardByPartId(Long scrapYardPartId);

	void restockScrapYardPart(Long scrapYardPartId);

	void updatePart(ScrapYardPartsRequestDto scrapYardPart);

	List<ScrapYardPartsResponseDto> getPartBySubcategoryIdAndCarId(Long partId, Long carId);
}
