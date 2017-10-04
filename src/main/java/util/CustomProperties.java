package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomProperties extends Properties {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CustomProperties(DataSource dataSource) {
        super();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> configs = jdbcTemplate
                .queryForList("select config_key, config_value from config_params");

        logger.info("Loading properties from Database");
        for (Map<String, Object> config : configs) {
            setProperty((config.get("config_key")).toString(), (config.get("config_value")).toString());
        }
    }
}
