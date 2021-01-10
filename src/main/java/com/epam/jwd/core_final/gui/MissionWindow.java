package com.epam.jwd.core_final.gui;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MissionWindow extends AbstractMissionWindow {
    private static MissionWindow instance;
    private JLabel text = new JLabel("Choose:");
    private JButton createMission = new JButton("Get");
    private JButton updateMission = new JButton("Update");
    private JButton writeMission = new JButton("Write");

    private MissionWindow() {
        super("Mission", true);
        text.setBounds(230, 39, 50, 17);
        createMission.setBounds(191, 90, 118, 35);
        updateMission.setBounds(191, 143, 118, 35);
        writeMission.setBounds(191, 196, 118, 35);
        createMission.addActionListener(e -> {
            setVisible(false);
            MissionCreateWindow.getInstance().setVisible(true);
        });
        updateMission.addActionListener(e -> {
            setVisible(false);
            MissionUpdateWindow.getInstance().setVisible(true);
        });
        writeMission.addActionListener(e -> {
            setVisible(false);
            MissionWriteWindow.getInstance().setVisible(true);
        });
        add(text);
        add(createMission);
        add(updateMission);
        add(writeMission);
    }

    public synchronized static MissionWindow getInstance() {
        if (instance == null) {
            instance = new MissionWindow();
        }
        return instance;
    }
}
