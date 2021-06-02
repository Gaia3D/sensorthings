package gaia3d.airkorea.dto;

import gaia3d.airquality.domain.Position;
import gaia3d.airquality.domain.Item;
import gaia3d.airquality.domain.Station;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class StationResponse {

    public static final String PROPERTY_SPLIT_REGEX = ",";
    private String dmX;
    private String dmY;
    private String item;
    private String mangName;
    private String year;
    private String addr;
    private String stationName;


    public Station toStation() {

        // build position
        Position position = new Position(this.dmX, this.dmY);

        // build observedProperties
        String[] items = this.item.trim().split(PROPERTY_SPLIT_REGEX);
        List<Item> observedProperties = Arrays.stream(items)
                .map(String::trim)
                // PM2.5에서 .을 제거하여 enum PM25에 매핑
                .map(item -> item.replace(".", ""))
                .map(Item::valueOf)
                .collect(Collectors.toList());

        // build year
        Integer year = Optional.ofNullable(this.year).map(Integer::valueOf).orElse(null);

        return Station.builder()
                .name(this.stationName)
                .position(position)
                .items(observedProperties)
                .networkName(this.mangName)
                .year(year)
                .addr(this.addr)
                .build();

    }
}
