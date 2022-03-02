#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ${package}.serialization.converter.YamlJackson2HttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer{
		
	private static final MediaType MEDIA_TYPE_YAML = MediaType.valueOf("application/x-yaml");
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", 
					"OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
// Via EXTENSION -> http://localhost:8080/person/v1/16.json
//		configurer.favorParameter(false)
//			.ignoreAcceptHeader(false)
//			.defaultContentType(MediaType.APPLICATION_JSON)
//			.mediaType("json", MediaType.APPLICATION_JSON)
//			.mediaType("xml", MediaType.APPLICATION_XML);
		
// Via QUERY PARAM -> http://localhost:8080/person/v1/16?mediaType=json
//		configurer.favorPathExtension(false)
//		.favorParameter(true)
//		.parameterName("mediaType")
//		.ignoreAcceptHeader(true)
//		.useRegisteredExtensionsOnly(false)
//		.defaultContentType(MediaType.APPLICATION_JSON)
//		.mediaType("json", MediaType.APPLICATION_JSON)
//		.mediaType("xml", MediaType.APPLICATION_XML);
		
// Via HEADER
		configurer.favorPathExtension(false)
		.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json", MediaType.APPLICATION_JSON)
		.mediaType("xml", MediaType.APPLICATION_XML)
		.mediaType("x-yaml", MEDIA_TYPE_YAML);
	}
}

