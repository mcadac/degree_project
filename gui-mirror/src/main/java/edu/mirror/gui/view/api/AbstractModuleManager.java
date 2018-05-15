package edu.mirror.gui.view.api;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by McadaC on 5/15/18.
 */
public abstract class AbstractModuleManager implements ActionListener {

    /**
     * Modules for manager control
     */
    private final List<AbstractModule> modules;

    /**
     * Constructor
     *
     * @param initModules
     */
    public AbstractModuleManager(final int initModules) {
        modules = new ArrayList<>(initModules);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }

    /**
     * Initialize manager
     */
    public void initialize() {

        update();
        final int interval = getInterval();

        if (getInterval() > 0) {

            Timer timer = new Timer(interval, this);
            timer.start();

        }
    }


    /**
     * Add an module for the manager
     *
     * @param  module with update strategy
     */
    public void addModule(AbstractModule module) {
        this.modules.add(module);
    }

    /**
     * updates this model and it's modules
     */
    protected void update() {
        onUpdate();
        invalidate();
    }


    /**
     * updates the view
     * use to update view after a network call
     */
    protected void invalidate() {
        modules.forEach( module -> module.doUpdate(this));
    }

    /**
     * Time in milliseconds it will take for this model to doUpdate it's data
     *
     * @return a time in milliseconds
     */
    protected abstract int getInterval();

    /**
     * Updates the data of this model
     *
     * do not call this method directly instead call the update() method
     */
    protected abstract void onUpdate();


}
