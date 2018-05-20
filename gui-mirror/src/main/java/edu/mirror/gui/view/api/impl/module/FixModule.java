package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;

import java.awt.*;

/**
 * Created by McadaC on 5/20/18.
 */
public class FixModule extends AbstractModule {

    /**
     * The three methods below are used by the box layout
     * to force the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return null;
    }

    /**
     * Will be called by the model every time it updates
     *
     * @param moduleManager model that calls this method
     */
    @Override
    public void doUpdate(AbstractModuleManager moduleManager) {

    }

}
