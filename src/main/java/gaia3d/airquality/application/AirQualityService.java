package gaia3d.airquality.application;

import gaia3d.airkorea.dto.AirKoreaHeader;
import gaia3d.airkorea.dto.AirKoreaResponse;
import gaia3d.airkorea.dto.AirKoreaResponseWrapper;
import gaia3d.airkorea.dto.StationsResponse;
import gaia3d.airquality.domain.Stations;
import org.jetbrains.annotations.Nullable;

public interface AirQualityService {

    Stations getStations();

    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AirQualityService.class);

    @Nullable
    default Stations airKoreaResponseToStations(AirKoreaResponseWrapper<StationsResponse> airKoreaResponse) {
        Stations stations;
        AirKoreaResponse<StationsResponse> airKoreaStationResponse = airKoreaResponse.getResponse();
        log.info("====== header : {}", airKoreaStationResponse.getHeader().toString());
        //log.info("====== body : {}", airKoreaStationResponse.getBody().toString());
        AirKoreaHeader airKoreaHeader = airKoreaStationResponse.getHeader();
        if (!airKoreaHeader.ok()) return null;
        log.info("====== totalCnt : {}", airKoreaStationResponse.getBody().getTotalCount());
        stations = airKoreaStationResponse.getBody().toStations();
        log.info("====== stations : {}", stations.toString());
        return stations;
    }

}
