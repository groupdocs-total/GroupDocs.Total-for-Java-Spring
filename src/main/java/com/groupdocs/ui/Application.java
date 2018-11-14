package com.groupdocs.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.net.MalformedURLException;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final String DEFAULT_CONFIGURATION_FILE = "defaultConfiguration.yml";
    private static String configurationFile = "";

    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            configurationFile = args[0];
        }
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean propertiesFactoryBean = new YamlPropertiesFactoryBean();
        ClassPathResource defaultResource = new ClassPathResource(DEFAULT_CONFIGURATION_FILE);

        File file = StringUtils.isEmpty(configurationFile) ? null : new File(configurationFile);
        if (file != null && file.exists()) {
            try {
                propertiesFactoryBean.setResolutionMethod(YamlProcessor.ResolutionMethod.OVERRIDE_AND_IGNORE);
                propertiesFactoryBean.setResources(defaultResource, new FileUrlResource(file.toURI().toURL()));
            } catch (MalformedURLException e) {
                logger.info("Can not find external configuration file. Use default.");
                propertiesFactoryBean.setResources(defaultResource);
            }
        } else {
            logger.info("Can not find external configuration file. Use default.");
            propertiesFactoryBean.setResources(defaultResource);
        }

        propertySourcesPlaceholderConfigurer.setProperties(propertiesFactoryBean.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    /**
     * Configure CORS parameters
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*");
            }
        };
    }

}
