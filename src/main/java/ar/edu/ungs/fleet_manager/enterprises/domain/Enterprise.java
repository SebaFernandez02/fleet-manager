package ar.edu.ungs.fleet_manager.enterprises.domain;

import ar.edu.ungs.fleet_manager.shared.domain.Module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record Enterprise(EnterpriseId id, EnterpriseName name, List<Module> modules) {
    public static Enterprise create(String name) {
        EnterpriseId id = new EnterpriseId(UUID.randomUUID().toString());
        return new Enterprise(id, new EnterpriseName(name), new ArrayList<>());
    }

    public static Enterprise build(String id, String name, String modules) {
        return new Enterprise(new EnterpriseId(id),
                              new EnterpriseName(name),
                              !modules.isBlank() ? Arrays.stream(modules.split(",")).map(Module::parse).toList() : new ArrayList<>());
    }

    public void add(Module module) {
        this.modules.add(module);
    }

    public void remove(Module module) {
        this.modules.remove(module);
    }

    public void removeAll() {
        this.modules.clear();
    }
}
