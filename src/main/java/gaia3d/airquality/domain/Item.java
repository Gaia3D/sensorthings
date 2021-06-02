package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.builder.ObservedPropertyBuilder;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum Item {

    PM10("pm10Value"),
    PM25("pm25Value"),
    SO2("so2Value"),
    CO("coValue"),
    O3("o3Value"),
    NO2("no2Value"),
    PM10_DAILY("pm10ValueDaily"),
    PM25_DAILY("pm25ValueDaily"),
    SO2_DAILY("so2ValueDaily"),
    CO_DAILY("coValueDaily"),
    O3_DAILY("o3ValueDaily"),
    NO2_DAILY("no2ValueDaily");

    private final String name;

    Item(String name) {
        this.name = name;
    }

    public static ItemDetails generateItemDetails() {

        ItemDetail pm10Value = ItemDetail.builder().item(Item.PM10).name("미세먼지(PM10)")
                .description("미세먼지(PM10) Particulates")
                .definition("https://en.wikipedia.org/wiki/Particulates")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.UGM3).timeUnit(TimeUnit.HOUR).build();
        ItemDetail pm10ValueDaily = pm10Value.change(Item.PM10_DAILY, "미세먼지(PM10) 24시간", TimeUnit.DAILY);

        ItemDetail pm25Value = ItemDetail.builder().item(Item.PM25).name("미세먼지(PM2.5)")
                .description("미세먼지(PM2.5) Particulates")
                .definition("https://en.wikipedia.org/wiki/Particulates")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.UGM3).timeUnit(TimeUnit.HOUR).build();
        ItemDetail pm25ValueDaily = pm25Value.change(Item.PM25_DAILY, "미세먼지(PM2.5) 24시간", TimeUnit.DAILY);

        ItemDetail so2Value = ItemDetail.builder().item(Item.SO2).name("아황산가스 농도")
                .description("아황산가스 농도 Sulfur_dioxide")
                .definition("https://en.wikipedia.org/wiki/Sulfur_dioxide")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.PPM).timeUnit(TimeUnit.HOUR).build();
        ItemDetail so2ValueDaily = so2Value.change(Item.SO2_DAILY, "아황산가스 농도 24시간", TimeUnit.DAILY);

        ItemDetail coValue = ItemDetail.builder().item(Item.CO).name("일산화탄소 농도")
                .description("일산화탄소 농도 Carbon_monoxide")
                .definition("https://en.wikipedia.org/wiki/Carbon_monoxide")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.PPM).timeUnit(TimeUnit.HOUR).build();
        ItemDetail coValueDaily = coValue.change(Item.CO_DAILY, "일산화탄소 농도 24시간", TimeUnit.DAILY);

        ItemDetail o3Value = ItemDetail.builder().item(Item.O3).name("오존 농도")
                .description("오존 농도 Ozone")
                .definition("https://en.wikipedia.org/wiki/Ozone")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.PPM).timeUnit(TimeUnit.HOUR).build();
        ItemDetail o3ValueDaily = o3Value.change(Item.O3_DAILY, "오존 농도 24시간", TimeUnit.DAILY);

        ItemDetail no2Value = ItemDetail.builder().item(Item.NO2).name("이산화질소 농도")
                .description("이산화질소 Nitrogen_dioxide")
                .definition("https://en.wikipedia.org/wiki/Nitrogen_dioxide")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.PPM).timeUnit(TimeUnit.HOUR).build();
        ItemDetail no2ValueDaily = no2Value.change(Item.NO2_DAILY, "이산화질소 농도 24시간", TimeUnit.DAILY);

        return new ItemDetails(List.of(pm10Value, pm10ValueDaily, pm25Value, pm25ValueDaily,
                so2Value, so2ValueDaily, coValue, coValueDaily,
                o3Value, o3ValueDaily, no2Value, no2ValueDaily));
    }

    public static Map<String, ObservedProperty> getObservedProperties(ItemDetails itemDetails) {
        return itemDetails.getItemDetails().stream()
                .map(detail -> ObservedPropertyBuilder.builder()
                        .name(detail.getItem().getName())
                        .description(detail.getDescription())
                        .definition(detail.getDefinition())
                        .build())
                .collect(Collectors.toMap(ObservedProperty::getName, Function.identity()));
    }

    public static Map<String, ObservedProperty> getObservedPropertiesByTimeUnit(ItemDetails itemDetails, TimeUnit timeUnit) {
        return itemDetails.getItemDetails().stream()
                .filter(detail -> detail.getTimeUnit() == timeUnit)
                .map(detail -> ObservedPropertyBuilder.builder()
                        .name(detail.getItem().getName())
                        .description(detail.getDescription())
                        .definition(detail.getDefinition())
                        .build())
                .collect(Collectors.toMap(ObservedProperty::getName, Function.identity()));
    }

}
