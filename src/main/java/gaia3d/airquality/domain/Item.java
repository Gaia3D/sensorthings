package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.builder.ObservedPropertyBuilder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Item {

    PM10("pm10Value", "미세먼지(PM10) Particulates", "https://en.wikipedia.org/wiki/Particulates", TimeUnit.HOUR),
    PM25("pm25Value", "미세먼지(PM2.5) Particulates", "https://en.wikipedia.org/wiki/Particulates", TimeUnit.HOUR),
    SO2("so2Value", "아황산가스 농도 Sulfur_dioxide", "https://en.wikipedia.org/wiki/Sulfur_dioxide", TimeUnit.HOUR),
    CO("coValue", "일산화탄소 농도 Carbon_monoxide", "https://en.wikipedia.org/wiki/Carbon_monoxide", TimeUnit.HOUR),
    O3("o3Value", "오존 농도 Ozone", "https://en.wikipedia.org/wiki/Ozone", TimeUnit.HOUR),
    NO2("no2Value", "이산화질소 Nitrogen_dioxide", "https://en.wikipedia.org/wiki/Nitrogen_dioxide", TimeUnit.HOUR),
    PM10Daily("pm10ValueDaily", "미세먼지(PM10) Particulates", "https://en.wikipedia.org/wiki/Particulates", TimeUnit.DAILY),
    PM25Daily("pm25ValueDaily", "미세먼지(PM2.5) Particulates", "https://en.wikipedia.org/wiki/Particulates", TimeUnit.DAILY),
    SO2Daily("so2ValueDaily", "아황산가스 농도 Sulfur_dioxide", "https://en.wikipedia.org/wiki/Sulfur_dioxide", TimeUnit.DAILY),
    CODaily("coValueDaily", "일산화탄소 농도 Carbon_monoxide", "https://en.wikipedia.org/wiki/Carbon_monoxide", TimeUnit.DAILY),
    O3Daily("o3ValueDaily", "오존 농도 Ozone", "https://en.wikipedia.org/wiki/Ozone", TimeUnit.DAILY),
    NO2Daily("no2ValueDaily", "이산화질소 Nitrogen_dioxide", "https://en.wikipedia.org/wiki/Nitrogen_dioxide", TimeUnit.DAILY);

    private final String name;
    private final String description;
    private final String definition;
    private final TimeUnit type;

    Item(String name, String description, String definition, TimeUnit type) {
        this.name = name;
        this.description = description;
        this.definition = definition;
        this.type = type;
    }

    public static Map<String, ObservedProperty> toObservedPropertiesByType(TimeUnit type) {
        return Arrays.stream(Item.values())
                .filter(item -> item.type == type)
                .map(item -> ObservedPropertyBuilder.builder()
                        .name(item.getName())
                        .description(item.getDescription())
                        .definition(item.getDefinition())
                        .build())
                .collect(Collectors.toMap(ObservedProperty::getName, Function.identity()));
    }

}
