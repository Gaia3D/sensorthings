package gaia3d.airquality.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ItemDetails {
    private final List<ItemDetail> itemDetails;

    public ItemDetail getItemDetail(Item item) {
        return itemDetails.stream()
                .filter(detail -> detail.getItem() == item)
                .findFirst()
                .orElse(null);
    }

}
