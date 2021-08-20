package gaia3d.frost.application;

import de.fraunhofer.iosb.ilt.sta.ServiceFailureException;
import de.fraunhofer.iosb.ilt.sta.model.Entity;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.ext.EntityList;
import de.fraunhofer.iosb.ilt.sta.query.Query;
import de.fraunhofer.iosb.ilt.sta.service.SensorThingsService;
import gaia3d.config.PropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Service
public class FrostServerService {

    private PropertiesConfig propertiesConfig;
    private SensorThingsService service;

    public FrostServerService(PropertiesConfig propertiesConfig) {
        try {
            this.propertiesConfig = propertiesConfig;
            URL serviceEndpoint = new URL(this.propertiesConfig.getFrostServiceEndpoint());
            this.service = new SensorThingsService(serviceEndpoint);
        } catch (MalformedURLException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    public <T extends Entity<T>> void create(T entity) {
        try {
            service.create(entity);
        } catch (ServiceFailureException e) {
            log.error("-------- create. message = {}", e.getMessage());
        }
        log.info("Dry Run: Not creating entity..." + entity.toString());
    }

    public <T extends Entity<T>> void update(T entity) {
        try {
            service.update(entity);
        } catch (ServiceFailureException e) {
            log.error("-------- update. message = {}", e.getMessage());
        }
        log.info("Dry Run: Not updating entity..." + entity);
    }

    public Location findLocationByName(String name) {
        Location location = null;
        Query<Location> query = service
                .locations()
                .query()
                .filter(String.format("name eq '%s'", name));
        try {
            EntityList<Location> list = query.list();
            if (list.size() == 0) log.debug("Not exist entity(Location) with name : {}", name);
            if (list.size() > 1) log.debug("More than one entity(Location) with name : {}", name);
            if (list.size() >= 1) location = list.iterator().next();
        } catch (ServiceFailureException e) {
            log.error("{}", e.getMessage(), e);
        }
        return location;
    }

}
