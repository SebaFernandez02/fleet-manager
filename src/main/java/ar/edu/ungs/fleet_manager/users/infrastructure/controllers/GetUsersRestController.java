package ar.edu.ungs.fleet_manager.users.infrastructure.controllers;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.application.search.UserAllSearcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GetUsersRestController {
    private final UserAllSearcher searcher;

    public GetUsersRestController(UserAllSearcher searcher) {
        this.searcher = searcher;
    }

    @GetMapping("/api/users")
    public ResponseEntity<?> handle(@RequestParam(value = "enterprise_id", required = true) String enterpriseId) {
        List<UserResponse> values = this.searcher.execute(new EnterpriseId(enterpriseId));

        return ResponseEntity.ok(values);
    }
}
