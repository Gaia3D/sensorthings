package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.Sensor;
import de.fraunhofer.iosb.ilt.sta.model.builder.SensorBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Builder
@EqualsAndHashCode
public class ItemDetail {

    private final Item item;
    private final String name;
    private final String description;
    private final String definition;
    private final String encodingType;
    private final MeasureUnit measureUnit;
    private final TimeUnit timeUnit;

    private Map<String, Object> createMetadata() {
        Map<String, Object> resultMap = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                int modifiers = field.getModifiers();
                if (field.getType().equals(Logger.class) ||
                        (Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
                    continue;
                }
                resultMap.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                log.error("{}", e.getMessage(), e);
            }
        }
        return resultMap;
    }

    public ItemDetail change(Item item, String name, TimeUnit timeUnit) {
        return ItemDetail.builder()
                .item(item)
                .name(name)
                .description(this.description)
                .definition(this.definition)
                .encodingType(this.encodingType)
                .measureUnit(this.measureUnit)
                .timeUnit(timeUnit)
                .build();
    }

    public Sensor toSensor(Sensor sensor) {
        Id sensorId = null;
        if (sensor != null) {
            sensorId = sensor.getId();
        }
        return SensorBuilder.builder()
                .id(sensorId)
                .name(this.item.getName())
                .description(this.description)
                .encodingType(this.encodingType)
                .metadata(createMetadata())
                .build();
    }

}
