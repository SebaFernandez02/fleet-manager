package ar.edu.ungs.fleet_manager.controls.domain;

import java.util.List;
import java.util.Optional;

public interface ControlTemplateRepository {

    void save(ControlTemplate controlTemplate);

    Optional<ControlTemplate> findById(ControlId id);

    List<ControlTemplate> searchAll();
}
