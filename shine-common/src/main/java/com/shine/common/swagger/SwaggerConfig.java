package entity.Swagger;


import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @Desc: 后台管理_Swagger配置
 * ----------------------------
 * @Author: shine
 * @Date: 2020/1/10 8:56
 * ----------------------------
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    com.google.common.base.Predicate<RequestHandler> goods = RequestHandlerSelectors.basePackage("com.shine.goods..controller");
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(Predicates.or(goods))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("后台管理_API接口文档")
                        .description("商城各类后台管理接口")
                        .version("9.0")
                        .contact(new Contact("shine", "www.shineyu.com.cn", "shine_yu@vip.163.com"))
                        .license("The Apache License")
                        .licenseUrl("http://www.shine95.com.cn")
                        .build());

    }
}
