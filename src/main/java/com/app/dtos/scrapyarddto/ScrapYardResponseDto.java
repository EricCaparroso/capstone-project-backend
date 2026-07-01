package com.app.dtos.scrapyarddto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScrapYardResponseDto {
    private Long scrapYardId;
	private String name;
	private String location;
	private String number;
	private String schudle;
    private Double latitude;
    private Double longitude;
}
