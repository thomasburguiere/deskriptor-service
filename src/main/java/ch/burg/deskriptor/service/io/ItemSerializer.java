package ch.burg.deskriptor.service.io;

import ch.burg.deskriptor.engine.model.Item;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ItemSerializer extends JsonSerializer<Item> {
    @Override
    public void serialize(
            final Item value,
            final JsonGenerator gen,
            final SerializerProvider serializers
    ) throws IOException {

    }


    @Override
    public Class<Item> handledType() {
        return Item.class;
    }
}
