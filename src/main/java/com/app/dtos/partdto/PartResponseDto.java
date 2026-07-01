package com.app.dtos.partdto;

import com.app.dtos.categorydto.CategoryResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PartResponseDto {
	private Long partId;
	private String name;
    private String imageName;
    private CategoryResponseDto category;

}
