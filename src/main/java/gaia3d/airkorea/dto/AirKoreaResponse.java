package gaia3d.airkorea.dto;

import lombok.Data;

@Data
public class AirKoreaResponse<T> {
    private T body;
    private AirKoreaHeader header;
}
