package ar.edu.ungs.fleet_manager.users.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum Module {
    ALERTS,
    ANALYTICS,
    CONTROLS,
    ORDERS,
    PRODUCTS,
    PROVIDERS,
    RESERVES,
    USERS,
    VEHICLES;

    public static Module parse(String module) {
        try {
            return valueOf(module.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("module invalid");
        }
    }
}
