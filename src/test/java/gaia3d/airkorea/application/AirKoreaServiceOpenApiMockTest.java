package gaia3d.airkorea.application;

import gaia3d.airquality.application.AirQualityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AirKoreaServiceOpenApiMockTest {

    @Autowired
    private AirQualityService airKoreaServiceOpenApiMock;

    @Test
    void getStations() {
        airKoreaServiceOpenApiMock.getStations();
    }
}