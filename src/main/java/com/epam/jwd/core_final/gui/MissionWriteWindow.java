package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.InputException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MissionWriteWindow extends AbstractMissionWindow {
    private static MissionWriteWindow instance;
    private JLabel enter = new JLabel("Enter information:");
    private JLabel name = new JLabel("Name:");
    private JLabel start = new JLabel("StartDate:");
    private JLabel end = new JLabel("EndDate:");
    private JLabel distance = new JLabel("Distance:");
    private JLabel mission = new JLabel("MissionResult:");
    private JTextField inputName = new JTextField();
    private MaskFormatter mf;
    private JFormattedTextField inputStartDate;
    private JFormattedTextField inputEndDate;
    private JTextField inputDistance = new JTextField();
    private final String[] arrayOfMissionResult = {"NONE", "CANCELLED", "FAILED", "PLANNED", "IN_PROGRESS", "COMPLETED"};
    private JComboBox inputMissionResult = new JComboBox(arrayOfMissionResult);
    private JButton write = new JButton("Write");
    private JTextArea output = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(output);

    private MissionWriteWindow() {
        super("Mission", true);
        try {
            enter.setBounds(204, 39, 120, 17);
            name.setBounds(38, 86, 40, 17);
            start.setBounds(38, 130, 70, 17);
            end.setBounds(38, 176, 70, 17);
            distance.setBounds(38, 223, 70, 17);
            mission.setBounds(38, 263, 100, 17);
            inputName.setBounds(141, 84, 150, 25);
            mf = new MaskFormatter(generationFormat());
            mf.setPlaceholderCharacter('_');
            inputStartDate = new JFormattedTextField(mf);
            inputStartDate.setBounds(141, 128, 150, 25);
            inputEndDate = new JFormattedTextField(mf);
            inputEndDate.setBounds(141, 174, 150, 25);
            inputDistance.setBounds(141, 221, 150, 25);
            inputMissionResult.setBounds(141, 261, 95, 25);
            write.setBounds(184, 301, 100, 25);
            scrollPane.setBounds(136, 353, 220, 100);
            output.setEnabled(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            write.addActionListener(e -> {
                try {
                    FlightMissionCriteria criteria = new FlightMissionCriteria();
                    if (!inputName.getText().trim().equals("")) {
                        checkCorrectName(inputName.getText().trim());
                        criteria.setName(inputName.getText().trim());
                    }
                    if (!inputStartDate.getText().equals("____-__-__ __:__:__")) {
                        criteria.setStartDate(LocalDate.parse(inputStartDate.getText(),
                                DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDataTimeFormat())));
                    }
                    if (!inputEndDate.getText().equals("____-__-__ __:__:__")) {
                        criteria.setEndDate(LocalDate.parse(inputEndDate.getText(),
                                DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDataTimeFormat())));
                    }
                    if (!inputDistance.getText().trim().equals("")) {
                        criteria.setDistance(Long.parseLong(inputDistance.getText().trim()));
                    }
                    if (!((String) inputMissionResult.getSelectedItem()).equals("NONE")) {
                        criteria.setMissionResult(MissionResult.valueOf((String) inputMissionResult.getSelectedItem()));
                    }
                    List<FlightMission> allMissionsByCriteria = service.findAllMissionsByCriteria(criteria);
                    if (allMissionsByCriteria.size() == 0) {
                        output.setText("Mission not found");
                        return;
                    }
                    writeToFile(allMissionsByCriteria);
                    output.setText(allMissionsByCriteria.size() + " were writed to the file " +
                            ApplicationProperties.getInstance().getMissionsFileName());

                } catch (InputException inputException) {
                    output.setText("Incorrect input to the " + inputException.getMessage() + " field");
                } catch (DateTimeParseException exception) {
                    output.setText("Incorrect date-time format");
                } catch (NumberFormatException exception) {
                    output.setText("Incorrect input to the distance field");
                }
            });
            add(inputName);
            add(inputStartDate);
            add(inputEndDate);
            add(inputDistance);
            add(inputMissionResult);
            add(write);
            add(scrollPane);
            add(enter);
            add(name);
            add(start);
            add(end);
            add(distance);
            add(mission);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generationFormat() {
        ApplicationProperties instance = ApplicationProperties.getInstance();
        String dataTimeFormat = instance.getDataTimeFormat();
        return dataTimeFormat.replaceAll("\\w", "#");
    }

    public static MissionWriteWindow getInstance() {
        if (instance == null) {
            instance = new MissionWriteWindow();
        }
        return instance;
    }

    private void writeToFile(List<FlightMission> missions) {
        ApplicationProperties properties = ApplicationProperties.getInstance();
        try {
            if (!Files.isDirectory(Paths.get(properties.getOutputRootDir()))) {
                Files.createDirectory(Paths.get(properties.getOutputRootDir()));
            }
            if (!Files.isRegularFile(
                    Paths.get(properties.getOutputRootDir() + "/" + properties.getMissionsFileName()))) {
                Files.createFile(
                        Paths.get(properties.getOutputRootDir() + "/" + properties.getMissionsFileName()));
            }
            for (FlightMission flightMission : missions) {
                Files.write(Paths.get(properties.getOutputRootDir() + "/" + properties.getMissionsFileName()),
                        (flightMission.toString() + "\n").getBytes(), StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
