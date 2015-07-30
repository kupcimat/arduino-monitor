package org.kupcimat.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.kupcimat.ArduinoMonitorApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ArduinoMonitorApplication.class)
@WebAppConfiguration
public class AbstractControllerTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webAppContext;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.webAppContext).build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Serialize object to json string.
     */
    protected String serialize(Object value) throws IOException {
        return objectMapper.writeValueAsString(value);
    }

    /**
     * Deserialize json string to object of type clazz.
     */
    protected <T> T deserialize(String value, Class<T> clazz) throws IOException {
        return objectMapper.readValue(value, clazz);
    }
}
