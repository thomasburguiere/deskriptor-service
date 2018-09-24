package ch.burg.deskriptor.service;

import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.io.DescriptorDeserializer;
import ch.burg.deskriptor.io.DescriptorKeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    @Primary
    public ObjectMapper jacksonBuilder() {
        final ObjectMapper objectMapper = new ObjectMapper();

        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addKeyDeserializer(Descriptor.class, new DescriptorKeyDeserializer());
        simpleModule.addDeserializer(Descriptor.class, new DescriptorDeserializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
//        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.deserializerByType(Descriptor.class, new DescriptorDeserializer());
//        builder.
//        return builder;
    }
}
