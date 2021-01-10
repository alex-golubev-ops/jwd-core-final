package com.epam.jwd.core_final.gui;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.exception.InputException;
import javax.swing.JFrame;


public class AbstractWindow extends JFrame implements ApplicationMenu {
    protected static NassaContext context = new NassaContext();

    public AbstractWindow() {
        setTitle(getClass().getSimpleName());
        init();

    }

    public AbstractWindow(String name) {
        setTitle(name);
        init();
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return context;
    }

    private void init() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(null);

    }

    protected void checkCorrectId(String id) throws InputException {
        if (!id.matches("[\\d]+") && !id.equals("")) {
            throw new InputException("id");
        }
    }

    protected void checkCorrectName(String name) throws InputException {
        if (!name.matches("[A-Za-z ]+") && !name.equals("")) {
            throw new InputException("name");
        }
    }

    protected void checkId(String id, Criteria criteria) throws InputException {
        checkCorrectId(id);
        if (!id.equals("")) {
            criteria.setId(Long.parseLong(id));
        }
    }

    protected void checkName(String name, Criteria criteria) throws InputException {
        checkCorrectName(name);
        if (!name.equals("")) {
            criteria.setName(name);
        }
    }
}
