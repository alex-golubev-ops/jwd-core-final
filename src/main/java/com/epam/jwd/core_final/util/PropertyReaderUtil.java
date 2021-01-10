package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public final class PropertyReaderUtil {
    private static PropertyReaderUtil instance;
    private static final Properties properties = new Properties();
    private static Logger logger = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private PropertyReaderUtil() {
    }

    public Optional<String> getProperty(String key) {
        return Optional.of(properties.getProperty(key));
    }

    public synchronized static PropertyReaderUtil getInstance() {
        if (instance == null) {
            instance = new PropertyReaderUtil();
            loadProperties();
        }
        return instance;
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    private static void loadProperties() {
        final String propertiesFileName = "src/main/resources/application.properties";
        try (FileInputStream input = new FileInputStream(propertiesFileName)) {
            properties.load(input);
            logger.info("Properties were wrote");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
