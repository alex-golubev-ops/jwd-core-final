package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InputException;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import javax.swing.*;

public class AbstractSpaceshipWindow extends AbstractWindow {
    private JButton back = new JButton("<");
    private boolean buttonBack;
    protected SpaceshipService service = SpaceshipServiceImpl.getInstance(context);

    public AbstractSpaceshipWindow(String name, boolean buttonBack) {
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

    protected void checkId(String id, CrewMemberCriteria criteria) throws InputException {
        checkCorrectId(id);
        if (!id.equals("")) {
            criteria.setId(Long.parseLong(id));
        }
    }

    protected void checkName(String name, CrewMemberCriteria criteria) throws InputException {
        checkCorrectName(name);
        if (!name.equals("")) {
            criteria.setName(name);
        }
    }

    protected void checkIsReadyForNextMissions(boolean isReady, SpaceshipCriteria criteria){
        criteria.setReadyForNextMissions(isReady);
    }
}
