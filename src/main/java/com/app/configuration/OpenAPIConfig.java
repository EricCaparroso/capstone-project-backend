package com.app.configuration;

import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "WRITER SERVICE",
				description = "Our app provides a consice listing of writers name",
				termsOfService = "www.accesodatos/terminos_y_condiciones",
				version = "1.0.0",
				contact = @Contact(
						name = "Alexis",
						url = "http://salesianos-lacuesta.com",
						email = "alex@salesianos-lacuesta.net"
				),
				license = @License(
						name = "Standard Software Use License for Acceso a Datos",
						url = "http://lacuesta.salesianos.edu/licence"
				)
		),
		servers = {			// LLaves porque van a haber varios servidores
			@Server(
					description = "Server URL in Development enviroment",
					url = "http://localhost:8081"
			),
			@Server(
					description = "Server URL in Production enviroment",
					url = "http://localhost:8081"
			)
		},
		security = @SecurityRequirement(
				name = "Security Basic"
				)
)
@SecurityScheme(
		name = "Security Basic",
		description = "User/Password for Writers Service",
		type = SecuritySchemeType.HTTP,
		paramName = HttpHeaders.AUTHORIZATION,
		in = SecuritySchemeIn.HEADER,
		scheme = "basic"
)
public class OpenAPIConfig {

}
