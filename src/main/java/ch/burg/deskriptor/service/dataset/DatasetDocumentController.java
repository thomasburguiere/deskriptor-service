package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.Dataset;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/dataset")
public class DatasetDocumentController {

    private final DatasetDocumentRepository datasetDocumentRepository;

    public DatasetDocumentController(final DatasetDocumentRepository datasetDocumentRepository) {
        this.datasetDocumentRepository = datasetDocumentRepository;
    }

    @GetMapping(value = "/list")
    public List<DatasetDocument> list() {
        return datasetDocumentRepository.findAll();
    }

    @GetMapping(value = "/{datasetId}")
    public ResponseEntity<DatasetDocument> get(@PathVariable final String datasetId) {
        final Optional<DatasetDocument> datasetDocumentOptional = datasetDocumentRepository.findById(datasetId);

        if (!datasetDocumentOptional.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        return new ResponseEntity<>(datasetDocumentOptional.get(), OK);
    }

    @PostMapping(value = "/{datasetId}")
    public ResponseEntity<Void> post(@RequestBody final Dataset dataset, @PathVariable final String datasetId) {
        datasetDocumentRepository.save(new DatasetDocument(dataset, datasetId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
