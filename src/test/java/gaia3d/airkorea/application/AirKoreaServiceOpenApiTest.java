package gaia3d.airkorea.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AirKoreaServiceOpenApiTest {

    @Autowired
    private AirKoreaService airKoreaServiceOpenApi;

    @Test
    void getStations() {
        airKoreaServiceOpenApi.getStations();
    }
}