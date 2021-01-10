package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.InputException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MissionUpdateWindow extends AbstractMissionWindow {
    private static MissionUpdateWindow instance;
    private JLabel enter = new JLabel("Enter information:");
    private JLabel id = new JLabel("Id:");
    private JLabel start = new JLabel("StartDate:");
    private JLabel end = new JLabel("EndDate:");
    private JLabel distance = new JLabel("Distance:");
    private JLabel mission = new JLabel("MissionResult:");
    private JTextField inputId = new JTextField();
    private MaskFormatter mf;
    private JFormattedTextField inputStartDate;
    private JFormattedTextField inputEndDate;
    private JTextField inputDistance = new JTextField();
    private final String[] arrayOfMissionResult = {"NONE", "CANCELLED", "FAILED",
            "PLANNED", "IN_PROGRESS", "COMPLETED"};
    private JComboBox inputMissionResult = new JComboBox(arrayOfMissionResult);
    private JButton update = new JButton("Update");
    private JTextArea output = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(output);

    private MissionUpdateWindow() {
        super("Mission", true);
        try {
            enter.setBounds(204, 39, 120, 17);
            id.setBounds(38, 86, 40, 17);
            start.setBounds(38, 130, 70, 17);
            end.setBounds(38, 176, 70, 17);
            distance.setBounds(38, 223, 70, 17);
            mission.setBounds(38, 263, 100, 17);
            inputId.setBounds(141, 84, 150, 25);
            mf = new MaskFormatter(generationFormat());
            mf.setPlaceholderCharacter('_');
            inputStartDate = new JFormattedTextField(mf);
            inputStartDate.setBounds(141, 128, 150, 25);
            inputEndDate = new JFormattedTextField(mf);
            inputEndDate.setBounds(141, 174, 150, 25);
            inputDistance.setBounds(141, 221, 150, 25);
            inputMissionResult.setBounds(141, 261, 95, 25);
            update.setBounds(184, 301, 100, 25);
            scrollPane.setBounds(136, 353, 200, 100);
            output.setEnabled(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            update.addActionListener(e -> {
                try {
                    if (inputId.getText().trim().equals("")) {
                        throw new InputException("id");
                    }
                    checkCorrectId(inputId.getText().trim());
                    LocalDate startDate = null;
                    if (!inputStartDate.getText().equals("____-__-__ __:__:__")) {
                        startDate = LocalDate.parse(inputStartDate.getText(),
                                DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDataTimeFormat()));
                    }
                    LocalDate endDate = null;
                    if (!inputEndDate.getText().equals("____-__-__ __:__:__")) {
                        endDate = LocalDate.parse(inputEndDate.getText(),
                                DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDataTimeFormat()));
                    }
                    Long distance = null;
                    if (!inputDistance.getText().equals("")) {
                        Long.parseLong(inputDistance.getText());
                    }
                    MissionResult result = null;
                    if (!((String) inputMissionResult.getSelectedItem()).equals("NONE")) {
                        result = MissionResult.valueOf((String) inputMissionResult.getSelectedItem());
                    }
                    FlightMission mission = new FlightMission(Long.parseLong(inputId.getText()),
                            startDate, endDate, distance, result);
                    service.updateSpaceshipDetails(mission);
                    output.setText("Mission was update");

                } catch (UnknownEntityException ex) {
                    output.setText("The mission is not exist");
                } catch (DateTimeParseException exception) {
                    output.setText("Incorrect date-time format");
                } catch (NumberFormatException exception) {
                    output.setText("Incorrect input to the distance field");
                } catch (InputException inputException) {
                    output.setText("Incorrect input to the " + inputException.getMessage() + " field");
                }

            });
            add(inputId);
            add(inputStartDate);
            add(inputEndDate);
            add(inputDistance);
            add(inputMissionResult);
            add(update);
            add(scrollPane);
            add(enter);
            add(id);
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

    public static MissionUpdateWindow getInstance() {
        if (instance == null) {
            instance = new MissionUpdateWindow();
        }
        return instance;
    }

}
