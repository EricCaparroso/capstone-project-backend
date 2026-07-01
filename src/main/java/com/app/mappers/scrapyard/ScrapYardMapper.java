package com.app.mappers.scrapyard;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardRequestDto;
import com.app.dtos.scrapyarddto.ScrapYardResponseDto;
import com.app.dtos.scrapyardpartsdto.ScrapYardPartsRequestDto;
import com.app.entity.ScrapYard;
import com.app.entity.ScrapYardParts;


@Mapper(componentModel = "spring")
public interface ScrapYardMapper {
	// Dto -> Entity
		ScrapYard toEntity(ScrapYardRequestDto scrapYardRequestDto);
		
	    @Mapping(source = "scrapYardId", target = "scrapYardId")
		ScrapYardResponseDto toResponse(ScrapYard scrapYard);
}
