package ch.burg.deskriptor.service.item;

import ch.burg.deskriptor.engine.model.Item;
import ch.burg.deskriptor.service.dataset.DatasetDocument;
import ch.burg.deskriptor.service.dataset.DatasetDocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class ItemController {

    private final DatasetDocumentRepository datasetDocumentRepository;

    public ItemController(final DatasetDocumentRepository datasetDocumentRepository) {
        this.datasetDocumentRepository = datasetDocumentRepository;
    }

    @GetMapping(value = "/dataset/{datasetId}/items")
    public ResponseEntity<Collection<Item>> listDatasetItems(@PathVariable final String datasetId) {
        final Optional<DatasetDocument> datasetDocument = datasetDocumentRepository.findById(datasetId);

        if (datasetDocument.isPresent()) {
            return new ResponseEntity<>(datasetDocument.get().getDataset().getItems(), HttpStatus.OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping(value = "/dataset/{datasetId}/item/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable final String datasetId, @PathVariable final String itemId) {
        final Optional<DatasetDocument> datasetDocument = datasetDocumentRepository.findById(datasetId);

        if (!datasetDocument.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        final Optional<Item> itemOptional = datasetDocument.get()
                .getDataset().getItems()
                .stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();
        if (!itemOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(itemOptional.get(), OK);
    }
}
