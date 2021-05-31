package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.builder.LocationBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.ThingBuilder;
import lombok.extern.slf4j.Slf4j;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static gaia3d.airquality.domain.ObservedProperty.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class StationTest {

    private Station station;
    private Location location;

    @BeforeEach
    void setUp() {
        Position position = new Position("126.975961", "37.564639");
        // given
        station = Station.builder()
                .name("중구")
                .position(position)
                .observedProperties(List.of(SO2, CO, O3, NO2, PM10, PM25))
                .networkName("도시대기")
                .year(1995)
                .addr("서울 중구 덕수궁길 15시청서소문별관 3동")
                .build();
        location = LocationBuilder.builder().id(Id.tryToParse("123456")).build();
    }

    private static Stream<Arguments> Location이_없거나_있을_경우() {
        // given
        Id expected = Id.tryToParse("123456");
        Location location = LocationBuilder.builder().id(expected).build();
        return Stream.of(
                Arguments.of(null, null),           // 없을 경우
                Arguments.of(location, expected)    // 있을 경우
        );
    }

    @ParameterizedTest
    @MethodSource("Location이_없거나_있을_경우")
    void toLocationEntityTest(Location location, Id expected) {
        // when
        Location actual = station.toLocationEntity(location);
        Feature feature = (Feature) actual.getLocation();
        Point point = (Point) feature.getGeometry();
        log.info("====== location : {}", actual);
        log.info("====== feature : {}", actual.getLocation());

        // then
        assertThat(actual.getId()).isEqualTo(expected);
        assertThat(actual.getName()).isEqualTo(station.getAddr());
        assertThat(actual.getDescription()).isEqualTo(Station.STATION_DESCRIPTION);

        assertThat(feature.getId()).isEqualTo(station.getName());
        assertThat(point.getCoordinates()).isEqualTo(new LngLatAlt(126.975961, 37.564639, 0));
    }

    private static Stream<Arguments> Thing이_없거나_있을_경우() {
        // given
        Id expected = Id.tryToParse("123456");
        Thing thing = ThingBuilder.builder().id(expected).build();
        return Stream.of(
                Arguments.of(null, null),       // 없을 경우
                Arguments.of(thing, expected)   // 있을 경우
        );
    }

    @ParameterizedTest
    @MethodSource("Thing이_없거나_있을_경우")
    void toThingEntityTest(Thing thing, Id expected) {
        // when
        Thing actual = station.toThingEntity(thing, List.of(station.toLocationEntity(location)));
        log.info("======= id : {}, name : {}, desc : {}", actual.getId(), actual.getName(), actual.getDescription());
        log.info("======= property : {}", actual.getProperties());
        actual.getLocations().forEach(l -> {
            log.info("====== location : {}", l);
            log.info("====== feature : {}", l.getLocation());
        });

        // then
        assertThat(actual.getId()).isEqualTo(expected);
        assertThat(actual.getName()).isEqualTo(station.getName());
        assertThat(actual.getDescription()).isEqualTo(station.getNetworkName());

        actual.getLocations().forEach(l -> {
            assertThat(l.getId()).isEqualTo(location.getId());
            assertThat(l.getName()).isEqualTo(station.getAddr());
            assertThat(l.getDescription()).isEqualTo(Station.STATION_DESCRIPTION);

            Feature feature = (Feature) l.getLocation();
            Point point = (Point) feature.getGeometry();

            assertThat(feature.getId()).isEqualTo(station.getName());
            assertThat(point.getCoordinates()).isEqualTo(new LngLatAlt(126.975961, 37.564639, 0));
        });
    }

}