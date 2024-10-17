package ar.edu.ungs.fleet_manager.alerts.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public record AlertDescription(String value) {
    public AlertDescription {
        ensureValueValid(value);
    }

    private void ensureValueValid(String value) {
        if (value.isBlank()) {
            throw new InvalidParameterException("the description of alert is invalid");
        }
    }
}
