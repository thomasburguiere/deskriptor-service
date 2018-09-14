package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.Dataset;
import ch.burg.deskriptor.engine.model.Item;
import ch.burg.deskriptor.engine.model.State;
import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.descriptor.DiscreteDescriptor;
import ch.burg.deskriptor.engine.model.tree.DescriptorNode;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatasetDeserializer extends JsonDeserializer<Dataset> {
    @Override
    public Dataset deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final TreeNode datasetNode = p.getCodec().readTree(p);

        final List<DescriptorNode> descriptorDependencyNodes = deserializeDescriptorDependencyNodes(datasetNode);
        final Set<Item> items = deserializeItems(datasetNode);
        final Set<Descriptor> descriptors = deserializeDescriptors(datasetNode);

        return Dataset.builder()
                .descriptorDependencyNodes(descriptorDependencyNodes)
                .descriptors(descriptors)
                .items(items)
                .build();
    }

    private Set<Descriptor> deserializeDescriptors(final TreeNode datasetNode) {
        final ArrayNode descriptorsArrayNode = (ArrayNode) datasetNode.get("descriptors");

        if (descriptorsArrayNode == null) {
            return Set.of();
        }

        final Set<Descriptor> desc = new HashSet<>();

        for (int i = 0; i < descriptorsArrayNode.size(); i++) {
            final JsonNode descriptorJsonNode = descriptorsArrayNode.get(i);
            if (isDiscreteDescriptor(descriptorJsonNode)) {
                desc.add(DiscreteDescriptor.builder()
                        .withId(descriptorJsonNode.get("id").asText())
                        .withName(descriptorJsonNode.get("name").asText())
                        .withPossibleStates(deserializeStates(descriptorJsonNode))
                        .build()
                );
            }
        }

        return desc;
    }

    private static boolean isDiscreteDescriptor(final JsonNode descriptorJsonNode) {
        return descriptorJsonNode.get("possibleStates") != null;
    }

    private static State[] deserializeStates(final JsonNode descriptorNode) {
        final ArrayNode statesArrayNode = (ArrayNode) descriptorNode.get("possibleStates");
        if (statesArrayNode == null) {
            return new State[0];
        }

        final State[] states = new State[statesArrayNode.size()];


        for (int i = 0; i < statesArrayNode.size(); i++) {
            states[i] = State.fromName(statesArrayNode.get(i).get("name").asText());
        }
        return states;
    }

    private static Set<Item> deserializeItems(final TreeNode datasetNode) {
        final ArrayNode items = (ArrayNode) datasetNode.get("items");
        if (items == null) {
            return Set.of();
        }

        final Set<Item> itemsCast = new HashSet<>();
        for (int i = 0; i < items.size(); i++) {
            final JsonNode itemJsonNode = items.get(i);
            itemsCast.add(
                    Item.builder()
                            .withName(itemJsonNode.get("name").asText())
                            .build()
            );
        }
        return itemsCast;
    }

    private static List<DescriptorNode> deserializeDescriptorDependencyNodes(final TreeNode datasetNode) {
        final TreeNode descriptorDependencyNodes = datasetNode.get("descriptorDependencyNodes");

        List<DescriptorNode> descriptorDependencyNodesCast;
        try {
            descriptorDependencyNodesCast = (List<DescriptorNode>) descriptorDependencyNodes;
        } catch (final ClassCastException e) {
            descriptorDependencyNodesCast = List.of();
        }
        return descriptorDependencyNodesCast;
    }
}
