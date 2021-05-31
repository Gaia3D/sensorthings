package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.FeatureOfInterest;
import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.builder.FeatureOfInterestBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.LocationBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.ThingBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractFeatureOfInterestBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractLocationBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.geojson.Feature;
import org.geojson.Point;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Builder
public class Station {

    public static final String LOCATION_DESCRIPTION = "대기질 측정소 위치";
    public static final String FEATURE_OF_INTEREST_DESCRIPTION = "한국환경공단 대기질 측정소";

    // 측정소 명
    private String name;
    // 위치
    private Position position;
    // 측정 항목
    private List<Item> items;
    // 측정망 명
    private String networkName;
    // 설치년도
    private Integer year;
    // 주소
    private String addr;

    // Feature
    private Feature feature;

    private Map<String, Object> toProperties() {
        Map<String, Object> resultMap = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.getType().equals(Logger.class) || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                resultMap.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                log.error("{}", e.getMessage(), e);
            }
        }
        return resultMap;
    }

    public Location toLocationEntity(Location location) {
        Id locationId = null;
        if (location != null) {
            locationId = location.getId();
        }
        createFeature();
        return LocationBuilder.builder()
                .id(locationId)
                .name(this.addr)
                .encodingType(AbstractLocationBuilder.ValueCode.GeoJSON)
                .description(LOCATION_DESCRIPTION)
                .location(this.feature)
                .build();
    }

    public Thing toThingEntity(Thing thing, List<Location> locations) {
        Id thingId = null;
        if (thing != null) {
            thingId = thing.getId();
        }
        return ThingBuilder.builder()
                .id(thingId)
                .name(this.name)
                .description(networkName)
                .properties(toProperties())
                .locations(locations)
                .build();
    }

    public FeatureOfInterest toFeatureOfInterestEntity(FeatureOfInterest foi) {
        Id foiId = null;
        if (foi != null) {
            foiId = foi.getId();
        }

        if (this.feature == null) {
            createFeature();
        }

        return FeatureOfInterestBuilder.builder()
                .id(foiId)
                .name(this.name)
                .description(FEATURE_OF_INTEREST_DESCRIPTION)
                .encodingType(AbstractFeatureOfInterestBuilder.ValueCode.GeoJSON)
                .feature(this.feature)
                .build();
    }

    private void createFeature() {
        Feature feature = new Feature();
        Point point = this.position.toPoint();
        feature.setId(this.name);
        feature.setGeometry(point);
        this.feature = feature;
    }

}