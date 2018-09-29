package ch.burg.deskriptor.service.dataset;

import ch.burg.deskriptor.engine.model.descriptor.DiscreteDescriptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetDeserializationIntegrationTest {


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
    public void should_post_and_deserialize_dataset_correctly() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "   \"descriptors\": [{" +
                "      \"id\":\"discrete desc\"," +
                "      \"name\":\"discrete desc\"," +
                "      \"possibleStates\": [{" +
                "           \"name\": \"state\"" +
                "       }]" +
                "   }]," +
                "   \"items\": [{" +
                "       \"name\": \"item\"" +
                "   }]," +
                "   \"descriptorDependencyNodes\": null" +
                "}";

        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        final DatasetDocument datasetDocument = datasetDocumentRepository.findById("kurwa").get();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotNull();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotEmpty();

        final DiscreteDescriptor descr = (DiscreteDescriptor) datasetDocument.getDataset().getDescriptors().iterator().next();
        assertThat(descr.getId()).isEqualTo("discrete desc");
        assertThat(descr.getName()).isEqualTo("discrete desc");
        assertThat(descr.getPossibleSates()).hasSize(1);
        assertThat(descr.getPossibleSates().iterator().next().getName()).isEqualTo("state");

        // items
        assertThat(datasetDocument.getDataset().getItems().iterator().hasNext()).isTrue();
        assertThat(datasetDocument.getDataset().getItems().iterator().next().getName()).isEqualTo("item");
    }

    @Test
    public void should_deserialize_dataset_with_no_items() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "       \"descriptors\": [{" +
                "          \"id\":\"discrete desc\"," +
                "          \"name\":\"discrete desc\"," +
                "          \"possibleStates\": [{" +
                "               \"name\": \"state\"" +
                "           }]" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";


        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());


        final DatasetDocument datasetDocument = datasetDocumentRepository.findById("kurwa").get();

        // descriptors
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotNull();
        assertThat(datasetDocument.getDataset().getDescriptors()).hasSize(1);
    }


    @Test
    public void should_deserialize_dataset_with_no_descriptors() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "       \"items\": [{" +
                "           \"name\": \"item\"" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";


        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());


        final DatasetDocument datasetDocument = datasetDocumentRepository.findById("kurwa").get();

        // items
        assertThat(datasetDocument.getDataset().getItems()).isNotNull();
        assertThat(datasetDocument.getDataset().getItems()).hasSize(1);
    }

    @Test
    public void should_deserialize_dataset_with_discreteDescriptors_with_empty_states() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "       \"descriptors\": [{" +
                "          \"id\":\"discrete desc\"," +
                "          \"name\":\"discrete desc\"," +
                "          \"possibleStates\": []" +
                "       }]," +
                "       \"items\": [{" +
                "           \"name\": \"item\"" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";

        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        final DatasetDocument datasetDocument = datasetDocumentRepository.findById("kurwa").get();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotNull();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotEmpty();

        final DiscreteDescriptor descr = (DiscreteDescriptor) datasetDocument.getDataset().getDescriptors().iterator().next();
        assertThat(descr.getId()).isEqualTo("discrete desc");
        assertThat(descr.getName()).isEqualTo("discrete desc");
        assertThat(descr.getPossibleSates()).hasSize(0);

        // items
        assertThat(datasetDocument.getDataset().getItems().iterator().hasNext()).isTrue();
        assertThat(datasetDocument.getDataset().getItems().iterator().next().getName()).isEqualTo("item");
    }


    @Test
    public void should_deserialize_dataset_with_discreteDescriptors_with_null_states() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "       \"descriptors\": [{" +
                "          \"id\":\"discrete desc\"," +
                "          \"name\":\"discrete desc\"," +
                "          \"possibleStates\": null" +
                "       }]," +
                "       \"items\": [{" +
                "           \"name\": \"item\"" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";

        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        final DatasetDocument datasetDocument = datasetDocumentRepository.findById("kurwa").get();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotNull();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotEmpty();

        final DiscreteDescriptor descr = (DiscreteDescriptor) datasetDocument.getDataset().getDescriptors().iterator().next();
        assertThat(descr.getId()).isEqualTo("discrete desc");
        assertThat(descr.getName()).isEqualTo("discrete desc");
        assertThat(descr.getPossibleSates()).hasSize(0);

        // items
        assertThat(datasetDocument.getDataset().getItems().iterator().hasNext()).isTrue();
        assertThat(datasetDocument.getDataset().getItems().iterator().next().getName()).isEqualTo("item");
    }

    @Test
    public void should_return_400_when_unable_to_infer_descriptor_concrete_type_from_json_content() throws Exception {// given

        //language=JSON
        final String json = "{" +
                "       \"descriptors\": [{" +
                "          \"id\":\"discrete desc\"," +
                "          \"name\":\"discrete desc\"" +
                "       }]," +
                "       \"items\": [{" +
                "           \"name\": \"item\"" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";

        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void should_be_able_to_infer_descriptor_concrete_type_based_on_flag() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "       \"descriptors\": [{" +
                "          \"discrete\": true," +
                "          \"id\":\"discrete desc\"," +
                "          \"name\":\"discrete desc\"" +
                "       }]," +
                "       \"items\": [{" +
                "           \"name\": \"item\"" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";

        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void should_return_be_able_to_infer_descriptor_concrete_type_based_on_presence_of_states() throws Exception {
        // given
        //language=JSON
        final String json = "{" +
                "       \"descriptors\": [{" +
                "          \"possibleStates\": null," +
                "          \"id\":\"discrete desc\"," +
                "          \"name\":\"discrete desc\"" +
                "       }]," +
                "       \"items\": [{" +
                "           \"name\": \"item\"" +
                "       }]," +
                "       \"descriptorDependencyNodes\": null" +
                "}";

        // when / then
        mockMvc.perform(post(DATASET_BASE_URI + "/kurwa").content(json).contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        final DatasetDocument datasetDocument = datasetDocumentRepository.findById("kurwa").get();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotNull();
        assertThat(datasetDocument.getDataset().getDescriptors()).isNotEmpty();

        final DiscreteDescriptor descr = (DiscreteDescriptor) datasetDocument.getDataset().getDescriptors().iterator().next();
        assertThat(descr.getId()).isEqualTo("discrete desc");
        assertThat(descr.getName()).isEqualTo("discrete desc");
        assertThat(descr.getPossibleSates()).hasSize(0);
    }
}
