package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InputException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CrewMemberUpdateWindow extends AbstractCrewMemberWindow {
    private static CrewMemberUpdateWindow instance;
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
    private JButton update = new JButton("Update");
    private JTextArea output = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(output);

    private CrewMemberUpdateWindow() {
        super("Crew Member", true);
        choose.setBounds(230, 39, 50, 17);
        JLabel id = new JLabel("ID:");
        id.setBounds(52, 99, 15, 17);
        name.setBounds(52, 136, 52, 17);
        rank.setBounds(261, 99, 33, 17);
        role.setBounds(261, 136, 33, 17);
        idInput.setBounds(127, 95, 80, 25);
        nameInput.setBounds(127, 132, 80, 25);
        ranks.setBounds(319, 95, 120, 25);
        roles.setBounds(319, 132, 120, 25);
        isReadyForNextMissions.setBounds(184, 175, 190, 17);
        update.setBounds(203, 215, 118, 35);
        output.setEnabled(false);
        scrollPane.setBounds(59, 263, 382, 150);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
                String stringRole = (String) roles.getSelectedItem();
                Role role = null;
                if (!stringRole.equals("NONE")) {
                    role = Role.valueOf(stringRole);
                }
                String stringRank = (String) ranks.getSelectedItem();
                Rank rank = null;
                if (!stringRank.equals("NONE")) {
                    rank = Rank.valueOf(stringRank);
                }
                boolean isReady = this.isReadyForNextMissions.isSelected();
                CrewMember crewMember = new CrewMember(Long.parseLong(stringId), stringName, role, rank, isReady);
                service.updateCrewMemberDetails(crewMember);
                output.setText("Update crew member");
            } catch (InputException inputException) {
                output.setText("Incorrect input to the " + inputException.getMessage() + " field");
            } catch (UnknownEntityException entityException) {
                output.setText("The crew member is not exist");
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
        add(update);
        add(scrollPane);

    }

    public static CrewMemberUpdateWindow getInstance() {
        if (instance == null) {
            instance = new CrewMemberUpdateWindow();
        }
        return instance;
    }

}
