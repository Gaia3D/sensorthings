package gaia3d.airquality.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AirQualityServiceImplTest {

    @Autowired
    AirQualityService airQualityService;

    @Test
    void run() {
        airQualityService.run();
    }
}