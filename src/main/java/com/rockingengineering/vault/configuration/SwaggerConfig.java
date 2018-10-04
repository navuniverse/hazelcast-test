package com.rockingengineering.vault.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.rockingengineering.vault.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("Naveen Kumar", "www.rockingengineering.com", "contact@rockingengineering.com");

		ApiInfo apiInfo = new ApiInfo(
				"Spring Cloud Vault Test Microservice",
				"RockingEngineering Spring Vault Test",
				"1.0",
				"https://www.rockingengineering.com/terms-conditions",
				contact,
				"RockingEngineering Internal License",
				"https://www.rockingengineering.com/privacy-policy");

		return apiInfo;
	}
}