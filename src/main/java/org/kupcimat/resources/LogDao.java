package org.kupcimat.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.kupcimat.metrics.MetricHelper;
import org.kupcimat.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class LogDao {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MetricHelper metricHelper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveLog(Log log) {
        notNull(log, "log cannot be null");

        metricHelper.time("db.save_log",
                () -> jdbcTemplate.update("INSERT INTO log VALUES (?,?)", log.getTimestamp(), serializeValues(log.getValues()))
        );
    }

    public List<Log> getAllLogs(int limit) {
        isTrue(limit > 0, "limit must be greater than 0");

        return metricHelper.time("db.get_all_logs",
                () -> jdbcTemplate.query("SELECT * FROM log ORDER BY timestamp DESC LIMIT " + limit,
                        (rs, rowNum) -> new Log(rs.getTimestamp("timestamp"), deserializeValues(rs.getString("values"))))
        );
    }

    public List<Log> getAllLogs(String logType, int limit) {
        notEmpty(logType, "logType cannot be empty");

        return getAllLogs(limit).stream()
                .filter(log -> log.getValues().containsKey(logType))
                .map(log -> new Log(log.getTimestamp(), singletonMap(logType, log.getValues().get(logType))))
                .collect(toList());
    }

    public void deleteAllLogs() {
        metricHelper.time("db.delete_all_logs",
                () -> jdbcTemplate.update("DELETE FROM log")
        );
    }

    private static String serializeValues(Map<String, Double> values) {
        try {
            return OBJECT_MAPPER.writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Double> deserializeValues(String values) {
        try {
            return OBJECT_MAPPER.readValue(values, new TypeReference<Map<String, Double>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
