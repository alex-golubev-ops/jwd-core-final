package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InputException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;

public class SpaceshipGetWindow extends AbstractSpaceshipWindow {
    private static SpaceshipGetWindow instance;
    private final JLabel choose = new JLabel("Choose:");
    private JLabel name = new JLabel("Name:");
    private JLabel flightDistance = new JLabel("Distance:");
    private JTextField idInput = new JTextField();
    private JTextField nameInput = new JTextField();
    private JCheckBox isReadyForNextMissions = new JCheckBox("is ready for next missions");
    private JButton find = new JButton("Find");
    private JTextArea output = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(output);
    private JLabel id = new JLabel("ID:");

    private SpaceshipGetWindow() {
        super("Spaceship", true);
        choose.setBounds(230, 39, 50, 17);
        id.setBounds(52, 99, 15, 17);
        name.setBounds(52, 136, 52, 17);
        idInput.setBounds(127, 95, 80, 25);
        nameInput.setBounds(127, 132, 80, 25);
        isReadyForNextMissions.setBounds(184, 175, 190, 17);
        find.setBounds(203, 215, 118, 35);
        output.setEnabled(false);
        scrollPane.setBounds(59, 263, 382, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        find.addActionListener(e -> {
            try {
                SpaceshipCriteria criteria = new SpaceshipCriteria();
                checkId(idInput.getText().trim(), criteria);
                checkName(nameInput.getText().trim(), criteria);
                checkIsReadyForNextMissions(isReadyForNextMissions.isSelected(), criteria);
                StringBuilder allSpaceship = new StringBuilder();
                List<Spaceship> allSpaceshipsByCriteria = service.findAllSpaceshipsByCriteria(criteria);
                for (Spaceship allSpaceshipsByCriterion : allSpaceshipsByCriteria) {
                    allSpaceship.append(allSpaceshipsByCriterion + "\n");
                }
                output.setText(allSpaceship.toString());
            } catch (InputException ex) {
                output.setText("Incorrect input to the " + ex.getMessage() + " field");
            }
        });
        add(choose);
        add(id);
        add(name);
        add(idInput);
        add(nameInput);
        add(isReadyForNextMissions);
        add(find);
        add(scrollPane);
    }

    public static SpaceshipGetWindow getInstance() {
        if (instance == null) {
            instance = new SpaceshipGetWindow();
        }
        return instance;
    }
}
