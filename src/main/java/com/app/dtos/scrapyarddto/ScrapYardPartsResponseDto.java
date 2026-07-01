package com.app.dtos.scrapyarddto;

import com.app.dtos.carsdtos.CarResponseDto;
import com.app.dtos.partdto.PartResponseDto;
import com.app.entity.Part;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScrapYardPartsResponseDto {

	private Long scrapYardPartId;
	private PartResponseDto partId;
	private CarResponseDto car;
	private ScrapYardResponseDto scrapYard;
	private float price;
	private int wearLevel;
	private String category;
	private boolean reserved;
}
