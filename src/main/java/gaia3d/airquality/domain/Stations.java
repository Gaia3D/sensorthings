package gaia3d.airquality.domain;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@RequiredArgsConstructor
@ToString
public class Stations {
    private final List<Station> stations;
}
