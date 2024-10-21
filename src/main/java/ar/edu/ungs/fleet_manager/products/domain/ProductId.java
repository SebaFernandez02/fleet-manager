package ar.edu.ungs.fleet_manager.products.domain;

import ar.edu.ungs.fleet_manager.shared.domain.Identifier;
import ar.edu.ungs.fleet_manager.shared.domain.exceptions.InvalidParameterException;

public final class ProductId extends Identifier {
    public ProductId (String value){
        super(value);
    }
}
