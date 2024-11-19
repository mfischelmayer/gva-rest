package at.fischelmayer.gvarest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EndpointTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void code() throws Exception {
        // Send a multipart request with file and additional fields
        mockMvc.perform(get("/code")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    String body = mvcResult.getResponse().getContentAsString();
                    Assertions.assertThat(body).isEqualTo("3");
                });

    }

}

