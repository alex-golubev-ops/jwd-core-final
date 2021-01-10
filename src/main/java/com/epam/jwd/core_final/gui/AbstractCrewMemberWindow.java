package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.InputException;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.impl.CrewServiceImpl;
import javax.swing.JButton;

public class AbstractCrewMemberWindow extends AbstractWindow {
    private JButton back = new JButton("<");
    private boolean buttonBack;
    protected CrewService service = CrewServiceImpl.getInstance(context);

    public AbstractCrewMemberWindow(String name, boolean buttonBack) {
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

    protected void checkRole(String role, CrewMemberCriteria criteria) throws InputException {
        if (!role.equals("NONE")) {
            criteria.setRole(Role.valueOf(role));
        }
    }

    protected void checkRank(String rank, CrewMemberCriteria criteria) throws InputException {
        if (!rank.equals("NONE")) {
            criteria.setRank(Rank.valueOf(rank));
        }
    }

    protected void checkIsReadyForNextMissions(boolean isReady, CrewMemberCriteria criteria){
        criteria.setReadyForNextMissions(isReady);
    }

}
