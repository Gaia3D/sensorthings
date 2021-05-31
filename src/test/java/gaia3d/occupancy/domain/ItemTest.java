package gaia3d.occupancy.domain;

import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void toObservedProperties() {
        // given
        ObservedProperty[] expected = new ObservedProperty[]{
                new ObservedProperty("occupancy", "https://en.wikipedia.org/wiki/Occupancy", "The occupancy of each cell based on the number of people"),
                new ObservedProperty("occupancyBuild", "https://en.wikipedia.org/wiki/Occupancy", "The occupancy of building based on the number of people"),
                new ObservedProperty("occupancyFloor", "https://en.wikipedia.org/wiki/Occupancy", "The occupancy of floor based on the number of people")
        };
        // when
        Map<String, ObservedProperty> observedPropertyMap = Item.toObservedProperties();
        // then
        assertThat(observedPropertyMap).containsValues(expected);
    }
}