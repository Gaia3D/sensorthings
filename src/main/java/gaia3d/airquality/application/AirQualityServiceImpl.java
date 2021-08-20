package gaia3d.airquality.application;

import de.fraunhofer.iosb.ilt.sta.model.Location;
import gaia3d.airkorea.application.AirKoreaService;
import gaia3d.airquality.domain.Station;
import gaia3d.airquality.domain.Stations;
import gaia3d.config.PropertiesConfig;
import gaia3d.frost.application.FrostServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AirQualityServiceImpl implements AirQualityService {

    private final PropertiesConfig propertiesConfig;
    private final FrostServerService frostServerService;

    private final AirKoreaService airKoreaServiceOpenApiMock;
    private final AirKoreaService airKoreaServiceOpenApi;

    public AirQualityServiceImpl(PropertiesConfig propertiesConfig, FrostServerService frostServerService,
                                 AirKoreaService airKoreaServiceOpenApiMock, AirKoreaService airKoreaServiceOpenApi) {
        this.propertiesConfig = propertiesConfig;
        this.frostServerService = frostServerService;
        this.airKoreaServiceOpenApiMock = airKoreaServiceOpenApiMock;
        this.airKoreaServiceOpenApi = airKoreaServiceOpenApi;
    }

    public void run() {
        AirKoreaService airKoreaService;
        if (propertiesConfig.isMockEnable()) {
            airKoreaService = this.airKoreaServiceOpenApiMock;
        } else {
            airKoreaService = this.airKoreaServiceOpenApi;
        }
        Stations stations = airKoreaService.getStations();
        for (Station station : stations.getStations()) {
            // initial location entity
            Location location = initializeLocation(station);
        }
    }

    private Location initializeLocation(Station station) {
        String address = station.getAddr();
        Location existingLocation = frostServerService.findLocationByName(address);
        Location location = station.toLocationEntity(existingLocation);
        if (existingLocation == null) {
            frostServerService.create(location);
        } else {
            frostServerService.update(location);
        }
        return location;
    }

}
