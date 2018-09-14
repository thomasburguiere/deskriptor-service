package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.Dataset;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    @Id
    private String id;

    @JsonDeserialize(using = DatasetDeserializer.class)
    private final Dataset dataset;

}
