package com.aws.codestar.projecttemplates;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.context.annotation.Bean;

/** Simple class to start up the application.
 *
 * @SpringBootApplication adds:
 *  @Configuration
 *  @EnableAutoConfiguration
 *  @ComponentScan
 */
@SpringBootApplication(scanBasePackages = "com.aws.codestar.projecttemplates", exclude = {
        CodecsAutoConfiguration.class,
        ConfigurationPropertiesAutoConfiguration.class,
        EmbeddedWebServerFactoryCustomizerAutoConfiguration.class,
        GroovyTemplateAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        JmxAutoConfiguration.class,
        ProjectInfoAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        ReactiveSecurityAutoConfiguration.class
})
@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("Starting Application");
        SpringApplication.run(Application.class, args);
    }
}
