package ar.edu.ungs.fleet_manager.controls.domain;



import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.List;
import java.util.Optional;

public interface ControlRepository {
    void save(Control control);

    Optional<Control> findById(ControlId id);

    List<Control> searchAll(EnterpriseId enterpriseId);
}
