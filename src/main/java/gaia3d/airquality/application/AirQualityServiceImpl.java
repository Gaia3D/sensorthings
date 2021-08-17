package gaia3d.airquality.application;

import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import gaia3d.airkorea.application.AirKoreaService;
import gaia3d.airkorea.application.AirKoreaServiceOpenApi;
import gaia3d.airkorea.application.AirKoreaServiceOpenApiMock;
import gaia3d.airquality.domain.Stations;
import gaia3d.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class AirQualityServiceImpl implements AirQualityService {

    private PropertiesConfig propertiesConfig;
    private SensorThingsService service;

    @Autowired
    private AirKoreaService airKoreaServiceOpenApiMock;

    @Autowired
    private AirKoreaService airKoreaServiceOpenApi;


    public AirQualityServiceImpl(PropertiesConfig propertiesConfig) {
        try {
            this.propertiesConfig = propertiesConfig;
            URL serviceEndpoint = new URL(this.propertiesConfig.getFrostServiceEndpoint());
            this.service = new SensorThingsService(serviceEndpoint);
        } catch (MalformedURLException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    public void run() {
        AirKoreaService airKoreaService = null;
        if (propertiesConfig.isMockEnable()) {
            airKoreaService = this.airKoreaServiceOpenApiMock;
        } else {
            airKoreaService = this.airKoreaServiceOpenApi;
        }
        Stations stations = airKoreaService.getStations();
        
    }

}
