package com.epam.jwd.core_final.gui;

import javax.swing.JButton;
import javax.swing.JLabel;

public class SpaceshipWindow extends AbstractSpaceshipWindow{
    private static SpaceshipWindow instance;
    private JLabel text = new JLabel("Choose:");
    private JButton getSpaceship = new JButton("Get");
    private JButton updateSpaceship = new JButton("Update");
    private SpaceshipWindow() {
        super("Spaceship",true);
        text.setBounds(230,39,50,17);
        getSpaceship.setBounds(191,90,118,35);
        updateSpaceship.setBounds(191,143,118,35);
        getSpaceship.addActionListener(e->{
            setVisible(false);
            SpaceshipGetWindow.getInstance().setVisible(true);
        });
        updateSpaceship.addActionListener(e->{
            setVisible(false);
            SpaceshipUpdateWindow.getInstance().setVisible(true);
        });
        add(text);
        add(getSpaceship);
        add(updateSpaceship);
    }
    public synchronized static SpaceshipWindow getInstance(){
        if(instance==null){
            instance = new SpaceshipWindow();
        }
        return instance;
    }
}
