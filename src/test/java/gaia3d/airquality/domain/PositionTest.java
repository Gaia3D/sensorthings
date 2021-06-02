package gaia3d.airquality.domain;

import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {

    @Test
    void toPointTest() {
        Position position = new Position("126.975961", "37.564639");
        Point actual = position.toPoint();
        assertThat(actual.getCoordinates()).isEqualTo(new LngLatAlt(position.getLongitude(), position.getLatitude(), 0));
    }

    @Test
    void toPointNullTest() {
        Position position = new Position(null, null);
        Point actual = position.toPoint();
        assertThat(actual).isEqualTo(null);
    }

}