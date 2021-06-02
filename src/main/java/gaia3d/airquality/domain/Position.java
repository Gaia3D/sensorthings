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
    // 높이
    private final double altitude;

    /**
     * 2차원 Point 생성자
     * @param dmX 경도
     * @param dmY 위도
     */
    public Position(String dmX, String dmY) {
        this.longitude = Optional.ofNullable(dmX).map(Double::parseDouble).orElse(Double.NaN);
        this.latitude = Optional.ofNullable(dmY).map(Double::parseDouble).orElse(Double.NaN);
        this.altitude = 0;
        if (Double.isNaN(this.longitude) || Double.isNaN(this.latitude)) {
            log.info("====== Invalid location. longitude : {}, latitude : {}", dmX, dmY);
        }
    }

    /**
     * 3차원 Point 생성자
     * @param dmX 경도
     * @param dmY 위도
     * @param altitude 높이
     */
    public Position(String dmX, String dmY, String altitude) {
        this.longitude = Optional.ofNullable(dmX).map(Double::parseDouble).orElse(Double.NaN);
        this.latitude = Optional.ofNullable(dmY).map(Double::parseDouble).orElse(Double.NaN);
        this.altitude = Optional.ofNullable(altitude).map(Double::parseDouble).orElse(Double.NaN);
        if (Double.isNaN(this.longitude) || Double.isNaN(this.latitude) || Double.isNaN(this.altitude)) {
            log.info("====== Invalid location. longitude : {}, latitude : {}, altitude : {}", dmX, dmY, altitude);
        }
    }

    /**
     * Point 변경하기
     * @return Point
     */
    public Point toPoint() {
        Point point = null;
        if (!Double.isNaN(this.longitude) && !Double.isNaN(this.latitude)) {
            point = new Point(this.longitude, this.latitude, this.altitude);
        }
        return point;
    }

}
