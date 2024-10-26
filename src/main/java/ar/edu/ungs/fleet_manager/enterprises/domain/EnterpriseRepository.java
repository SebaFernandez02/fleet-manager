package ar.edu.ungs.fleet_manager.enterprises.domain;

import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository {
    void save(Enterprise enterprise);

    Optional<Enterprise> findById(EnterpriseId id);

    List<Enterprise> searchAll();
}
