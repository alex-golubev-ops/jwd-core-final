package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.impl.MissionServiceImpl;

import javax.swing.JButton;

public class AbstractMissionWindow extends AbstractWindow{
    private JButton back = new JButton("<");
    private boolean buttonBack;
    protected MissionService service = MissionServiceImpl.getInstance(context);

    public AbstractMissionWindow(String name, boolean buttonBack) {
        super(name);
        this.buttonBack = buttonBack;
        init();
    }

    private void init() {
        if (buttonBack) {
            back.setBounds(12, 14, 50, 30);
            back.addActionListener(e -> {
                setVisible(false);
                MainWindow.getInstance().setVisible(true);
            });
            add(back);
        }
    }
}
