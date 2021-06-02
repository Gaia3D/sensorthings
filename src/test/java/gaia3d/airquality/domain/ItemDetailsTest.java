package gaia3d.airquality.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemDetailsTest {

    @Test
    void getItemDetail() {
        // given
        ItemDetail expected = ItemDetail.builder().item(Item.PM10).name("미세먼지(PM10)")
                .description("미세먼지(PM10) Particulates")
                .definition("https://en.wikipedia.org/wiki/Particulates")
                .encodingType("http://www.opengis.net/doc/IS/SensorML/2.0")
                .measureUnit(MeasureUnit.UGM3).timeUnit(TimeUnit.HOUR).build();
        ItemDetails itemDetails = Item.generateItemDetails();
        // when
        ItemDetail actual = itemDetails.getItemDetail(Item.PM10);
        // then
        assertThat(actual).isEqualTo(expected);
    }

}