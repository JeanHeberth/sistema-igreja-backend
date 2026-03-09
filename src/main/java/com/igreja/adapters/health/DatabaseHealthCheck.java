package com.igreja.adapters.health;

import org.eclipse.microprofile.health.*;
import jakarta.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class DatabaseHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {

        boolean bancoOk = true;

        if (bancoOk) {
            return HealthCheckResponse.up("Banco de dados");
        }

        return HealthCheckResponse.down("Banco de dados");
    }
}
