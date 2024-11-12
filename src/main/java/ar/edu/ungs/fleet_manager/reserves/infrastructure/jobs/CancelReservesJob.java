package ar.edu.ungs.fleet_manager.reserves.infrastructure.jobs;

import ar.edu.ungs.fleet_manager.enterprises.application.EnterpriseResponse;
import ar.edu.ungs.fleet_manager.enterprises.application.search.EnterpriseSearcher;
import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;
import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.application.search.ReservesAllSearcher;
import ar.edu.ungs.fleet_manager.reserves.application.update.ReserveStatusUpdater;
import ar.edu.ungs.fleet_manager.reserves.domain.ReserveStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CancelReservesJob {
    private final ReservesAllSearcher searcher;
    private final ReserveStatusUpdater updater;
    private final EnterpriseSearcher enterpriseSearcher;

    public CancelReservesJob(ReservesAllSearcher searcher, ReserveStatusUpdater updater, EnterpriseSearcher enterpriseSearcher) {
        this.searcher = searcher;
        this.updater = updater;
        this.enterpriseSearcher = enterpriseSearcher;
    }

    @Scheduled (cron = "0 1,16,31,46 * * * *")
    public void execute(){
        List<EnterpriseResponse> enterprises = this.enterpriseSearcher.execute();

        for(EnterpriseResponse enterprise : enterprises){

            List<ReserveResponse> reserves = this.searcher.execute(new EnterpriseId(enterprise.id())).stream().filter(x -> x.status().equals(ReserveStatus.CREATED.toString())).toList();

            for(ReserveResponse reserve : reserves){

                if(reserve.dateFinishReserve().isBefore(LocalDateTime.now())){

                    this.updater.execute(reserve.id(), ReserveStatus.CANCELLED.toString());
                }
            }
        }

    }
}
