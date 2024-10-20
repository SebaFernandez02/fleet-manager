package ar.edu.ungs.fleet_manager.orders.application.create;

import ar.edu.ungs.fleet_manager.orders.domain.Order;
import ar.edu.ungs.fleet_manager.orders.domain.OrderRepository;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplate;
import ar.edu.ungs.fleet_manager.orders.domain.OrderTemplateRepository;
import ar.edu.ungs.fleet_manager.products.domain.Product;
import ar.edu.ungs.fleet_manager.products.domain.ProductRepository;
import ar.edu.ungs.fleet_manager.providers.domain.ProviderId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrdersCreator {
    private final ProductRepository productRepository;
    private final OrderTemplateRepository orderTemplateRepository;
    private final OrderCreator orderCreator;

    public OrdersCreator(ProductRepository productRepository, OrderCreator orderCreator, OrderTemplateRepository orderTemplateRepository) {
        this.productRepository = productRepository;
        this.orderTemplateRepository =orderTemplateRepository;
        this.orderCreator = orderCreator;
    }


    public void execute() {
        List<Product> productsList =  this.productRepository.searchAllNoStockAutoPurchase(); // Trae todas orders que tienen autoPurchase, tienen Stock faltante, y que no tienen orden de compra activa;

        //Busca si el producto tiene una template para crear la orden, si no tiene la crea.
        for(Product product: productsList){
            this.orderTemplateRepository.findByProduct(product.id())
                    .ifPresentOrElse(orderCreator::execute,
                    ()-> {
                        OrderTemplate newOrderTemplate = OrderTemplateCreator.create(product);
                        this.orderTemplateRepository.save(newOrderTemplate);
                        orderCreator.execute(newOrderTemplate);
                    });


        }
    }

}

