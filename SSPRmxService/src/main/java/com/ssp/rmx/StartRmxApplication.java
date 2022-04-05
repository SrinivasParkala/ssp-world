package com.ssp.rmx;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StartRmxApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(StartRmxApplication.class, args);
	}

	@Bean
  public RestTemplate getRestTemplate() {
     return new RestTemplate();
  }
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey())).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "Header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**")).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User Management API").description("")
				.termsOfServiceUrl("https://www.sspusermgmt.com/api")
				.contact(new Contact("Srinivas Shettigar", "http://www.sspusermgmt.com",
						"sspusermgmt@example.com"))
				.license("Open Source").licenseUrl("https://www.example.com").version("1.0.0").build();
	}

}
