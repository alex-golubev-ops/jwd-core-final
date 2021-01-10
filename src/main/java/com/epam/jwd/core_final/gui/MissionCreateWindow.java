package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.exception.DuplicateEntityException;
import com.epam.jwd.core_final.exception.InputException;
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


public class MissionCreateWindow extends AbstractMissionWindow {
    private static MissionCreateWindow instance;
    private JLabel enter = new JLabel("Enter information:");
    private JLabel name = new JLabel("Name:");
    private JLabel start = new JLabel("StartDate:");
    private JLabel end = new JLabel("EndDate:");
    private JLabel distance = new JLabel("Distance:");
    private JLabel mission = new JLabel("MissionResult:");
    private JTextField nameInput = new JTextField();
    private MaskFormatter mf;
    private JFormattedTextField inputStartDate;
    private JFormattedTextField inputEndDate;
    private JTextField inputDistance = new JTextField();
    private final String[] arrayOfMissionResult = {"CANCELLED", "FAILED", "PLANNED", "IN_PROGRESS", "COMPLETED"};
    private JComboBox inputMissionResult = new JComboBox(arrayOfMissionResult);
    private JButton create = new JButton("Create");
    private JTextArea output = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(output);

    private MissionCreateWindow() {
        super("Mission", true);
        try {
            enter.setBounds(204, 39, 120, 17);
            name.setBounds(38, 86, 40, 17);
            start.setBounds(38, 130, 70, 17);
            end.setBounds(38, 176, 70, 17);
            distance.setBounds(38, 223, 70, 17);
            mission.setBounds(38, 263, 100, 17);
            nameInput.setBounds(141, 84, 150, 25);
            mf = new MaskFormatter(generationFormat());
            mf.setPlaceholderCharacter('_');
            inputStartDate = new JFormattedTextField(mf);
            inputStartDate.setBounds(141, 128, 150, 25);
            inputEndDate = new JFormattedTextField(mf);
            inputEndDate.setBounds(141, 174, 150, 25);
            inputDistance.setBounds(141, 221, 150, 25);
            inputMissionResult.setBounds(141, 261, 95, 25);
            create.setBounds(184, 301, 100, 25);
            scrollPane.setBounds(136, 353, 200, 70);
            output.setEnabled(false);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            create.addActionListener(e -> {
                try {
                    String stringName = nameInput.getText().trim();
                    if (stringName.equals("")) {
                        throw new InputException("name");
                    }
                    checkCorrectName(stringName);
                    LocalDate startDate = LocalDate.parse(inputStartDate.getText(),
                            DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDataTimeFormat()));
                    LocalDate endDate = LocalDate.parse(inputEndDate.getText(),
                            DateTimeFormatter.ofPattern(ApplicationProperties.getInstance().getDataTimeFormat()));
                    Long distance = Long.parseLong(inputDistance.getText().trim());
                    MissionResult result = MissionResult.valueOf((String) inputMissionResult.getSelectedItem());
                    FlightMission flightMission = new FlightMission(stringName, startDate, endDate, distance, result);
                    service.createMission(flightMission);
                    output.setText("Mission was add");
                } catch (InputException inputException) {
                    output.setText("Incorrect input to the " + inputException.getMessage() + " field");
                } catch (DateTimeParseException exception) {
                    output.setText("Incorrect date-time format");
                } catch (NumberFormatException exception) {
                    output.setText("Incorrect input to the distance field");
                } catch (DuplicateEntityException duplicateEntityException) {
                    output.setText("The mission with the same name already exist");
                }
            });
            add(nameInput);
            add(inputStartDate);
            add(inputEndDate);
            add(inputDistance);
            add(inputMissionResult);
            add(create);
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

    public static MissionCreateWindow getInstance() {
        if (instance == null) {
            instance = new MissionCreateWindow();
        }
        return instance;
    }

}
