package ch.burg.deskriptor.service.dataset;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
