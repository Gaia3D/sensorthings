package gaia3d.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RootConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

//    @Bean
//    public RestTemplate geoserverRestTemplate(PropertiesConfig propertiesConfig, RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder.rootUri("http://" + propertiesConfig.getGeoserverUrl() + "/geoserver/rest")
//                .basicAuthentication(propertiesConfig.getGeoserverUser(), propertiesConfig.getGeoserverPassword())
//                .build();
//    }

}
