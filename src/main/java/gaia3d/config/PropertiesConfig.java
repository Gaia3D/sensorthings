package gaia3d.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:sensorthings.properties")
public class PropertiesConfig {

    // local : 로컬, develop : 개발, product : 운영
    @Value("${sensorthings.profile}")
    private Profile profile;

    @Value("${geoserver.url}")
    private String geoserverUrl;
    @Value("${geoserver.user}")
    private String geoserverUser;
    @Value("${geoserver.password}")
    private String geoserverPassword;

}
