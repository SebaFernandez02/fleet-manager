package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.Locale;

public enum ProductAutomaticPurchase {
    ENABLED,
    DISABLED;


    public static ProductAutomaticPurchase parse(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.ROOT));
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid state for automatic purchase");
        }
    }




}
