package gaia3d.airquality.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class Metadata {
    private final String name;
    private final String description;
    private final String definition;
    private final TimeUnit type;
    private final String koreanName;
    private final String unitOfMeasurement;
}
