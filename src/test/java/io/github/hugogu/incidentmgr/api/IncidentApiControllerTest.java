package io.github.hugogu.incidentmgr.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.hugogu.incidentmgr.api.dto.IncidentView;
import io.github.hugogu.incidentmgr.domain.IncidentStatus;
import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class IncidentApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @CsvSource(
            "classpath:data/creation-request.json"
    )
    public void overallFlowIntegrationTest(String requestFile) throws Exception {
        final String request = Files.readString(ResourceUtils.getFile(requestFile).toPath());

        val createResponse = mockMvc.perform(post("/api/incident")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.raiser").value("hugo"))
                .andExpect(jsonPath("$.status").value(IncidentStatus.OPEN.name()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();

        val incident = objectMapper.readValue(createResponse.getResponse().getContentAsString(), IncidentView.class);

        mockMvc.perform(patch("/api/incident:process/" + incident.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(IncidentStatus.RESOLVING.name()))
                .andReturn();

        mockMvc.perform(patch("/api/incident:close/" + incident.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(IncidentStatus.CLOSED.name()))
                .andReturn();

        mockMvc.perform(patch("/api/incident:process/" + incident.getId()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        mockMvc.perform(get("/api/incident")
                        .param("raiser", "hugo")
                        .param("status", "CLOSED")
                        .param("page", "0")
                        .param("size", "1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value("1"))
                .andExpect(jsonPath("$.content[0].raiser").value("hugo"))
                .andReturn();
    }
}
