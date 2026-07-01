package com.app.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.RefreshToken;
import com.app.entity.Role;
import com.app.entity.UserEntity;
import com.app.dtos.auth.AuthLoginRequestDto;
import com.app.dtos.auth.AuthResponseDto;
import com.app.dtos.auth.JwtResponseDto;
import com.app.exception.ResourceNotFoundException;
import com.app.jwt.JwtTokenProvider;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private RefreshTokenServiceImpl refreshTokenService;
	
    /**
     * Maps a set of Role entities to a collection of Spring Security GrantedAuthority.
     *
     * @param roles the set of Role entities
     * @return a collection of GrantedAuthority objects
     */
	public Collection<GrantedAuthority> mapToAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName())))
																			.collect(Collectors.toList());
	}

    /**
     * Loads a user from the database by username and returns a Spring Security UserDetails object.
     * This method is used internally by Spring Security during authentication.
     *
     * @param username the username to look up
     * @return a UserDetails object with user credentials and authorities
     * @throws UsernameNotFoundException if the user is not found
     */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		System.out.println("username-->" + username);
		
		UserEntity userEntity = userRepository.findUserEntityByUsername(username)
											  .orElseThrow(() -> new ResourceNotFoundException("Username: " + username + " not found!"));
		
		
		return new User(userEntity.getUsername(),
						userEntity.getPassword(),
						userEntity.isEnabled(),
						userEntity.isAccountNoExpired(),
						userEntity.isCredentialNoExpired(),
						userEntity.isAccountNoLocked(),
						mapToAuthorities(userEntity.getRoles())
						);
	}


    /**
     * Authenticates a user by comparing the raw password with the encoded one in the database.
     *
     * @param username the username to authenticate
     * @param password the raw password to check
     * @return an Authentication object if successful
     * @throws BadCredentialsException if credentials are invalid
     */
	private Authentication authenticate(String username, String password) {
		System.out.println("authenticate -->" + username);
		UserDetails userDetails = this.loadUserByUsername(username);
		
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
	}
	
    /**
     * Handles the login logic:
     * - Authenticates the user
     * - Generates JWT access token
     * - Creates a refresh token
     * - Returns both in an AuthResponseDto
     *
     * @param authLoginRequest DTO with login credentials
     * @return AuthResponseDto with access token, refresh token, scrapYardId (if applicable), and userId
     */
	public AuthResponseDto login(AuthLoginRequestDto authLoginRequest) {
	    Authentication authentication = this.authenticate(authLoginRequest.getUsername(), authLoginRequest.getPassword());

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String accessToken = jwtTokenProvider.generateToken(authentication);

	    UserEntity user = userRepository.findUserEntityByUsername(authLoginRequest.getUsername())
	        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());

	    Long scrapYardId = null;
	    if (user.getRoles().stream().anyMatch(r -> r.getName().equals("SCRAPYARD"))) {
	        scrapYardId = user.getScrapYard() != null ? user.getScrapYard().getScrapYardId() : null;
	    }

	    return new AuthResponseDto(accessToken, refreshToken.getToken(), scrapYardId, user.getUserId());
	}
	
    /**
     * Refreshes the access token for a valid, authenticated user.
     *
     * @param user the UserEntity object
     * @return a new JWT access token
     */
	public String refreshToken(UserEntity user) {
		
	    UserDetails userDetails = this.loadUserByUsername(user.getUsername());
	    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, userDetails.getAuthorities());;

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String newAccessToken = jwtTokenProvider.generateToken(authentication);

        return newAccessToken;

	}
	
    /**
     * Registers a new user:
     * - Validates that the username is not taken
     * - Encodes the password
     * - Assigns a default role
     * - Saves the user
     * - Authenticates and returns tokens
     *
     * @param registerDto DTO with username and password
     * @return AuthResponseDto with access and refresh token
     */
	public AuthResponseDto register(AuthLoginRequestDto registerDto) {
	    if (userRepository.findUserEntityByUsername(registerDto.getUsername()).isPresent()) {
	        throw new RuntimeException("Username is already taken");
	    }

	    Role roleUser = roleRepository.findById(2L)
	        .orElseThrow(() -> new RuntimeException("Role USER not found"));

	    UserEntity newUser = new UserEntity();
	    newUser.setUsername(registerDto.getUsername());
	    newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
	    newUser.setEnabled(true);
	    newUser.setAccountNoExpired(true);
	    newUser.setAccountNoLocked(true);
	    newUser.setCredentialNoExpired(true);
	    newUser.setRoles(Set.of(roleUser));

	    userRepository.save(newUser);

	    // Autenticación tras registro
	    Authentication authentication = authenticate(registerDto.getUsername(), registerDto.getPassword());
	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String accessToken = jwtTokenProvider.generateToken(authentication);
	    RefreshToken refreshToken = refreshTokenService.createRefreshToken(newUser.getUserId());

	    return new AuthResponseDto(
	        accessToken,
	        refreshToken.getToken(),
	        null, // scrapYardId, nunca se registrara un usuario como desguace, se añade manualmente por codigo
	        newUser.getUserId()
	    );
	}
	
}
