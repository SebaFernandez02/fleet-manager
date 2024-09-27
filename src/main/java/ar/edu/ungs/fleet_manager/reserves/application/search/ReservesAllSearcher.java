package ar.edu.ungs.fleet_manager.reserves.application.search;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReservesAllSearcher {
    private final ReserveRepository repository;

    public ReservesAllSearcher(ReserveRepository repository) {
        this.repository = repository;
    }

    public List<ReserveResponse> execute() {
        return this.repository.searchAll()
                .stream()
                .map(ReserveResponse::map)
                .toList();
    }
}
