package ar.edu.ungs.fleet_manager.enterprises.domain;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

public final class Enterprise {
    private final EnterpriseId id;
    private EnterpriseName name;
    private Boolean isActive;
    private EnterpriseType type;
    private final LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public Enterprise(EnterpriseId id,
                      EnterpriseName name,
                      Boolean isActive,
                      EnterpriseType type,
                      LocalDateTime dateCreated,
                      LocalDateTime dateUpdated) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.type = type;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public static Enterprise create(String name, String type, Boolean isActive) {
        EnterpriseId id = new EnterpriseId(UUID.randomUUID().toString());
        return new Enterprise(id, new EnterpriseName(name), isActive, EnterpriseType.valueOf(type.toUpperCase(Locale.ROOT)), LocalDateTime.now(), LocalDateTime.now());
    }

    public static Enterprise build(String id,
                                   String name,
                                   Boolean isActive,
                                   String type,
                                   LocalDateTime dateCreated,
                                   LocalDateTime dateUpdated) {
        return new Enterprise(new EnterpriseId(id), new EnterpriseName(name), isActive, EnterpriseType.valueOf(type), dateCreated, dateUpdated);
    }

    public EnterpriseId id() {
        return id;
    }

    public EnterpriseName name() {
        return name;
    }

    public EnterpriseType type() {
        return type;
    }

    public void update(String name, String type, Boolean isActive) {
        if (Optional.ofNullable(name).isPresent()) {
            this.name = new EnterpriseName(name);
        }

        if (Optional.ofNullable(type).isPresent()) {
            this.type = EnterpriseType.valueOf(type.toUpperCase(Locale.ROOT));
        }

        if (Optional.ofNullable(isActive).isPresent()) {
            this.isActive = isActive;
        }

        this.dateUpdated = LocalDateTime.now();
    }

    public Boolean isActive() {
        return isActive;
    }

    public LocalDateTime dateCreated() {
        return dateCreated;
    }

    public LocalDateTime dateUpdated() {
        return dateUpdated;
    }
}
