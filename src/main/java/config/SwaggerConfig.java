#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.util.Collections;

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
				.apis(RequestHandlerSelectors.basePackage("${package}"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(new ApiInfo("RestFull API With Spring Boot", 
						"Description for the API", 
						"v1", 
						"termsOfServiceUrl", 
						new Contact("Jamesson Faria", "", "jj@aol.com"), 
						"License API", 
						"License Url",
						Collections.emptyList()));
	}
	
}
