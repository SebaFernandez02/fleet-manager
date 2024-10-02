package ar.edu.ungs.fleet_manager.orders.infrastructure.jobs;

import ar.edu.ungs.fleet_manager.orders.application.create.OrdersCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CreateOrdersJob {
    private final OrdersCreator creator;

    public CreateOrdersJob(OrdersCreator creator) {
        this.creator = creator;
    }

    @Scheduled (fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void execute(){
        this.creator.execute();
    }
}
