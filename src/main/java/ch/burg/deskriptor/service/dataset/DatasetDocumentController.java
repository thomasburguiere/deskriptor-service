package ch.burg.deskriptor.service.dataset;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public DatasetDocument get(@PathVariable final String datasetId) {
        return datasetDocumentRepository.findById(datasetId).get();
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody final DatasetDocument datasetDocument){
        datasetDocumentRepository.save(datasetDocument);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
