package com.app.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.entity.RefreshToken;
import com.app.repository.RefreshTokenRepository;
import com.app.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service responsible for managing refresh tokens.
 * Handles creation, validation, lookup, and deletion of tokens from the database.
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Value("${jwt.refresh.expirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;
    
    /**
     * Creates a new refresh token for a given user.
     * Deletes any existing token associated with that user before creating a new one.
     *
     * @param userId the ID of the user
     * @return the newly created RefreshToken
     */

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUser_UserId(userId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).orElseThrow());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Validates that the given refresh token has not expired.
     * If expired, it is deleted and an exception is thrown.
     *
     * @param token the RefreshToken to verify
     * @return the same token if still valid
     * @throws RuntimeException if the token has expired
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token ha expirado.");
        }
        return token;
    }

    /**
     * Finds a refresh token by its token string.
     *
     * @param token the token string
     * @return Optional containing the RefreshToken if found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Deletes the refresh token associated with the given user ID.
     *
     * @param userId the user ID
     * @return the number of deleted rows (typically 1 or 0)
     */
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(
            userRepository.findById(userId).get());
    }
    
    /**
     * Deletes a refresh token by its token string.
     *
     * @param token the token string
     */
    @Transactional
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
