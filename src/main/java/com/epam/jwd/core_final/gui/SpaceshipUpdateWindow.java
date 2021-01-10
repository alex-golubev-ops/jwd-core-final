package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InputException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SpaceshipUpdateWindow extends AbstractSpaceshipWindow {
    private static SpaceshipUpdateWindow instance;
    private JLabel choose = new JLabel("Choose:");
    private JLabel id = new JLabel("ID:");
    private JLabel name = new JLabel("Name:");
    private JLabel distance = new JLabel("Distance:");
    private JTextField idInput = new JTextField();
    private JTextField nameInput = new JTextField();
    private JTextField distanceInput = new JTextField();
    private JCheckBox isReadyForNextMissions = new JCheckBox("is ready for next missions");
    private JButton update = new JButton("Update");
    private JTextArea output = new JTextArea();

    private SpaceshipUpdateWindow() {
        super("Spaceship", true);
        choose.setBounds(230, 39, 50, 17);
        id.setBounds(52, 99, 15, 17);
        name.setBounds(52, 136, 52, 17);
        distance.setBounds(261, 99, 55, 17);
        idInput.setBounds(127, 95, 80, 25);
        nameInput.setBounds(127, 132, 80, 25);
        distanceInput.setBounds(319, 95, 80, 25);
        isReadyForNextMissions.setBounds(282, 136, 190, 17);
        update.setBounds(203, 215, 118, 35);
        output.setEnabled(false);
        output.setBounds(59, 263, 382, 150);
        update.addActionListener(e -> {
            try {
                String stringId = idInput.getText().trim();
                if (stringId.equals("")) {
                    throw new InputException("id");
                }
                checkCorrectId(stringId);
                String stringName = nameInput.getText().trim();
                if (stringName.equals("")) {
                    stringName = null;
                } else {
                    checkCorrectName(stringName);
                }
                Long distance = null;
                if (!distanceInput.getText().equals("")) {
                    distance = Long.parseLong(distanceInput.getText().trim());
                }
                boolean isReady = this.isReadyForNextMissions.isSelected();
                Spaceship spaceship = new Spaceship(Long.parseLong(stringId), stringName, null, distance, isReady);
                service.updateSpaceshipDetails(spaceship);
                output.setText("Update spaceship");
            } catch (InputException inputException) {
                output.setText("Incorrect input to the " + inputException.getMessage() + " field");
            } catch (UnknownEntityException entityException) {
                output.setText("The spaceship is not exist");
            }
        });
        add(choose);
        add(id);
        add(name);
        add(distance);
        add(idInput);
        add(nameInput);
        add(isReadyForNextMissions);
        add(update);
        add(output);
        add(distanceInput);
    }

    public static SpaceshipUpdateWindow getInstance() {
        if (instance == null) {
            instance = new SpaceshipUpdateWindow();
        }
        return instance;
    }
}
