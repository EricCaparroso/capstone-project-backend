package com.app.dtos.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDto {

	private String accessToken;
	private String tokenType = "Bearer ";
    private String refreshToken;
	private Long scrapYardId;
	private Long userId;
	
	public AuthResponseDto(String accessToken, String refreshToken, Long scrapYardId, Long userId) {
		this.accessToken = accessToken;
        this.refreshToken = refreshToken;
		this.scrapYardId = scrapYardId;
		this.userId = userId;
	}
}
