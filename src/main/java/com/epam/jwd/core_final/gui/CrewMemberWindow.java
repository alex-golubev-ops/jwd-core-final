package com.epam.jwd.core_final.gui;

import javax.swing.JButton;
import javax.swing.JLabel;

public class CrewMemberWindow extends AbstractCrewMemberWindow {
    private static CrewMemberWindow instance;
    private JLabel text = new JLabel("Choose:");
    private JButton getMember = new JButton("Get");
    private JButton updateCrewMember = new JButton("Update");

    private CrewMemberWindow() {
        super("Crew Member", true);
        text.setBounds(230, 39, 50, 17);
        getMember.setBounds(191, 90, 118, 35);
        updateCrewMember.setBounds(191, 143, 118, 35);
        getMember.addActionListener(e -> {
            setVisible(false);
            CrewMemberGetWindow.getInstance().setVisible(true);
        });
        updateCrewMember.addActionListener(e -> {
            setVisible(false);
            CrewMemberUpdateWindow.getInstance().setVisible(true);
        });
        add(text);
        add(getMember);
        add(updateCrewMember);
    }

    public synchronized static CrewMemberWindow getInstance() {
        if (instance == null) {
            instance = new CrewMemberWindow();
        }
        return instance;
    }
}
