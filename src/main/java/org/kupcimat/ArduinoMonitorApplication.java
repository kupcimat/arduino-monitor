package org.kupcimat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class ArduinoMonitorApplication extends SpringBootServletInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ArduinoMonitorApplication.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Create log table status=start");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS log (timestamp TIMESTAMP, values VARCHAR(256))");
        log.info("Create log table status=finish");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ArduinoMonitorApplication.class);

    }

    public static void main(String[] args) {
        SpringApplication.run(ArduinoMonitorApplication.class, args);
    }
}
