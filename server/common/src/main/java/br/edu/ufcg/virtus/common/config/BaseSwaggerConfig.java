package br.edu.ufcg.virtus.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class BaseSwaggerConfig extends WebMvcConfigurationSupport {

	private final String basePackage;
	private final String title;


	public BaseSwaggerConfig(String basePackage, String title) {
		this.basePackage = basePackage;
		this.title = title;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(this.basePackage))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(this.metaData());
	}

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title(this.title)
                .description("Rest documentation about this micro service")
                .version("1.0")
                .contact(new Contact("Igor Henrique Ferreira Moreno Pinheiro da Silva", "https://virtus.ufcg.edu.br", "igor.silva@virtus.ufcg.edu.br"))
                .build();
    }

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
