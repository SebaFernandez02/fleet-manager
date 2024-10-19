package ar.edu.ungs.fleet_manager.analytics.infrastructure.bean;

import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticOrigin;
import ar.edu.ungs.fleet_manager.analytics.domain.AnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence.PostgresAlertAnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence.PostgresControlAnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence.PostgresOrderAnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence.PostgresReserveAnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence.PostgresProductAnalyticRepository;
import ar.edu.ungs.fleet_manager.analytics.infrastructure.persistence.PostgresVehicleAnalyticRepository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AnalyticsBeans {
    private final ApplicationContext context;

    public AnalyticsBeans(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public Map<AnalyticOrigin, AnalyticRepository> getRepositories() {
        return Map.of(AnalyticOrigin.CONTROLS, context.getBean(PostgresControlAnalyticRepository.class),
                AnalyticOrigin.ALERTS, context.getBean(PostgresAlertAnalyticRepository.class),
                AnalyticOrigin.ORDERS, context.getBean(PostgresOrderAnalyticRepository.class),
                AnalyticOrigin.RESERVES, context.getBean(PostgresReserveAnalyticRepository.class),
                AnalyticOrigin.VEHICLES, context.getBean(PostgresVehicleAnalyticRepository.class),
                AnalyticOrigin.PRODUCTS, context.getBean(PostgresProductAnalyticRepository.class));
    }
}
