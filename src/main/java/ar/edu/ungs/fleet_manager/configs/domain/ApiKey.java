package ar.edu.ungs.fleet_manager.configs.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

public class ApiKey {
    private final ApiKeyType type;
    private final String key;
    private final boolean secret;
    private final EnterpriseId enterpriseId;

    public ApiKey(ApiKeyType type, String key, boolean secret, EnterpriseId enterpriseId) {
        this.type = type;
        this.key = key;
        this.secret = secret;
        this.enterpriseId = enterpriseId;
    }

    public static ApiKey create(String type, String key, boolean secret, String enterpriseId){
        return new ApiKey(ApiKeyType.parse(type), key, secret, new EnterpriseId(enterpriseId));
    }

    public ApiKeyType type() {
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
