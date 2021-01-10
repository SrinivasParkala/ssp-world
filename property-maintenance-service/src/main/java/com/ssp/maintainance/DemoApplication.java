package com.ssp.maintainance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAuthorizationServer
@EnableResourceServer
@EnableSwagger2
public class DemoApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	@Value("${user.oauth.clientId}")
	private String clientid;

	@Value("${user.oauth.clientSecret}")
	private String clientSecret;

	@Value("${user.oauth.oauthUrl}")
	private String oauthUrl;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info("Loading Application:");
		logger.info("config properties:"+System.getenv().get("SPRING_CONFIG_LOCATION"));
		
		Properties prop = new Properties();
		prop.setProperty("spring.config.location", System.getenv().get("SPRING_CONFIG_LOCATION"));
		application.application().setDefaultProperties(prop);
		return application.sources(DemoApplication.class);
	}

	/*@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}*/
	
	public static void main(String[] args) {
		logger.info("this is a info message");
		logger.warn("this is a warn message");
		logger.error("this is a error message:");
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Docket maintainanceApi() {
		/***
		 * return new Docket(DocumentationType.SWAGGER_2) .select()
		 * .apis(RequestHandlerSelectors .basePackage("com.ssp.maintainance")).build();
		 */
		List<ResponseMessage> list = new java.util.ArrayList<>();
		list.add(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Result"))
				.build());
		list.add(new ResponseMessageBuilder().code(401).message("Unauthorized").responseModel(new ModelRef("Result"))
				.build());
		list.add(new ResponseMessageBuilder().code(406).message("Not Acceptable").responseModel(new ModelRef("Result"))
				.build());

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/ssp/**")).build()
				.securitySchemes(Collections.singletonList(securitySchema()))
				.securityContexts(Collections.singletonList(securityContext())).pathMapping("/")
				.useDefaultResponseMessages(false).apiInfo(apiInfo()).globalResponseMessage(RequestMethod.GET, list)
				.globalResponseMessage(RequestMethod.POST, list);
	}

	private OAuth securitySchema() {

		List<AuthorizationScope> authorizationScopeList = new ArrayList();
		authorizationScopeList.add(new AuthorizationScope("read", "read all"));
		// authorizationScopeList.add(new AuthorizationScope("trust", "trust all"));
		authorizationScopeList.add(new AuthorizationScope("write", "access all"));

		List<GrantType> grantTypes = new ArrayList();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(oauthUrl + "/oauth/token");

		grantTypes.add(creGrant);

		return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**")).build();
	}

	private List<SecurityReference> defaultAuth() {

		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[2];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		// authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
		authorizationScopes[1] = new AuthorizationScope("write", "write all");

		return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
	}

	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(clientid, clientSecret, "", "", "", ApiKeyVehicle.HEADER, "", " ");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("My API title").description("")
				.termsOfServiceUrl("https://www.example.com/api")
				.contact(new Contact("Srinivas Shettigar", "http://www.sspmaintainance.com",
						"sspmaintainance@example.com"))
				.license("Open Source").licenseUrl("https://www.example.com").version("1.0.0").build();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.US);
		return slr;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setCacheSeconds(3600);
		return messageSource;
	}

}
