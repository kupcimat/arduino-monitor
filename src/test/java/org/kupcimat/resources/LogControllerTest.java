package org.kupcimat.resources;

import org.junit.Test;
import org.kupcimat.model.Log;
import org.springframework.http.MediaType;

import java.sql.Timestamp;

import static java.util.Collections.singletonMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LogControllerTest extends AbstractControllerTest {

    private static final Log LOG = new Log(new Timestamp(1), singletonMap("value", 1.0));

    @Test
    public void createLog() throws Exception {
        mockMvc.perform(post("/logs")
                .content(serialize(LOG))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getLogs() throws Exception {
        mockMvc.perform(get("/logs"))
                .andExpect(status().isOk());
    }

    @Test
    public void getSelectedLogs() throws Exception {
        mockMvc.perform(get("/logs/type"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteLogs() throws Exception {
        mockMvc.perform(delete("/logs"))
                .andExpect(status().isNoContent());
    }
}
