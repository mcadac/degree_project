package edu.mirror.gui.view.api;

/**
 * Created by McadaC on 5/15/18.
 */
public interface IUpdateStrategy {

    /**
     * Will be called by the model every time it updates
     *
     * @param moduleManager module that calls this method
     */
    void doUpdate(AbstractModuleManager moduleManager);
}
