package gaia3d.airkorea.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gaia3d.airkorea.dto.AirKoreaResponseWrapper;
import gaia3d.airkorea.dto.StationsResponse;
import gaia3d.airquality.application.AirQualityService;
import gaia3d.airquality.domain.Stations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class AirKoreaServiceOpenApiMock implements AirKoreaService {

    private final ObjectMapper objectMapper;

    public AirKoreaServiceOpenApiMock(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Stations getStations() {
        log.info("mock 데이터 [한국환경공단_에어코리아_측정소정보]");
        ClassPathResource resource = new ClassPathResource("airquality/airQualityStation.json");
        Stations stations;
        try {
            Path path = Paths.get(resource.getURI());
            log.info("====== file path : {}", path.toAbsolutePath());
            AirKoreaResponseWrapper<StationsResponse> airKoreaResponse = objectMapper.readValue(path.toFile(), new TypeReference<>() {});
            stations = airKoreaResponseToStations(airKoreaResponse);
            if (stations == null) return null;
        } catch (IOException e) {
            log.error("====== error message : {}", e.getMessage(), e);
            return null;
        }
        return stations;
    }

}
