package com.app.mappers.part;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.app.dtos.categorydto.CategoryResponseDto;
import com.app.dtos.partdto.PartResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardPartsResponseDto;
import com.app.dtos.scrapyarddto.ScrapYardRequestDto;
import com.app.entity.Part;
import com.app.entity.PartCategory;
import com.app.entity.ScrapYard;
import com.app.entity.ScrapYardParts;

@Mapper(componentModel = "spring")
public interface PartMapper {
	
		// Dto -> Entity
/*		@Mapping(target = "categoryId", ignore = true)
		@Mapping(target = "model", ignore = true)
		@Mapping(target = "motor", ignore = true)
		ScrapYard toBook(ScrapYardRequestDto scrapYardRequestDto);
		*/
		// Entity -> Dto
		
		PartResponseDto toResponse(Part part);
}
