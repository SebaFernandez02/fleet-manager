package ar.edu.ungs.fleet_manager.analytics.domain;

import java.util.List;

public interface AnalyticRepository {
    List<Analytic> search();
}
