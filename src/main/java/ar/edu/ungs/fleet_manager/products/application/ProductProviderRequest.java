package ar.edu.ungs.fleet_manager.products.application;

import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

import java.util.List;

public record ProductProviderRequest(String providerId) {
    public ProductProviderRequest {
        if (providerId == null) {
            throw new InvalidParameterException("the providerId to add cannot be null");
        }
    }

}
