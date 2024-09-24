package ar.edu.ungs.fleet_manager.providers.domain;

import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;

import java.util.Objects;
import java.util.UUID;

public final class Provider {
    private ProviderId id;
    private ProviderName name;
    private ProviderEmail email;
    private ProviderCuit cuit;
    private ProviderPhoneNumber phoneNumber;
    private ProviderAddress address;

    public Provider(ProviderId id,
                    ProviderName name,
                    ProviderEmail email,
                    ProviderCuit cuit,
                    ProviderPhoneNumber phoneNumber,
                    ProviderAddress address
                    ) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cuit = cuit;
        this.email = email;
        this.name = name;
    }

    public static Provider create(String name,
                                  String email,
                                  String cuit,
                                  String phoneNumber,
                                  String address){
        return build(UUID.randomUUID().toString(),
                     name,
                     email,
                     cuit,
                     phoneNumber,
                     address);

    }

    public static Provider build(String id,
                                String name,
                                String email,
                                String cuit,
                                String phoneNumber,
                                String address){

        return new Provider(new ProviderId(id),
                            new ProviderName(name),
                            new ProviderEmail(email),
                            new ProviderCuit(cuit),
                            new ProviderPhoneNumber(phoneNumber),
                            new ProviderAddress(address));
    }


    public ProviderId id() {
        return id;
    }

    public ProviderName name() {
        return name;
    }

    public ProviderEmail email() {
        return email;
    }

    public ProviderCuit cuit() {
        return cuit;
    }

    public ProviderPhoneNumber phoneNumber() {
        return phoneNumber;
    }

    public ProviderAddress address() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provider provider = (Provider) o;
        return Objects.equals(email, provider.email) && Objects.equals(cuit, provider.cuit) && Objects.equals(phoneNumber, provider.phoneNumber) && Objects.equals(address, provider.address) && Objects.equals(name, provider.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, cuit, phoneNumber, address, name);
    }

    @Override
    public String toString() {
        return "Provider{" +
                "email=" + email +
                ", cuit=" + cuit +
                ", phoneNumber=" + phoneNumber +
                ", address=" + address +
                ", name=" + name +
                '}';
    }
}
