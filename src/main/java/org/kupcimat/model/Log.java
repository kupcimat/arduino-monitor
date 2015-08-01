package org.kupcimat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static com.google.common.collect.Maps.newHashMap;
import static org.apache.commons.lang3.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Log {

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private final Timestamp timestamp;
    private final Map<String, Double> values;

    @JsonCreator
    public Log(@JsonProperty("timestamp") Timestamp timestamp,
               @JsonProperty("values") Map<String, Double> values) {

        this.timestamp = notNull(timestamp, "timestamp cannot be null");
        this.values = newHashMap(notNull(values, "values cannot be null"));
    }

    @JsonSerialize(using = TimestampJsonSerializer.class)
    @JsonDeserialize(using = TimestampJsonDeserializer.class)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Map<String, Double> getValues() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Log log = (Log) o;
        return Objects.equals(timestamp, log.timestamp) &&
                Objects.equals(values, log.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, values);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("timestamp", timestamp)
                .append("values", values)
                .toString();
    }
}
