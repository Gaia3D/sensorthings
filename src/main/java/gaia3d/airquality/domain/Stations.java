package gaia3d.airquality.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@RequiredArgsConstructor
@Getter
@ToString
public class Stations {
    private final List<Station> stations;
}
