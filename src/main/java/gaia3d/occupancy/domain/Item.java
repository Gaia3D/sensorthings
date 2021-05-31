package gaia3d.occupancy.domain;

import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.builder.ObservedPropertyBuilder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Item {

    OCCUPANCY("occupancy", "The occupancy of each cell based on the number of people", "https://en.wikipedia.org/wiki/Occupancy"),
    OCCUPANCY_BUILD("occupancyBuild", "The occupancy of building based on the number of people", "https://en.wikipedia.org/wiki/Occupancy"),
    OCCUPANCY_FLOOR("occupancyFloor", "The occupancy of floor based on the number of people", "https://en.wikipedia.org/wiki/Occupancy");

    private final String name;
    private final String description;
    private final String definition;

    Item(String name, String description, String definition) {
        this.name = name;
        this.description = description;
        this.definition = definition;
    }

    public static Map<String, ObservedProperty> toObservedProperties() {
        return Arrays.stream(Item.values())
                .map(item -> ObservedPropertyBuilder.builder()
                        .name(item.getName())
                        .description(item.getDescription())
                        .definition(item.getDefinition())
                        .build())
                .collect(Collectors.toMap(ObservedProperty::getName, Function.identity()));
    }

}
