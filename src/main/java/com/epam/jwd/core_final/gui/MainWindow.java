package com.epam.jwd.core_final.gui;

import javax.swing.JButton;
import javax.swing.JLabel;


public class MainWindow extends AbstractWindow {
    private static MainWindow instance;
    private JLabel text = new JLabel("Choose:");
    private JButton crewMember = new JButton("CrewMember");
    private JButton spaceship = new JButton("Spaceship");
    private JButton mission = new JButton("Mission");

    private MainWindow() {
        super();
        text.setBounds(230, 39, 50, 17);
        crewMember.setBounds(191, 90, 118, 35);
        spaceship.setBounds(191, 143, 118, 35);
        mission.setBounds(191, 200, 118, 35);
        crewMember.addActionListener(e -> {
            setVisible(false);
            CrewMemberWindow.getInstance().setVisible(true);
        });
        mission.addActionListener(e -> {
            setVisible(false);
            MissionWindow.getInstance().setVisible(true);
        });
        spaceship.addActionListener(e -> {
            setVisible(false);
            SpaceshipWindow.getInstance().setVisible(true);
        });
        add(text);
        add(crewMember);
        add(spaceship);
        add(mission);
    }

    public synchronized static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

}
