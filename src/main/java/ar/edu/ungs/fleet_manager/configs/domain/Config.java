package ar.edu.ungs.fleet_manager.configs.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

public class Config {
    private final ConfigType type;
    private final String key;
    private final boolean secret;
    private final EnterpriseId enterpriseId;

    public Config(ConfigType type, String key, boolean secret, EnterpriseId enterpriseId) {
        this.type = type;
        this.key = key;
        this.secret = secret;
        this.enterpriseId = enterpriseId;
    }

    public static Config create(String type, String key, boolean secret, String enterpriseId){
        return new Config(ConfigType.parse(type), key, secret, new EnterpriseId(enterpriseId));
    }

    public ConfigType type() {
        return type;
    }

    public String key() {
        return key;
    }

    public boolean secret() {
        return secret;
    }

    public EnterpriseId enterpriseId() {
        return enterpriseId;
    }
}
