package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.InputException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.List;

public class CrewMemberGetWindow extends AbstractCrewMemberWindow {
    private static CrewMemberGetWindow instance;
    private final String[] arrayOfRanks = {"NONE", "TRAINEE", "SECOND_OFFICER", "FIRST_OFFICER", "CAPTAIN"};
    private final String[] arrayOfRoles = {"NONE", "MISSION_SPECIALIST", "FLIGHT_ENGINEER", "PILOT", "COMMANDER"};
    private final JLabel choose = new JLabel("Choose:");
    private JLabel name = new JLabel("Name:");
    private JLabel rank = new JLabel("Rank:");
    private JLabel role = new JLabel("Role:");
    private JTextField idInput = new JTextField();
    private JTextField nameInput = new JTextField();
    private final JComboBox ranks = new JComboBox(arrayOfRanks);
    private JComboBox roles = new JComboBox(arrayOfRoles);
    private JCheckBox isReadyForNextMissions = new JCheckBox("is ready for next missions");
    private JButton find = new JButton("Find");
    private JTextArea output = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(output);
    private JLabel id = new JLabel("ID:");

    @SuppressWarnings({"ConstantConditions", "StringConcatenationInsideStringBufferAppend"})
    private CrewMemberGetWindow() {
        super("Crew Member", true);
        choose.setBounds(230, 39, 50, 17);
        id.setBounds(52, 99, 15, 17);
        name.setBounds(52, 136, 52, 17);
        rank.setBounds(261, 99, 33, 17);
        role.setBounds(261, 136, 33, 17);
        idInput.setBounds(127, 95, 80, 25);
        nameInput.setBounds(127, 132, 80, 25);
        ranks.setBounds(319, 95, 120, 25);
        roles.setBounds(319, 132, 120, 25);
        isReadyForNextMissions.setBounds(184, 175, 190, 17);
        find.setBounds(203, 215, 118, 35);
        output.setEnabled(false);
        scrollPane.setBounds(59, 263, 382, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        find.addActionListener(e -> {
            try {
                CrewMemberCriteria criteria = new CrewMemberCriteria();
                checkId(idInput.getText().trim(), criteria);
                checkName(nameInput.getText().trim(), criteria);
                checkRole((String) roles.getSelectedItem(), criteria);
                checkRank((String) ranks.getSelectedItem(), criteria);
                checkIsReadyForNextMissions(isReadyForNextMissions.isSelected(), criteria);
                StringBuilder allCrewMembers = new StringBuilder();
                List<CrewMember> allCrewMembersByCriteria = service.findAllCrewMembersByCriteria(criteria);
                for (CrewMember allCrewMembersByCriterion : allCrewMembersByCriteria) {
                    allCrewMembers.append(allCrewMembersByCriterion + "\n");
                }
                output.setText(allCrewMembers.toString());
            } catch (InputException ex) {
                output.setText("Incorrect input to the " + ex.getMessage() + " field");
            }
        });
        add(choose);
        add(id);
        add(name);
        add(rank);
        add(role);
        add(idInput);
        add(nameInput);
        add(ranks);
        add(roles);
        add(isReadyForNextMissions);
        add(find);
        add(scrollPane);
    }

    public static CrewMemberGetWindow getInstance() {
        if (instance == null) {
            instance = new CrewMemberGetWindow();
        }
        return instance;
    }

}
