package gl.passhelper.controller.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component("myhealth")
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = 100;
        if (errorCode != 0) {
            return Health.status(new Status("FATAL","something wrong")).build(); //FATAL会映射为503
        }
        return Health.up().build();
    }
}
