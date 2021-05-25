package gaia3d.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class RootConfig {

    @Bean
    public RestTemplate airkoreaRestTemplate(PropertiesConfig propertiesConfig, RestTemplateBuilder restTemplateBuilder) {

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new Jaxb2RootElementHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        return restTemplateBuilder
                //.messageConverters(messageConverters)
                //.messageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Bean
    public RestTemplate geoserverRestTemplate(PropertiesConfig propertiesConfig, RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.rootUri("http://" + propertiesConfig.getGeoserverUrl() + "/geoserver/rest")
                .basicAuthentication(propertiesConfig.getGeoserverUser(), propertiesConfig.getGeoserverPassword())
                .build();
    }

}
