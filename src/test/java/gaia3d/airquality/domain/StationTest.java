package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.*;
import de.fraunhofer.iosb.ilt.sta.model.builder.*;
import lombok.extern.slf4j.Slf4j;
import org.geojson.Feature;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static gaia3d.airquality.domain.Item.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class StationTest {

    private Position position;
    private Station station;
    private Location location;
    private List<Datastream> datastreams;
    private Datastream pm10;
    private Datastream pm25;
    private Datastream so2;
    private Datastream o3;

    @BeforeEach
    void setUp() {
        position = new Position("126.975961", "37.564639");
        // given
        station = Station.builder()
                .name("중구")
                .position(position)
                .items(List.of(SO2, CO, O3, NO2, PM10, PM25))
                .networkName("도시대기")
                .year(1995)
                .addr("서울 중구 덕수궁길 15시청서소문별관 3동")
                .build();
        location = LocationBuilder.builder().id(Id.tryToParse("123456")).build();

        pm10 = DatastreamBuilder.builder().id(Id.tryToParse("1")).name(PM10.getName())
                .sensor(SensorBuilder.builder().id(Id.tryToParse("11")).build())
                .thing(ThingBuilder.builder().id(Id.tryToParse("111")).build())
                .build();
        pm25 = DatastreamBuilder.builder().id(Id.tryToParse("2")).name(PM25.getName())
                .sensor(SensorBuilder.builder().id(Id.tryToParse("22")).build())
                .thing(ThingBuilder.builder().id(Id.tryToParse("222")).build())
                .build();
        so2 = DatastreamBuilder.builder().id(Id.tryToParse("3")).name(SO2.getName())
                .sensor(SensorBuilder.builder().id(Id.tryToParse("33")).build())
                .thing(ThingBuilder.builder().id(Id.tryToParse("333")).build())
                .build();
        o3 = DatastreamBuilder.builder().id(Id.tryToParse("4")).name(O3.getName())
                .sensor(SensorBuilder.builder().id(Id.tryToParse("44")).build())
                .thing(ThingBuilder.builder().id(Id.tryToParse("444")).build())
                .build();
        datastreams = List.of(pm10, pm25, so2, o3);
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
        assertThat(actual.getDescription()).isEqualTo(Station.LOCATION_DESCRIPTION);

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
            assertThat(l.getDescription()).isEqualTo(Station.LOCATION_DESCRIPTION);

            Feature feature = (Feature) l.getLocation();
            Point point = (Point) feature.getGeometry();

            assertThat(feature.getId()).isEqualTo(station.getName());
            assertThat(point.getCoordinates()).isEqualTo(new LngLatAlt(126.975961, 37.564639, 0));
        });
    }

    private static Stream<Arguments> FeatureOfInterest가_없거나_있을_경우() {
        // given
        Id expected = Id.tryToParse("123456");
        FeatureOfInterest featureOfInterest = FeatureOfInterestBuilder.builder().id(expected).build();
        return Stream.of(
                Arguments.of(null, null),       // 없을 경우
                Arguments.of(featureOfInterest, expected)   // 있을 경우
        );
    }

    @ParameterizedTest
    @MethodSource("FeatureOfInterest가_없거나_있을_경우")
    void toFeatureOfInterestEntity(FeatureOfInterest featureOfInterest, Id expected) {
        // given, when
        FeatureOfInterest actual = station.toFeatureOfInterestEntity(featureOfInterest);
        log.info("======= FeatureOfInterest : {}", actual);

        Feature feature = new Feature();
        Point point = position.toPoint();
        feature.setId(station.getName());
        feature.setGeometry(point);

        // then
        assertThat(actual.getId()).isEqualTo(expected);
        assertThat(actual.getFeature()).isEqualTo(feature);
    }

    @Test
    void 기존_데이터스트림이_없을_경우() {
        // given, when
        List<Datastream> datastreams = List.of();
        List<Datastream> actual = station.toDatastreamsEntity(datastreams);

        // then
        assertThat(actual).hasSize(6);
        assertThat(actual).contains(
                station.toDatastream(SO2), station.toDatastream(CO),
                station.toDatastream(O3), station.toDatastream(NO2),
                station.toDatastream(PM10), station.toDatastream(PM25)
        );
        actual.forEach(datastream -> {
            log.info("======= Datastream : {}", datastream);
            assertThat(datastream.getId()).isEqualTo(null);
        });
    }

    @Test
    void 기존_데이터스트림과_중복되지_않는_경우() {
        // given, when
        station = Station.builder()
                .name("중구")
                .position(position)
                .items(List.of(CO, NO2))
                .networkName("도시대기")
                .year(1995)
                .addr("서울 중구 덕수궁길 15시청서소문별관 3동")
                .build();
        List<Datastream> actual = station.toDatastreamsEntity(datastreams);

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual).contains(station.toDatastream(CO), station.toDatastream(NO2));
        actual.forEach(datastream -> {
            log.info("======= Datastream : {}", datastream);
            assertThat(datastream.getId()).isEqualTo(null);
        });
    }

    @Test
    void 기존_데이터스트림보다_많은_경우() {
        // given, when
        List<Datastream> actual = station.toDatastreamsEntity(datastreams);
        // then
        assertThat(actual).hasSize(6);
        assertThat(actual).contains(
                station.updateDatastream(SO2, so2), station.toDatastream(CO),
                station.updateDatastream(O3, o3), station.toDatastream(NO2),
                station.updateDatastream(PM10, pm10), station.updateDatastream(PM25, pm25)
        );
        actual.forEach(datastream -> log.info("======= Datastream : {}", datastream));
    }

    @Test
    void 기존_데이터스트림보다_적은_경우() {
        // given, when
        station = Station.builder()
                .name("중구")
                .position(position)
                .items(List.of(PM10, PM25, NO2))
                .networkName("도시대기")
                .year(1995)
                .addr("서울 중구 덕수궁길 15시청서소문별관 3동")
                .build();
        List<Datastream> actual = station.toDatastreamsEntity(datastreams);
        // then
        assertThat(actual).hasSize(3);
        assertThat(actual).contains(
                station.updateDatastream(PM10, pm10),
                station.updateDatastream(PM25, pm25),
                station.toDatastream(NO2)
        );
        actual.forEach(datastream -> log.info("======= Datastream : {}", datastream));
    }

}