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

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

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
        mockMvc.perform(get("/dataset/" + datasetId + "/item/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void should_find_item_in_dataset() throws Exception {
        // given
        final String datasetId = "datasetId";
        final String itemId = "itemId";

        final Dataset dataset = Dataset
                .builder()
                .items(Set.of(Item
                        .builder()
                        .withId(itemId)
                        .withName("itemName")
                        .build())
                ).build();

        final DatasetDocument datasetDocument = new DatasetDocument(dataset, datasetId);
        datasetDocumentRepository.save(datasetDocument);

        // when / then
        mockMvc.perform(get("/dataset/datasetId/item/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("itemId"));
    }

    @Test
    public void should_post_item_in_dataset() throws Exception {

        // given
        final String datasetId = "datasetId";

        final Dataset dataset = Dataset
                .builder()
                .items(Collections.emptySet())
                .build();

        final DatasetDocument datasetDocument = new DatasetDocument(dataset, datasetId);
        datasetDocumentRepository.save(datasetDocument);

        // when
        //language=JSON
        final String json = "{" +
                "   \"id\": \"posted_item_id\"," +
                "   \"name\": \"posted_item_name\"" +
                "}";

        mockMvc.perform(post("/dataset/datasetId/item").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());


        // then
        assertThat(datasetDocumentRepository.findById("datasetId").get().getDataset().getItems()).hasSize(1);
    }

}
