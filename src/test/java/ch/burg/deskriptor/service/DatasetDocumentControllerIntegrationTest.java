package ch.burg.deskriptor.service;

import ch.burg.deskriptor.service.dataset.DatasetDocument;
import ch.burg.deskriptor.service.dataset.DatasetDocumentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    public void shouldListDatasets() throws Exception {
        // given
        datasetDocumentRepository.save(new DatasetDocument());

        // then
        mockMvc.perform(get(DATASET_BASE_URI + "/list" ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
