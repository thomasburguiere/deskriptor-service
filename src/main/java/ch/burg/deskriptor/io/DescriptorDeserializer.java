package ch.burg.deskriptor.io;

import ch.burg.deskriptor.engine.model.State;
import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.descriptor.DiscreteDescriptor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DescriptorDeserializer extends JsonDeserializer<Descriptor> {

    private static final String POSSIBLE_STATES = "possibleStates";

    @Override
    public Descriptor deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

        final JsonNode descriptorNode = p.getCodec().readTree(p);
        if (isDiscreteDescriptor(descriptorNode)) {
            return DiscreteDescriptor.builder()
                    .withId(descriptorNode.get("id").asText())
                    .withName(descriptorNode.get("name").asText())
                    .withPossibleStates(deserializeStates(descriptorNode))
                    .build();

        }
        throw new IllegalArgumentException("unable to infer Descriptor concrete type from json content");
    }

    private static boolean isDiscreteDescriptor(final JsonNode descriptorJsonNode) {
        return descriptorJsonNode.get(POSSIBLE_STATES) != null;
    }

    private static Set<State> deserializeStates(final JsonNode descriptorNode) {
        if (descriptorNode.get(POSSIBLE_STATES) instanceof NullNode) {
            return Set.of();
        }

        final ArrayNode statesArrayNode = (ArrayNode) descriptorNode.get(POSSIBLE_STATES);
        if (statesArrayNode == null) {
            return Set.of();
        }

        final Set<State> states = new HashSet<>();


        for (int i = 0; i < statesArrayNode.size(); i++) {
            states.add(State.fromName(statesArrayNode.get(i).get("name").asText()));
        }
        return states;
    }
}
