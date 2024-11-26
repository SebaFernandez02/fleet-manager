package ar.edu.ungs.fleet_manager.controls.infrastructure.services;

import ar.edu.ungs.fleet_manager.configs.domain.ApiKeyType;
import ar.edu.ungs.fleet_manager.configs.domain.services.ApiKeyFinder;
import ar.edu.ungs.fleet_manager.controls.application.ControlResponse;
import ar.edu.ungs.fleet_manager.controls.application.find.ControlByIdFinder;
import ar.edu.ungs.fleet_manager.controls.domain.*;
import ar.edu.ungs.fleet_manager.controls.domain.services.ControlPredictor;
import ar.edu.ungs.fleet_manager.enterprises.domain.Enterprise;
import ar.edu.ungs.fleet_manager.reserves.application.ReserveResponse;
import ar.edu.ungs.fleet_manager.reserves.domain.Reserve;
import ar.edu.ungs.fleet_manager.shared.infrastructure.exceptions.InfrastructureException;
import ar.edu.ungs.fleet_manager.vehicles.application.VehicleResponse;
import ar.edu.ungs.fleet_manager.vehicles.domain.Vehicle;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import io.github.flashvayne.chatgpt.property.ChatgptProperties;
import io.github.flashvayne.chatgpt.service.impl.DefaultChatgptService;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiControlPredictor implements ControlPredictor {
    private final ControlByIdFinder controlByIdFinder;
    private final ObjectMapper mapper;
    private final ApiKeyFinder keyFinder;

    public OpenAiControlPredictor(ControlByIdFinder controlByIdFinder, ObjectMapper mapper, ApiKeyFinder keyFinder) {
        this.controlByIdFinder = controlByIdFinder;
        this.mapper = mapper;
        this.keyFinder = keyFinder;
    }

    @Override
    public ControlPrediction execute(Vehicle vehicle, List<Control> controls, List<Reserve> reserves, Enterprise enterprise) {
        try {
            var prompt = """
                Eres un mecánico de vehículos. A partir de las especificaciones del vehículo, los viajes y controles mecánicos, necesito un diagnóstico del mismo para generar un control mecánico predictivo.
                Es importe aclarar que preciso que la generación de la respuesta sea en un formato json como:
                {
                  "type": "string",
                  "subject": "string",
                  "description": "string",
                  "priority": "string"
                }
                
                En 'type' se indicaría si es un control PREVENTIVE (prevención de posibles errores) y CORRECTIVE (corregir error en el vehiculo).  En 'subject' iría un título representativo al control mecánico. En 'description' es un descripción detallada de lo que se precisa controlar y/o arreglar en el vehículo. Por último, en 'priority' aplicaría el nivel de criticidad HIGH, MEDIUM or LOW.
                Finalizando con el contexto, adjunto las especificaciones, controles y viajes del vehículo:  
                - vehicle: %s
                - controls: %s
                - trips: %s
                """.formatted(mapper.writeValueAsString(VehicleResponse.map(vehicle)),
                    mapper.writeValueAsString(controls.stream().map(x -> this.controlByIdFinder.execute(x.id().value())).sorted(Comparator.comparing(ControlResponse::dateCreated)).limit(3).toList()),
                    mapper.writeValueAsString(reserves.stream().map(ReserveResponse::map).sorted(Comparator.comparing(ReserveResponse::dateCreated)).limit(2).toList()));

            String key = keyFinder.execute(ApiKeyType.CHATGPT_KEY, enterprise.id()).key();

            ChatgptProperties properties = new ChatgptProperties();

            properties.setApiKey(key);

            var message = new DefaultChatgptService(properties).sendMessage(prompt);

            var map = mapper.readValue(message, Map.class);

            var type = ControlType.parse((String) map.get("type"));
            var subject = new ControlSubject((String) map.get("subject"));
            var description = new ControlDescription((String) map.get("description"));
            var priority = ControlPriority.parse((String) map.get("priority"));

            return new ControlPrediction(type, subject, description, priority);
        } catch (Exception error) {
            throw new InfrastructureException(error.getMessage());
        }
    }
}
