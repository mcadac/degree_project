package edu.mirror.gui.view.api.impl.manager;

import edu.mirror.gui.view.api.AbstractModuleManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by McadaC on 5/19/18.
 */
public class ClockModuleManager extends AbstractModuleManager {

    /** interval change*/
    public static final int INTERVAL_CHANGE = 1000;

    /** Element to show */
    private Date date;

    /**
     * Constructor
     *
     * @param initModules
     */
    public ClockModuleManager(int initModules) {

        super(initModules);
    }

    /**
     * {@inheritDoc}
    */
    @Override
    protected int getInterval() {
        return INTERVAL_CHANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate() {
        this.date = new Date();
    }

    /**
     * a string of the current time at the given format
     *
     * @return
     */
    public String format(DateFormat format) {
        return format.format(this.date);
    }

    /**
     * convenience method that takes in a string
     *
     * @return
     */
    public String format(String format) {
        return new SimpleDateFormat(format).format(this.date);
    }
}
