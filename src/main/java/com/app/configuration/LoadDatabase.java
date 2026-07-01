package com.app.configuration;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.entity.Role;
import com.app.entity.ScrapYard;
import com.app.entity.UserEntity;
import com.app.repository.ScrapYardRepository;
import com.app.repository.UserRepository;

@Configuration
public class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, ScrapYardRepository scrapYardRepository) {
		return arg -> {
			/** Roles **/
			Role roleAdmin = Role.builder()
								 .name("ADMIN")
								 .build();
			
			Role roleUser = Role.builder()
					 			.name("USER")
					 			.build();
			

			Role roleDevelop= Role.builder()
					   			  .name("SCRAPYARD")
					   			  .build();
			
			UserEntity userAlex = UserEntity.builder()
											.username("Alexis")
											.password("$2a$10$3S84.aE5GAxLMeXyDUFkruNnoQVE/UOM6iY35vtwirheoBfl7B9qC")
											.isEnabled(true)
											.accountNoExpired(true)
											.accountNoLocked(true)
											.credentialNoExpired(true)
											.roles(Set.of(roleAdmin))
											.build();
			
			UserEntity userJose = UserEntity.builder()
											.username("Jose")
											.password("$2a$10$3S84.aE5GAxLMeXyDUFkruNnoQVE/UOM6iY35vtwirheoBfl7B9qC")
											.isEnabled(true)
											.accountNoExpired(true)
											.accountNoLocked(true)
											.credentialNoExpired(true)
											.roles(Set.of(roleUser))
											.build();
			
			ScrapYard scrapyardPolo = new ScrapYard();
			scrapyardPolo.setName("desguaces Polo");
			scrapyardPolo.setLocation("taco");
			scrapyardPolo.setNumber("608894476");
			scrapyardPolo.setSchudle("Lunes a Viernes de 08:00 a 18:00");
			scrapyardPolo.setLatitude(28.447994931901405);
			scrapyardPolo.setLongitude(-16.289774766054297);
			
			ScrapYard scrapyardPepe = new ScrapYard();
			scrapyardPepe.setName("desguaces Pepe");
			scrapyardPepe.setLocation("taco");
			scrapyardPepe.setNumber("609 785 878");
			scrapyardPepe.setSchudle("Lunes a Viernes de 08:00 a 18:00");
			scrapyardPepe.setLatitude(28.447994931901405);
			scrapyardPepe.setLongitude(-16.289774766054297);
			
			scrapYardRepository.save(scrapyardPepe);
			scrapYardRepository.save(scrapyardPolo);
			
			UserEntity userAndres = UserEntity.builder()
											  .username("Andres")
											  .scrapYard(new ScrapYard())
											  .scrapYard(scrapyardPolo)
											  .password("$2a$10$3S84.aE5GAxLMeXyDUFkruNnoQVE/UOM6iY35vtwirheoBfl7B9qC")
											  .isEnabled(true)
											  .accountNoExpired(true)
											  .accountNoLocked(true)
											  .credentialNoExpired(true)
											  .roles(Set.of(roleDevelop))
											  .build();		
			
			UserEntity userPepe = UserEntity.builder()
											  .username("Pepe")
											  .scrapYard(new ScrapYard())
											  .scrapYard(scrapyardPepe)
											  .password("$2a$10$3S84.aE5GAxLMeXyDUFkruNnoQVE/UOM6iY35vtwirheoBfl7B9qC")
											  .isEnabled(true)
											  .accountNoExpired(true)
											  .accountNoLocked(true)
											  .credentialNoExpired(true)
											  .roles(Set.of(roleDevelop))
											  .build();
			
			userRepository.saveAll(List.of(userPepe, userAndres, userJose));
		};
	}
}
