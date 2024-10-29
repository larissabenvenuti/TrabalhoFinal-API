package org.serratec.trabalhofinal.redesocialsimples.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration 

public class OpenApiConfig {

	@Value("${dominio.openapi.dev-url}")
	private String devUrl;
	
	@Value("${dominio.openapi.prod-url}")
	private String prodUrl;
	
	@Bean 
	OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("URL do servidor de desenvolvimento");
		
		Server prodServer = new Server();
		prodServer.setUrl(prodUrl);
		prodServer.setDescription("URL do servidor de produção");
		
		Contact contact = new Contact();
		contact.setEmail("contato@meudominio.com.br");
		contact.setName("Grupo5");
		contact.setUrl("https://www.meudominio.com.br");
		
		License apacheLicense = new License().name("Apache License")
				.url("https://www.apache.org/license/LICENSE-2.0");
		
		Info info = new Info().title("API de Rede Social Simples").version("1.0").contact(contact)
				.description("API para testes no DBeaver").termsOfService("https://www.meudominio.com.br/termos")
				.license(apacheLicense);
		
		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}
	
}

