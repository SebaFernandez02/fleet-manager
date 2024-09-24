package ar.edu.ungs.fleet_manager.providers.application;
import ar.edu.ungs.fleet_manager.providers.domain.Provider;


public record ProviderResponse(String id,
                               String name,
                               String email,
                               String cuit,
                               String phoneNumber,
                               String address) {
    public static ProviderResponse map(Provider provider) {
        return new ProviderResponse(
                provider.id().value(),
                provider.name().value(),
                provider.email().value(),
                provider.cuit().value(),
                provider.phoneNumber().value(),
                provider.address().value());
    }
}