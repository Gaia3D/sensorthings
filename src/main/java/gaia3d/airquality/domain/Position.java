package gaia3d.airquality.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.geojson.Point;

import java.util.Optional;

@Slf4j
@Data
public class Position {

    // 경도
    private final double longitude;
    // 위도
    private final double latitude;

    public Position(String dmX, String dmY) {
        this.longitude = Optional.ofNullable(dmX).map(Double::parseDouble).orElse(Double.NaN);
        this.latitude = Optional.ofNullable(dmY).map(Double::parseDouble).orElse(Double.NaN);
        if (Double.isNaN(this.longitude) || Double.isNaN(this.latitude)) {
            log.info("====== Invalid location. longitude : {}, latitude : {}", dmX, dmY);
        }
    }

    public Point toPoint() {
        Point point = null;
        if (!Double.isNaN(this.longitude) && !Double.isNaN(this.latitude)) {
            point = new Point(this.longitude, this.latitude, 0);
        }
        return point;
    }

}
