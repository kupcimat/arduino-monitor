package org.kupcimat.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

import java.sql.Timestamp;

import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogTest extends TestCase {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void testSerialization() throws Exception {
        final Log log = new Log(new Timestamp(1), singletonMap("value", 1.0));
        final String serialized = objectMapper.writeValueAsString(log);
        final Log deserialized = objectMapper.readValue(serialized, Log.class);

        assertThat(deserialized, is(log));
    }
}
