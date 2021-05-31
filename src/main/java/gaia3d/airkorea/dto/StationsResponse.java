package gaia3d.airkorea.dto;

import gaia3d.airquality.domain.Station;
import gaia3d.airquality.domain.Stations;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class StationsResponse {
    private int totalCount;
    private List<StationResponse> items;
    private int pageNo;
    private int numOfRows;

    public Stations toStations() {
        List<Station> stationList = items.stream()
                .map(StationResponse::toStation)
                .collect(Collectors.toList());
        return Stations.builder()
                .stations(stationList)
                .build();
    }

}
