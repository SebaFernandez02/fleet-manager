package ar.edu.ungs.fleet_manager.reserves.application.search;

import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveRepository;
import ar.edu.ungs.fleet_manager.users.domain.UserId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ReservesByUserSearcher {
    private final ReserveRepository repository;

    public ReservesByUserSearcher(ReserveRepository repository) {
        this.repository = repository;
    }

    public List<ReserveResponse> execute(String id) {
        var userId = new UserId(id);

        return this.repository.findByUserId(userId)
                .stream()
                .map(ReserveResponse::map)
                .toList();
    }
}
