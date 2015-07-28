package org.kupcimat.resources;

import org.kupcimat.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Service
public class LogDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void saveLog(Log log) {
        notNull(log, "log cannot be null");
        jdbcTemplate.update("INSERT INTO log VALUES (?,?)", log.getTimestamp(), log.getValue());
    }

    public List<Log> getAllLogs() {
        return jdbcTemplate.query("SELECT * FROM log ORDER BY timestamp DESC",
                (rs, rowNum) -> new Log(rs.getTimestamp("timestamp"), rs.getDouble("value")));
    }

    public void deleteAllLogs() {
        jdbcTemplate.update("DELETE FROM log");
    }
}
