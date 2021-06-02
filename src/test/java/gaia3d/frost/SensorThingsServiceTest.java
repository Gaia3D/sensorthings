package gaia3d.frost;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Id;
import de.fraunhofer.iosb.ilt.sta.model.Thing;
import de.fraunhofer.iosb.ilt.sta.model.builder.ThingBuilder;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import gaia3d.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@SpringBootTest
public class SensorThingsServiceTest {

    @Autowired
    private PropertiesConfig propertiesConfig;
    private SensorThingsService service;

    @BeforeEach
    void setUp() {
        try {
            URL serviceEndpoint = new URL(propertiesConfig.getFrostServiceEndpoint());
            this.service = new SensorThingsService(serviceEndpoint);
        } catch (MalformedURLException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    @Test
    void crudTest() throws ServiceFailureException {

        // create
        Thing thing = ThingBuilder.builder()
                .name("Thingything")
                .description("I'm a thing!")
                .build();
        log.info("======= create thing : {}", thing);
        service.create(thing);
        Id id = thing.getId();

        // read
        thing = service.things().find(id);
        log.info("======= read thing : {}", thing);
        log.info("======= read description : {}", thing.getDescription());

        // update
        thing.setDescription("Things change...");
        service.update(thing);
        log.info("======= update thing : {}", thing);
        log.info("======= update description : {}", thing.getDescription());

        // delete
        service.delete(thing);
        log.info("======= delete thing : {}", thing);

    }

}
