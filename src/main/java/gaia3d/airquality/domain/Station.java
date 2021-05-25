package gaia3d.airquality.domain;

import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
public class Station {
    // 측정소 명
    private String name;
    // 위치
    private Location location;
    // 측정 항목
    private List<ObservedProperty> observedProperties;
    // 측정망 명
    private String networkName;
    // 설치년도
    private Integer year;
    // 주소
    private String addr;
}
