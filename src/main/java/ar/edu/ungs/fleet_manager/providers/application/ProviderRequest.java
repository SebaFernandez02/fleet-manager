package ar.edu.ungs.fleet_manager.providers.application;

public record ProviderRequest(String name,
                              String email,
                              String cuit,
                              String phoneNumber,
                              String address,
                              String enterpriseId) {
}