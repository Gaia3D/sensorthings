package gaia3d.airkorea.application;

import gaia3d.airquality.application.AirQualityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AirKoreaServiceOpenApiTest {

    @Autowired
    private AirQualityService airKoreaServiceOpenApi;

    @Test
    void getStations() {
        airKoreaServiceOpenApi.getStations();
    }
}