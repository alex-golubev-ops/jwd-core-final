package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.gui.MainWindow;
import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        final MainWindow menu = MainWindow.getInstance();
        final NassaContext nassaContext = (NassaContext) menu.getApplicationContext();
        final Supplier<ApplicationContext> applicationContextSupplier = () -> nassaContext;
        nassaContext.init();
        menu.setVisible(true);
        return applicationContextSupplier::get;
    }
}
