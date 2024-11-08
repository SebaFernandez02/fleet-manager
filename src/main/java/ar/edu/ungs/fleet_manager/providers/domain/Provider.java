package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.Objects;
import java.util.UUID;

public record Provider(ProviderId id,
                       ProviderName name,
                       ProviderEmail email,
                       ProviderCuit cuit,
                       ProviderPhoneNumber phoneNumber,
                       ProviderAddress address,
                       EnterpriseId enterpriseId) {

    public static Provider create(String name,
                                  String email,
                                  String cuit,
                                  String phoneNumber,
                                  String address,
                                  String enterpriseId) {
        return build(UUID.randomUUID().toString(),
                name,
                email,
                cuit,
                phoneNumber,
                address,
                enterpriseId);
    }

    public static Provider build(String id,
                                 String name,
                                 String email,
                                 String cuit,
                                 String phoneNumber,
                                 String address,
                                 String enterpriseId) {
        return new Provider(new ProviderId(id),
                new ProviderName(name),
                new ProviderEmail(email),
                new ProviderCuit(cuit),
                new ProviderPhoneNumber(phoneNumber),
                new ProviderAddress(address),
                new EnterpriseId(enterpriseId));
    }
}
