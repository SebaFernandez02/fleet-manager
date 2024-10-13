package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;
import ar.edu.ungs.fleet_manager.controls.domain.Control;
import ar.edu.ungs.fleet_manager.controls.domain.ControlRepository;
import ar.edu.ungs.fleet_manager.users.application.UserResponse;
import ar.edu.ungs.fleet_manager.users.application.find.UserByIdFinder;
import ar.edu.ungs.fleet_manager.users.application.search.UserAllSearcher;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public final class ControlCreator {
    private final ControlRepository repository;
    private final UserAllSearcher userAllSearcher;

    public ControlCreator(ControlRepository repository, UserAllSearcher userAllSearcher) {
        this.repository = repository;
        this.userAllSearcher = userAllSearcher;
    }

    public void execute(ControlRequest request, String type){
        List<UserResponse> users = this.userAllSearcher.execute();

        for(UserResponse user : users){

            if(user.roles().contains("OPERATOR")){

                Control control = Control.create(type,
                        request.subject(),
                        request.description(),
                        request.vehicleId(),
                        user.id());

                this.repository.save(control);
                return;
            }
        }

    }
}
