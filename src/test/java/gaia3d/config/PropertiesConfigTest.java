package gaia3d.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PropertiesConfigTest {

    @Autowired
    private PropertiesConfig propertiesConfig;

    @Test
    void 프로파일_프로퍼티_테스트() {
        Profile profile = propertiesConfig.getProfile();
        log.info("========== profile = {}", profile);
        assertThat(profile).isEqualTo(Profile.LOCAL);
    }

    @Test
    void 지오서버_프로퍼티_테스트() {
        String geoserverUrl = propertiesConfig.getGeoserverUrl();
        log.info("========== geoserverUrl = {}", geoserverUrl);
    }

}