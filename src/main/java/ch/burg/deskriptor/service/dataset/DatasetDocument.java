package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.Dataset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class DatasetDocument {

    private final Dataset dataset;
    @Id
    private String id;

}
