package gaia3d.airquality.domain;

public enum TimeUnit {
    HOUR("HOUR"),
    DAILY("DAILY");

    private final String value;

    TimeUnit(String value) {
        this.value = value;
    }
}
