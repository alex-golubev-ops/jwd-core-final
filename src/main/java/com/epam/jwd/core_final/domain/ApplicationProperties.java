package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.util.PropertyReaderUtil;

/**
 * This class should be IMMUTABLE!
 * <p>
 * Expected fields:
 * <p>
 * inputRootDir {@link String} - base dir for all input files
 * outputRootDir {@link String} - base dir for all output files
 * crewFileName {@link String}
 * missionsFileName {@link String}
 * spaceshipsFileName {@link String}
 * <p>
 * fileRefreshRate {@link Integer}
 * dateTimeFormat {@link String} - date/time format for {@link java.time.format.DateTimeFormatter} pattern
 */
public final class ApplicationProperties {
    private static ApplicationProperties instance;
    private final String inputRootDir;
    private final String outputRootDir;
    private final String crewFileName;
    private final String missionsFileName;
    private final String spaceshipsFileName;
    private final Integer fileRefreshRate;
    private final String dataTimeFormat;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private ApplicationProperties() {
        PropertyReaderUtil propertyReader = PropertyReaderUtil.getInstance();
        this.inputRootDir = propertyReader.getProperty("inputRootDir").get();
        this.outputRootDir = propertyReader.getProperty("outputRootDir").get();
        this.crewFileName = propertyReader.getProperty("crewFileName").get();
        this.missionsFileName = propertyReader.getProperty("missionsFileName").get();
        this.spaceshipsFileName = propertyReader.getProperty("spaceshipsFileName").get();
        this.fileRefreshRate = Integer.parseInt(propertyReader.getProperty("fileRefreshRate").get());
        this.dataTimeFormat = propertyReader.getProperty("dateTimeFormat").get();
    }

    public String getInputRootDir() {
        return inputRootDir;
    }

    public String getOutputRootDir() {
        return outputRootDir;
    }

    public String getCrewFileName() {
        return crewFileName;
    }

    public String getMissionsFileName() {
        return missionsFileName;
    }

    public String getSpaceshipsFileName() {
        return spaceshipsFileName;
    }

    public Integer getFileRefreshRate() {
        return fileRefreshRate;
    }

    public String getDataTimeFormat() {
        return dataTimeFormat;
    }

    public static ApplicationProperties getInstance() {
        if (instance == null) {
            instance = new ApplicationProperties();
        }
        return instance;
    }
}
