package ch.burg.deskriptor.service.item;

import ch.burg.deskriptor.engine.model.Item;
import ch.burg.deskriptor.service.dataset.DatasetDocument;
import ch.burg.deskriptor.service.dataset.DatasetDocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final DatasetDocumentRepository datasetDocumentRepository;

    public ItemController(final DatasetDocumentRepository datasetDocumentRepository) {
        this.datasetDocumentRepository = datasetDocumentRepository;
    }

    @GetMapping(value = "/dataset/{datasetId}/list")
    public ResponseEntity<Collection<Item>> listDatasetItems(@PathVariable final String datasetId) {
        final Optional<DatasetDocument> datasetDocument = datasetDocumentRepository.findById(datasetId);

        if (datasetDocument.isPresent()) {
            return new ResponseEntity<>(datasetDocument.get().getDataset().getItems(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
