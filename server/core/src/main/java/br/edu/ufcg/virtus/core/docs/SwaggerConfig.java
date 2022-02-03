package br.edu.ufcg.virtus.core.docs;

import org.springframework.context.annotation.Configuration;

import br.edu.ufcg.virtus.common.config.BaseSwaggerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    public SwaggerConfig() {
        super("br.edu.ufcg.virtus.core.controller", "way-core");
    }
}
