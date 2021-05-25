package gaia3d.airkorea.dto;

import lombok.Data;

@Data
public class AirKoreaHeader {
    private String resultMsg;
    private String resultCode;

    public boolean ok() {
        return resultCode.equals("00");
    }

}
