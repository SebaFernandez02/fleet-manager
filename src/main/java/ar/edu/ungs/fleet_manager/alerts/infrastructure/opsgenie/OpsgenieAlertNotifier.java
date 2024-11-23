package ar.edu.ungs.fleet_manager.alerts.infrastructure.opsgenie;

import ar.edu.ungs.fleet_manager.alerts.domain.Alert;
import ar.edu.ungs.fleet_manager.alerts.domain.AlertNotifier;
import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.configs.domain.services.ApiKeyFinder;
import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public final class OpsgenieAlertNotifier implements AlertNotifier {
    //private static final String API_KEY = "9c0bbd88-b847-4f46-a703-c3efa254eb63";
    private static final String BASE_URL = "https://api.opsgenie.com/v2/alerts";
    private final ApiKeyFinder keyFinder;
    private final ObjectMapper objectMapper;

    public OpsgenieAlertNotifier(ApiKeyFinder keyFinder, ObjectMapper objectMapper) {
        this.keyFinder = keyFinder;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute(Alert alert) {
        try {
            var httpClient = HttpClient.newHttpClient();

            String API_KEY = keyFinder.execute(ApiKeyType.OPSGENIE_KEY,alert.enterpriseId()).key();

            Map<String, Object> requestBody = Map.of(
                    "message", alert.title().value(),
                    "description", alert.description().value(),
                    "priority", calculatePriority(alert),
                    "teams", List.of(Map.of("name", "FleetFly"))
            );

            String jsonRequestBody = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "GenieKey " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (!(response.statusCode() >= 200 || response.statusCode() <= 299)) {
                throw new RuntimeException("Error en la creación de la alerta: " + response.body());
            }
        } catch (Exception e) {
            throw new InfrastructureException(e.getMessage());
        }

    }

    private String calculatePriority(Alert alert) {
        return switch (alert.priority()) {
            case LOW -> "P3";
            case MEDIUM -> "P2";
            case HIGH -> "P1";
        };
    }
}
