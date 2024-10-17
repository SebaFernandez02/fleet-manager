package ar.edu.ungs.fleet_manager.orders.application.create;

import org.springframework.stereotype.Component;

@Component
public class OrdersCreator {
    public void execute() {
        // 1. Buscar todos los productos que no tengan stock
        // 2. Por cada producto buscar si tiene una orden de compra activa
        // 3. Si no tiene una order activa, crear order
        // 4. Añadir producto a la order
    }
}

