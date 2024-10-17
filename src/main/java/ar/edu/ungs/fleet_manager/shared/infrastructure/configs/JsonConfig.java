package ar.edu.ungs.fleet_manager.shared.infrastructure.configs;

import ar.edu.ungs.fleet_manager.orders.domain.OrderProduct;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class JsonConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.registerModule(new JavaTimeModule());
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        mapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        return mapper;
    }

//    public static ObjectMapper createJsonMapper(){
//        ObjectMapper mapper = new ObjectMapper();
//
//        SimpleModule module = new SimpleModule();
//        module.addSerializer((Class<List<OrderProduct>>) (Class<?>) List.class, new ProductListSerializer());
//        mapper.registerModule(module);
//        return mapper;
//    }

}
