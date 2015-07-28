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
import java.util.Objects;

import static org.apache.commons.lang3.Validate.notNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Log {

    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private final Timestamp timestamp;
    private final double value;

    @JsonCreator
    public Log(@JsonProperty("timestamp") Timestamp timestamp,
               @JsonProperty("value") double value) {

        this.timestamp = notNull(timestamp, "timestamp cannot be null");
        this.value = value;
    }

    @JsonSerialize(using = TimestampJsonSerializer.class)
    @JsonDeserialize(using = TimestampJsonDeserializer.class)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Log log = (Log) o;
        return Objects.equals(value, log.value) &&
                Objects.equals(timestamp, log.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("timestamp", timestamp)
                .append("value", value)
                .toString();
    }
}
