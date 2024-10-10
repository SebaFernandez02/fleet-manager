package ar.edu.ungs.fleet_manager.alerts.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record AlertTitle(String value) {
    public AlertTitle {
        ensureValueValid(value);
    }

    private void ensureValueValid(String value) {
        if (value.isBlank()) {
            throw new InvalidParameterException("the title of alert is invalid");
        }
    }
}
