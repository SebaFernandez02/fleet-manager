package ar.edu.ungs.fleet_manager.enterprises.domain;

import ar.edu.ungs.fleet_manager.shared.domain.Module;

public enum EnterpriseType {
    PERSONAL(new Module[]{Module.USERS, Module.VEHICLES}),
    INTERMEDIATE(new Module[]{Module.CONTROLS, Module.ORDERS, Module.PRODUCTS, Module.PROVIDERS, Module.USERS, Module.VEHICLES}),
    PROFESSIONAL(new Module[]{Module.CONTROLS, Module.ORDERS, Module.PRODUCTS, Module.PROVIDERS, Module.USERS, Module.VEHICLES, Module.ALERTS, Module.RESERVES, Module.ANALYTICS});

    private final Module[] modules;

    EnterpriseType(Module[] modules) {
        this.modules = modules;
    }

    public Module[] modules() {
        return modules;
    }
}
