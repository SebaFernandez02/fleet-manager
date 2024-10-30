package ar.edu.ungs.fleet_manager.alerts.domain;

public interface AlertNotifier {
    void execute(Alert alert);
}
