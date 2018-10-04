package ch.burg.deskriptor.service.item;

import ch.burg.deskriptor.engine.model.Item;
import ch.burg.deskriptor.service.config.GlobalExceptionHandler.EntityNotFoundException;
import ch.burg.deskriptor.service.dataset.DatasetDocument;
import ch.burg.deskriptor.service.dataset.DatasetDocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/dataset/{datasetId}/item")
public class ItemController {

    private final DatasetDocumentRepository datasetDocumentRepository;

    public ItemController(final DatasetDocumentRepository datasetDocumentRepository) {
        this.datasetDocumentRepository = datasetDocumentRepository;
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Collection<Item>> listDatasetItems(@PathVariable final String datasetId) {
        final DatasetDocument datasetDocument = getDatasetDocumentOrThrowNotFoundException(datasetId);
        return new ResponseEntity<>(datasetDocument.getDataset().getItems(), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItem(@PathVariable final String datasetId, @PathVariable final String itemId) {
        final DatasetDocument datasetDocument = getDatasetDocumentOrThrowNotFoundException(datasetId);

        final Optional<Item> itemOptional = datasetDocument
                .getDataset().getItems()
                .stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst();

        if (!itemOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(itemOptional.get(), OK);
    }

    @PostMapping
    public void addItemToDataset(@PathVariable final String datasetId, @RequestBody final Item item) {
        final DatasetDocument datasetDocument = getDatasetDocumentOrThrowNotFoundException(datasetId);
        datasetDocument.getDataset().getItems().add(item);
        datasetDocumentRepository.save(datasetDocument);

    }

    private DatasetDocument getDatasetDocumentOrThrowNotFoundException(final String datasetId) {
        final Optional<DatasetDocument> datasetDocument = datasetDocumentRepository.findById(datasetId);

        if (!datasetDocument.isPresent()) {
            throw new EntityNotFoundException();
        }
        return datasetDocument.get();
    }

}
