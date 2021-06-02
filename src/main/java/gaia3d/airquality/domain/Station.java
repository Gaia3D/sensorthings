package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.*;
import de.fraunhofer.iosb.ilt.sta.model.builder.DatastreamBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.FeatureOfInterestBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.LocationBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.ThingBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractDatastreamBuilder;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
public class Station {

    public static final String LOCATION_DESCRIPTION = "대기질 측정소 위치";
    public static final String FEATURE_OF_INTEREST_DESCRIPTION = "한국환경공단 대기질 측정소";

    // 측정 항목 상세
    private static final ItemDetails itemDetails = Item.generateItemDetails();
    // AirQuality ObservedProperty Map
    private static final Map<String, ObservedProperty> observedProperties = Item.getObservedProperties(itemDetails);

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

    // Feature : 한번만 선언하여 Location, FeatureOfInterest 를 만들때 공통으로 사용하기 위해..
    private Feature feature;

    private void createFeature() {
        Feature feature = new Feature();
        Point point = this.position.toPoint();
        feature.setId(this.name);
        feature.setGeometry(point);
        this.feature = feature;
    }

    private Map<String, Object> createProperties() {
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

    public Location toLocationEntity(Location location) {
        Id locationId = null;
        if (location != null) {
            locationId = location.getId();
            location = null;    // 강제 메모리 해제
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
            thing = null;       // 강제 메모리 해제
        }
        return ThingBuilder.builder()
                .id(thingId)
                .name(this.name)
                .description(networkName)
                .properties(createProperties())
                .locations(locations)
                .build();
    }

    public FeatureOfInterest toFeatureOfInterestEntity(FeatureOfInterest foi) {
        Id foiId = null;
        if (foi != null) {
            foiId = foi.getId();
            foi = null;     // 강제 메모리 해제
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

    public Datastream toDatastream(Item item) {

        String itemName = item.getName();
        ItemDetail detail = itemDetails.getItemDetail(item);
        ObservedProperty observedProperty = observedProperties.get(itemName);

        return DatastreamBuilder.builder()
                .id(null)
                .name(itemName)
                .description(detail.getDescription())
                .observationType(AbstractDatastreamBuilder.ValueCode.OM_Observation)
                .unitOfMeasurement(detail.getMeasureUnit().toUnitOfMeasurement())
                .sensor(detail.toSensor(null))
                .observedProperty(observedProperty)
                .thing(this.toThingEntity(null, List.of(this.toLocationEntity(null))))
                .build();

    }

    public Datastream updateDatastream(Item item, Datastream datastream) {

        String itemName = item.getName();
        ItemDetail detail = itemDetails.getItemDetail(item);
        ObservedProperty observedProperty = observedProperties.get(itemName);

        Datastream ds = null;
        try {
            Id datastreamId = datastream.getId();
            Sensor sensor = datastream.getSensor();
            Thing thing = datastream.getThing();

            ds = DatastreamBuilder.builder()
                    .id(datastreamId)
                    .name(itemName)
                    .description(detail.getDescription())
                    .observationType(AbstractDatastreamBuilder.ValueCode.OM_Observation)
                    .unitOfMeasurement(detail.getMeasureUnit().toUnitOfMeasurement())
                    .sensor(detail.toSensor(sensor))
                    .observedProperty(observedProperty)
                    .thing(this.toThingEntity(thing, thing.getLocations().toList()))
                    .build();

        } catch (ServiceFailureException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public List<Datastream> toDatastreamsEntity(List<Datastream> datastreams) {
        List<Datastream> result = new ArrayList<>();
        if (datastreams.size() <= 0) {
            // 기존 datastreams 가 없을 경우
            // 새로운 datastream 생성
            result = items.stream()
                    .map(this::toDatastream)
                    .collect(Collectors.toList());
        } else {
            // 기존 datastreams 가 있을 경우
            // TODO : for loop을 2번 수행. 개선 필요.
            List<Item> duplicated = new ArrayList<>();
            for (Item item : items) {
                String itemName = item.getName();
                for (Datastream datastream : datastreams) {
                    if (datastream != null && datastream.getName().equals(itemName)) {
                        duplicated.add(item);
                        // 기존 datastream 업데이트
                        Datastream ds = this.updateDatastream(item, datastream);
                        if (ds != null) result.add(ds);
                        datastream = null;
                        break;
                    }
                }
            }
            List<Item> uniques = new ArrayList<>(items);
            uniques.removeAll(duplicated);     // 중복 제거
            // 기존 datastream에 없는 새로운 datastream 생성
            List<Datastream> additional = uniques.stream()
                    .map(this::toDatastream)
                    .collect(Collectors.toList());
            result.addAll(additional);
        }
        return result;
    }

}