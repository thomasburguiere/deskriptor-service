package ch.burg.deskriptor.service.item;

import ch.burg.deskriptor.engine.model.Dataset;
import ch.burg.deskriptor.engine.model.Item;
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

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {


    private static final String ITEM_BASE_URI = "/item";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatasetDocumentRepository datasetDocumentRepository;

    @Before
    public void beforeEach() {
        datasetDocumentRepository.deleteAll();
    }

    @Test
    public void should_list_dataset_items() throws Exception {
        // given
        final String datasetId = "datasetId";

        final Dataset dataset = Dataset
                .builder()
                .items(Set.of(Item
                        .builder()
                        .withId("itemId")
                        .withName("itemName")
                        .build())
                ).build();

        final DatasetDocument datasetDocument = new DatasetDocument(dataset, datasetId);
        datasetDocumentRepository.save(datasetDocument);

        // when / then
        mockMvc.perform(get(ITEM_BASE_URI + "/dataset/" + datasetId + "/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

}
