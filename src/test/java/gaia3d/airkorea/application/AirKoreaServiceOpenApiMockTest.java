package gaia3d.airkorea.application;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AirKoreaServiceOpenApiMockTest {

    @Autowired
    private AirKoreaService airKoreaServiceOpenApiMock;

    @Test
    void getStations() {
        airKoreaServiceOpenApiMock.getStations();
    }
}