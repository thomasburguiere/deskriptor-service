package ch.burg.deskriptor.service.config;

import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.service.io.DescriptorDeserializer;
import ch.burg.deskriptor.service.io.DescriptorKeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config {

    @Bean
    @Primary
    public ObjectMapper jacksonBuilder() {
        final ObjectMapper objectMapper = new ObjectMapper();

        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(Descriptor.class, new DescriptorKeyDeserializer());
        simpleModule.addDeserializer(Descriptor.class, new DescriptorDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
