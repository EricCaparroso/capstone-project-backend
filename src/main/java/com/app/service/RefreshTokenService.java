package com.app.service;

import java.util.Optional;

import com.app.entity.RefreshToken;

public interface RefreshTokenService {
	RefreshToken createRefreshToken(Long userId);

    RefreshToken verifyExpiration(RefreshToken token);

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserId(Long userId);
}
