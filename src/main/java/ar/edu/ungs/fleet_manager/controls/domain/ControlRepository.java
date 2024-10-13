package ar.edu.ungs.fleet_manager.controls.domain;



import java.util.List;
import java.util.Optional;

public interface ControlRepository {
    void save(Control control);

    Optional<Control> findById(ControlId id);

    List<Control> searchAll();
}
