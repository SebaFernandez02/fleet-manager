package ar.edu.ungs.fleet_manager.reserves.application.search;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReservesAllSearcher {
    private final ReserveRepository repository;

    public ReservesAllSearcher(ReserveRepository repository) {
        this.repository = repository;
    }

    public List<ReserveResponse> execute(EnterpriseId enterpriseId) {
        return this.repository.searchAll(enterpriseId)
                .stream()
                .map(ReserveResponse::map)
                .toList();
    }
}
