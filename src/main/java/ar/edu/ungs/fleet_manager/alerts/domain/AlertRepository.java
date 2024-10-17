package ar.edu.ungs.fleet_manager.alerts.domain;

import java.util.List;
import java.util.Optional;

public interface AlertRepository {
    void save(Alert alert);

    Optional<Alert> findById(AlertId id);

    List<Alert> searchAll();
}
