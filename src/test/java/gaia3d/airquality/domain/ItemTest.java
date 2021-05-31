package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ItemTest {

    @Test
    void toObservedPropertyByHour() {
        // given
        ObservedProperty[] expected = new ObservedProperty[]{
                new ObservedProperty("pm10Value", "https://en.wikipedia.org/wiki/Particulates", "미세먼지(PM10) Particulates"),
                new ObservedProperty("pm25Value", "https://en.wikipedia.org/wiki/Particulates", "미세먼지(PM2.5) Particulates"),
                new ObservedProperty("so2Value", "https://en.wikipedia.org/wiki/Sulfur_dioxide", "아황산가스 농도 Sulfur_dioxide"),
                new ObservedProperty("coValue", "https://en.wikipedia.org/wiki/Carbon_monoxide", "일산화탄소 농도 Carbon_monoxide"),
                new ObservedProperty("o3Value", "https://en.wikipedia.org/wiki/Ozone", "오존 농도 Ozone"),
                new ObservedProperty("no2Value", "https://en.wikipedia.org/wiki/Nitrogen_dioxide", "이산화질소 Nitrogen_dioxide")
        };
        // when
        Map<String, ObservedProperty> observedPropertyMap = Item.toObservedPropertiesByType(TimeUnit.HOUR);
        // then
        assertThat(observedPropertyMap).containsValues(expected);
    }

    @Test
    void toObservedPropertyByDaily() {
        // given
        ObservedProperty[] expected = new ObservedProperty[]{
                new ObservedProperty("pm10ValueDaily", "https://en.wikipedia.org/wiki/Particulates", "미세먼지(PM10) Particulates"),
                new ObservedProperty("pm25ValueDaily", "https://en.wikipedia.org/wiki/Particulates", "미세먼지(PM2.5) Particulates"),
                new ObservedProperty("so2ValueDaily", "https://en.wikipedia.org/wiki/Sulfur_dioxide", "아황산가스 농도 Sulfur_dioxide"),
                new ObservedProperty("coValueDaily", "https://en.wikipedia.org/wiki/Carbon_monoxide", "일산화탄소 농도 Carbon_monoxide"),
                new ObservedProperty("o3ValueDaily", "https://en.wikipedia.org/wiki/Ozone", "오존 농도 Ozone"),
                new ObservedProperty("no2ValueDaily", "https://en.wikipedia.org/wiki/Nitrogen_dioxide", "이산화질소 Nitrogen_dioxide")
        };
        // when
        Map<String, ObservedProperty> observedPropertyMap = Item.toObservedPropertiesByType(TimeUnit.DAILY);
        // then
        assertThat(observedPropertyMap).containsValues(expected);
    }

}