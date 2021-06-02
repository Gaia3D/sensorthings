package gaia3d.airquality.domain;

import de.fraunhofer.iosb.ilt.sta.model.builder.ext.UnitOfMeasurementBuilder;
import de.fraunhofer.iosb.ilt.sta.model.ext.UnitOfMeasurement;
import lombok.ToString;

@ToString
public enum MeasureUnit {

    UGM3("microgram per cubic meter", "㎍/m³", "https://www.eea.europa.eu/themes/air/air-quality/resources/glossary/g-m3"),
    PPM("parts per million", "ppm", "https://en.wikipedia.org/wiki/Parts-per_notation");

    private final String name;
    private final String symbol;
    private final String definition;

    MeasureUnit(String name, String symbol, String definition) {
        this.name = name;
        this.symbol = symbol;
        this.definition = definition;
    }

    public UnitOfMeasurement toUnitOfMeasurement() {
        return UnitOfMeasurementBuilder.builder()
                .name(this.name)
                .symbol(this.symbol)
                .definition(this.definition)
                .build();
    }

}
