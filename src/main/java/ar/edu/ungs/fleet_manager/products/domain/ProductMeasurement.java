package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;
import ar.edu.ungs.fleet_manager.vehicles.domain.VehicleFuel;

import java.util.Locale;

public enum ProductMeasurement {
    KILOGRAM,
    LITER,
    UNIT;

    public static ProductMeasurement parse(String status) {
        try {
            return valueOf(status.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("product measurement invalid");
        }
    }
}
