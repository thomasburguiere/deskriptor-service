package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.Item;
import ch.burg.deskriptor.engine.model.descriptor.Descriptor;
import ch.burg.deskriptor.engine.model.tree.DescriptorNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DatasetDocument {
    @Id
    private String id;


    private final Set<Descriptor> descriptors;
    private final Set<Item> items;

    private final List<DescriptorNode> descriptorDependencyNodes;

}
