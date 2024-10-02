package ar.edu.ungs.fleet_manager.orders.infrastructure.jobs;

import ar.edu.ungs.fleet_manager.orders.application.create.OrdersCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CreateOrdersJob {
    private final OrdersCreator creator;
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrdersJob.class);

    public CreateOrdersJob(OrdersCreator creator) {
        this.creator = creator;
    }

    @Scheduled (fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void execute(){
        LOGGER.info("createOrderJob executing");
        this.creator.execute();
        LOGGER.info("createOrderJob executed");
    }
}
