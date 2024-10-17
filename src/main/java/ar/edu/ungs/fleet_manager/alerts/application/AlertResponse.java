package ar.edu.ungs.fleet_manager.alerts.application;

import ar.edu.ungs.fleet_manager.alerts.domain.Alert;

import java.time.LocalDateTime;

public record AlertResponse(String id,
                            String priority,
                            String title,
                            String description,
                            Boolean acknowledge,
                            LocalDateTime dateCreated,
                            LocalDateTime dateUpdated) {
    public static AlertResponse map(Alert alert) {
        return new AlertResponse(alert.id().value(),
                                 alert.priority().name(),
                                 alert.title().value(),
                                 alert.description().value(),
                                 alert.acknowledge(),
                                 alert.dateCreated(),
                                 alert.dateUpdated());
    }
}
