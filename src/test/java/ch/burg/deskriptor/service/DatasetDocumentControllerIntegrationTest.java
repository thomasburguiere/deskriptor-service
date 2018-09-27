package ch.burg.deskriptor.service;

import ch.burg.deskriptor.engine.model.Dataset;
import ch.burg.deskriptor.service.dataset.DatasetDocument;
import ch.burg.deskriptor.service.dataset.DatasetDocumentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetDocumentControllerIntegrationTest {


    private static final String DATASET_BASE_URI = "/dataset";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatasetDocumentRepository datasetDocumentRepository;

    @Before
    public void beforeEach() {
        datasetDocumentRepository.deleteAll();
    }

    @Test
    public void should_list_datasets() throws Exception {
        // given
        datasetDocumentRepository.save(new DatasetDocument());

        // then
        mockMvc.perform(get(DATASET_BASE_URI + "/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void should_return_404_when_no_dataset_document_is_found() throws Exception {
        // when / then
        mockMvc.perform(get(DATASET_BASE_URI + "/doesntExistsId"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void should_get_datasetDocument() throws Exception {
        // given
        final Dataset emptyDataset = new Dataset(Set.of(), Set.of(), Collections.emptyList());
        final DatasetDocument datasetDocument = new DatasetDocument(emptyDataset, "datasetId");
        datasetDocumentRepository.save(datasetDocument);

        // when / then
        mockMvc.perform(get(DATASET_BASE_URI + "/datasetId"))
            .andExpect(status().is2xxSuccessful());

    }

}
