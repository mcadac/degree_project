package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.animation.AnimationManager;
import edu.mirror.gui.view.animation.interpolators.DecelerateInterpolator;
import edu.mirror.gui.view.animation.listener.UpdateListener;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.WeatherModuleManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by McadaC on 5/19/18.
 */
public class WeatherForecastModule extends AbstractModule implements ActionListener {

    /** Module dimention */
    private float DIMENTION = 100f;

    /** Daily codes */
    private String[] dailyCodes;

    /** Hourly codes */
    private String[] hourlyCodes;

    /** Daily temperature */
    private float[] dailyTemps;

    /** Hourly temperature */
    private float[] hourlyTemps;

    /** Daily times*/
    private Date[] dailyTimes;

    /** Hourly times */
    private Date[] hourlyTimes;

    /** Timer to move information */
    private Timer timer;

    /** Curr x */
    private int currX = 0;

    /** Gap */
    private int gap = (int)(DIMENTION / 5);

    /** Daily flag*/
    private boolean onDaily = true;

    /**
     * Called when the view is initialized
     */
    @Override
    public void onInitialize() {
        this.timer = new Timer(8000, this);
        this.timer.start();
    }

    /**
     * Paints each of the components in this container.
     *
     * @param graphics
     */
    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        if (dailyCodes == null) {
            return;
        }

        final Graphics2D g2d = getGraphics2D(graphics);
        drawDaily(currX, g2d);
        drawHourly(currX + getWidth() + gap, g2d);
    }

    /**
     * Draws icons
     *
     * @param icon
     * @param x
     * @param y
     * @param graphics2D
     */
    private void drawIconAt(final String icon, int x, int y,  final Graphics2D graphics2D) {

        final FontMetrics fm = getFontMetrics(graphics2D.getFont());
        int width = fm.stringWidth(icon);
        int height = fm.getAscent();

        graphics2D.drawString(icon, x - width / 2, y + height / 2);
    }

    /**
     * Draws forecast by dayily
     *
     * @param x left most position
     * @param graphics2D
     */
    private void drawDaily(int x, final Graphics2D graphics2D) {

        int div = getWidth() / dailyCodes.length;

        for (int i = 0; i < dailyCodes.length; i++) {

            int ix = x + (div / 2 + div * i);

            graphics2D.setFont(Icons.Weather.font.deriveFont(DIMENTION * .2f));
            drawIconAt(dailyCodes[i], ix, (int)(DIMENTION * .2f), graphics2D);

            graphics2D.setFont(TextFonts.light.deriveFont(DIMENTION * .15f));
            final String temp = Math.round(dailyTemps[i]) + "°";
            centerString(temp, ix, (int)(DIMENTION * .55f), graphics2D);

            graphics2D.setFont(TextFonts.light.deriveFont(DIMENTION * .15f));

            String day = new SimpleDateFormat("EEE").format(dailyTimes[i]);

            if (i == 0) {
                day = "Today";
            }
            centerString(day, ix, (int)(DIMENTION * .7f), graphics2D);
        }
    }

    /**
     * Draws forecast by Hourly
     * @param x left most position
     * @param g2d
     */
    private void drawHourly(int x, Graphics2D g2d) {

        int div = getWidth() / 5;

        for (int i = 0; i < 5; i++) {
            int ix = x + (div / 2 + div * i);

            g2d.setFont(Icons.Weather.font.deriveFont(DIMENTION * .2f));
            drawIconAt(hourlyCodes[i], ix, (int)(DIMENTION * .2f), g2d);

            g2d.setFont(TextFonts.light.deriveFont(DIMENTION * .15f));
            String temp = Math.round(hourlyTemps[i]) + "°";
            centerString(temp, ix, (int)(DIMENTION * .55f), g2d);

            g2d.setFont(TextFonts.light.deriveFont(DIMENTION * .15f));
            String time = new SimpleDateFormat("h aa").format(hourlyTimes[i]).toLowerCase();
            centerString(time, ix, (int)(DIMENTION * .7f), g2d);
        }
    }

    /**
     * The three methods below are used by the box layout
     * to force the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, (int)(DIMENTION));
    }

    /**
     * Update module information
     *
     * @param moduleManager that calls this method
     */
    @Override
    public void doUpdate(AbstractModuleManager moduleManager) {

        if (!(moduleManager instanceof WeatherModuleManager)) {
            throw new IllegalStateException("Weather forecast not allowed");
        }

        WeatherModuleManager wm = (WeatherModuleManager)moduleManager;

        dailyCodes = wm.getDailyCodes();
        dailyTemps = wm.getDailyTemps();
        dailyTimes = wm.getDailyTimes();

        hourlyCodes = wm.getHourlyCodes();
        hourlyTemps = wm.getHourlyTemps();
        hourlyTimes = wm.getHourlyTimes();

        this.repaint();
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (onDaily) {
            slideLeft();
        } else {
            slideRight();
        }

    }

    /**
     * Slide left
     */
    private void slideLeft() {

        new AnimationManager(0, -getWidth() - gap)
                .withDuration(1000)
                .withInterpolator(new DecelerateInterpolator())
                .addUpdateListener( value -> {currX = (int)value; repaint();})
                .addEndListener( () -> {onDaily = false;})
                .start();
    }

    /**
     * Slide animation with right direction
     */
    private void slideRight() {

        new AnimationManager(-getWidth() - gap, 0)
                .withDuration(1000)
                .withInterpolator(new DecelerateInterpolator())
                .addUpdateListener(value -> {currX = (int)value; repaint();})
                .addEndListener(() -> onDaily = true)
                .start();
    }


}
