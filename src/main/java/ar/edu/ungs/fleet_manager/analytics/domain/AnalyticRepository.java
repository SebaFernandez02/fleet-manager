package ar.edu.ungs.fleet_manager.analytics.domain;

import ar.edu.ungs.fleet_manager.enterprises.domain.EnterpriseId;

import java.util.List;

public interface AnalyticRepository {
    List<Analytic> search(EnterpriseId enterpriseId);
}
