package ar.edu.ungs.fleet_manager.orders.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum OrderStatus {
    CREATED,
    REJECTED,
    APPROVED,
    COMPLETED,
    INACTIVE;

    public static OrderStatus parse(String status) {
        try {
            return valueOf(status.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid status: " + status);
        }
    }
}
