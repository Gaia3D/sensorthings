package gaia3d.airquality.domain;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class Location {
    // 경도
    private double longitude;
    // 위도
    private double latitude;
}
