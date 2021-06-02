package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.Sensor;
import de.fraunhofer.iosb.ilt.sta.model.builder.SensorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ItemDetailTest {

    private static Stream<Arguments> Sensor가_없거나_있을_경우() {
        // given
        Id expected = Id.tryToParse("123456");
        Sensor sensor = SensorBuilder.builder().id(expected).build();
        return Stream.of(
                Arguments.of(null, null),           // 없을 경우
                Arguments.of(sensor, expected)      // 있을 경우
        );
    }

    @ParameterizedTest
    @MethodSource("Sensor가_없거나_있을_경우")
    void toSensor(Sensor sensor, Id expected) {
        // when
        ItemDetail pm10Value = Item.generateItemDetails().getItemDetail(Item.PM10);
        Sensor actual = pm10Value.toSensor(sensor);
        log.info("====== Sensor : {}", actual);
        log.info("====== metadata : {}", actual.getMetadata());
        // then
        assertThat(actual.getId()).isEqualTo(expected);
        assertThat(actual.getName()).isEqualTo(pm10Value.getItem().getName());
        assertThat(actual.getDescription()).isEqualTo(pm10Value.getDescription());
        assertThat(actual.getEncodingType()).isEqualTo(pm10Value.getEncodingType());
    }

}