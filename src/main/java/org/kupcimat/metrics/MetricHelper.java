package org.kupcimat.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class MetricHelper {

    @Autowired
    private MetricRegistry metricRegistry;

    public <T> T time(String metricName, Supplier<T> measuredCode) {
        notEmpty(metricName, "metricName cannot be empty");
        notNull(measuredCode, "measuredCode cannot be null");

        final Timer timer = metricRegistry.timer(metricName);
        final Timer.Context context = timer.time();

        final T result;
        try {
            result = measuredCode.get();
        } finally {
            context.stop();
        }
        return result;
    }
}
