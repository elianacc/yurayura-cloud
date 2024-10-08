package pers.elianacc.yurayura.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2 config
 *
 * @author ELiaNaCc
 * @since 2019-11-18
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${yurayura.swagger.enable}")
    private Boolean swaggerEnable;
    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("pers.elianacc.yurayura.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(applicationName.toUpperCase() + "-API")
                        .description("在线接口文档")
                        .version("1.0.3")
                        .contact(new Contact("EliaNaCc", "https://github.com/elianacc/yura-cloud"
                                , "438507003@qq.com"))
                        .termsOfServiceUrl("http://yurayura.org/")
                        .license("Apache License")
                        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                        .build())
                .groupName(applicationName)
                .enable(swaggerEnable);
    }
}
