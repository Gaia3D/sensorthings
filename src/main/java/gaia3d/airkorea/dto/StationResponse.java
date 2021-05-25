package gaia3d.airkorea.dto;

import gaia3d.airquality.domain.Location;
import gaia3d.airquality.domain.ObservedProperty;
import gaia3d.airquality.domain.Station;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class StationResponse {

    private String dmX;
    private String item;
    private String mangName;
    private String year;
    private String addr;
    private String stationName;
    private String dmY;


    public Station toStation() {

        Location location = Location.builder()
                .longitude(Double.parseDouble(this.dmX))
                .latitude(Double.parseDouble(this.dmY))
                .build();

        String[] items = this.item.trim().split(",");
        List<ObservedProperty> observedProperties = Arrays.stream(items)
                .map(String::trim)
                .map(item -> item.replace(".", ""))
                .map(ObservedProperty::valueOf)
                .collect(Collectors.toList());

        Integer year = Optional.ofNullable(this.year)
                .map(Integer::valueOf)
                .orElse(null);

        return Station.builder()
                .name(this.stationName)
                .location(location)
                .observedProperties(observedProperties)
                .networkName(this.mangName)
                .year(year)
                .addr(this.addr)
                .build();
    }
}
