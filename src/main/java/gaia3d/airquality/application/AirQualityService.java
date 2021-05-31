package gaia3d.airquality.application;

import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import gaia3d.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class AirQualityService {

    private SensorThingsService service;

    public AirQualityService(PropertiesConfig propertiesConfig) {
        try {
            URL serviceEndpoint = new URL(propertiesConfig.getFrostServiceEndpoint());
            this.service = new SensorThingsService(serviceEndpoint);
        } catch (MalformedURLException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

}
