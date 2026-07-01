package com.app.mappers.scrapyardparts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardRequestDto;
import com.app.dtos.scrapyardpartsdto.ScrapYardPartsRequestDto;
import com.app.entity.ScrapYard;
import com.app.entity.ScrapYardParts;

@Mapper(componentModel = "spring")
public interface ScrapYardPartsMapper {
	

	// Dto -> Entity
	@Mapping(target = "scrapYardId", ignore = true)
	ScrapYardParts toEntity(ScrapYardPartsRequestDto scrapYardRequestDto);
	
	ScrapYardPartsResponseDto toResponse(ScrapYardParts scrapYard);
}
