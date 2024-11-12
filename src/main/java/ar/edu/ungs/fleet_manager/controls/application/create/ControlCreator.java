package ar.edu.ungs.fleet_manager.controls.application.create;

import ar.edu.ungs.fleet_manager.controls.application.ControlRequest;

public interface ControlCreator {
    void execute(ControlRequest request);
}