package br.com.umdesenvolvedor.restteste.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2 {
	
	@Bean
	public Docket decket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.umdesenvolvedor"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Library API")
				.description("API do projeto de teste unitários e de integração no Spring")
				.version("1.0")
				.contact(contact())
				.build();
		
	}

	private Contact contact() {
		return new Contact("João Rafael M Nogueira", "www.umdesenvolvedor.com.br", "j.rafael.tux@gmail.com");
	}
}
