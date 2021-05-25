package gaia3d.airkorea.application;

import gaia3d.airquality.domain.Stations;
import org.springframework.stereotype.Service;

@Service
public class AirKoreaServiceOpenApiMock implements AirKoreaService {

    @Override
    public Stations getStations() {
        return null;
    }

}
