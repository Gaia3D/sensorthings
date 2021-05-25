package gaia3d.airkorea.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gaia3d.airkorea.dto.AirKoreaHeader;
import gaia3d.airkorea.dto.AirKoreaResponse;
import gaia3d.airkorea.dto.AirKoreaResponseWrapper;
import gaia3d.airkorea.dto.StationsResponse;
import gaia3d.airquality.domain.Stations;
import gaia3d.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Service
public class AirKoreaServiceOpenApi implements AirKoreaService {

    private final PropertiesConfig propertiesConfig;
    private final RestTemplate airkoreaRestTemplate;
    private final ObjectMapper objectMapper;

    public AirKoreaServiceOpenApi(PropertiesConfig propertiesConfig, RestTemplate airkoreaRestTemplate, ObjectMapper objectMapper) {
        this.propertiesConfig = propertiesConfig;
        this.airkoreaRestTemplate = airkoreaRestTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Stations getStations() {
        log.info("api 연동 [한국환경공단_에어코리아_측정소정보]");
        String url = propertiesConfig.getAirkoreaServiceUrl() + "/MsrstnInfoInqireSvc/getMsrstnList";
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("serviceKey", UriUtils.encode(propertiesConfig.getAirkoreaAuthKey(), StandardCharsets.UTF_8))
                .queryParam("numOfRows", 10000)
                .queryParam("pageNo", 1)
                .queryParam("returnType", "json")
                .build(true)
                .toUri();
        log.info("====== url : {}", uri.toString());
        ResponseEntity<?> response = airkoreaRestTemplate.getForEntity(uri, String.class);
        return responseToStations(response);
    }

    @Nullable
    private Stations responseToStations(ResponseEntity<?> response) {
        Stations stations;
        try {
            Object body = Objects.requireNonNull(response.getBody());
            AirKoreaResponseWrapper<StationsResponse> airKoreaResponse = objectMapper.readValue(body.toString(), new TypeReference<>() {});
            AirKoreaResponse<StationsResponse> airKoreaStationResponse = airKoreaResponse.getResponse();
            log.info("====== header : {}", airKoreaStationResponse.getHeader().toString());
            //log.info("====== body : {}", airKoreaStationResponse.getBody().toString());
            AirKoreaHeader airKoreaHeader = airKoreaStationResponse.getHeader();
            if (!airKoreaHeader.ok()) return null;
            log.info("====== totalCnt : {}", airKoreaStationResponse.getBody().getTotalCount());
            stations = airKoreaStationResponse.getBody().toStations();
            log.info("====== stations : {}", stations.toString());
        } catch (JsonProcessingException e) {
            log.error("====== error message : {}", response.getBody());
            return null;
        }
        return stations;
    }


}