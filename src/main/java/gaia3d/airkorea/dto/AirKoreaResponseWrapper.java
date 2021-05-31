package gaia3d.airkorea.dto;

import lombok.Data;

@Data
public class AirKoreaResponseWrapper<T> {
    private AirKoreaResponse<T> response;
}
