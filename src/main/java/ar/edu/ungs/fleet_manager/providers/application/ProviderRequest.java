package ar.edu.ungs.fleet_manager.providers.application;

public record ProviderRequest(String id,
                             String name,
                             String email,
                             String cuit,
                             String phoneNumber,
                             String address) {
}