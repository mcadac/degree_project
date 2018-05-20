package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.WeatherModuleManager;

import java.awt.*;
import static edu.mirror.gui.config.Constant.EMPTY;

/**
 * Created by McadaC on 5/19/18.
 */
public class WeatherModule extends AbstractModule {

    /** Temperature icon */
    private static final String TEMPERATURE_ICON = "°";

    /** Dimention module */
    private float DIMENTION = 50f;

    /** Code */
    private String code = EMPTY;

    /** Temperature */
    private int temperature;


    /**
     * Paints each of the components in this container.
     *
     * @param g the graphics context.
     * @see Component#paint
     * @see Component#paintAll
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        final Graphics2D graphics2D = getGraphics2D(g);
        graphics2D.setFont(Icons.Weather.font.deriveFont(DIMENTION * .6f));
        drawIconAt(this.code, (int)(DIMENTION * .5f), (int) (DIMENTION * .6f), graphics2D);

        graphics2D.setFont(TextFonts.regular.deriveFont(DIMENTION + 20f));

        final FontMetrics fm = getFontMetrics(graphics2D.getFont());
        int width = fm.stringWidth(this.temperature + TEMPERATURE_ICON);

        graphics2D.drawString(temperature + TEMPERATURE_ICON, getWidth() - DIMENTION - width, DIMENTION);
    }

    /**
     * Draw temperature icon
     *
     * @param icon
     * @param x
     * @param y
     * @param g2d
     */
    private void drawIconAt(String icon, int x, int y, Graphics2D g2d) {

        final FontMetrics fontMetrics = getFontMetrics(g2d.getFont());
        int width = fontMetrics.stringWidth(icon);
        int height = fontMetrics.getAscent();

        g2d.drawString(icon, x - width / 2, y + height / 2);
    }

    /**
     * The three methods below are used by the box layout
     * to force the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int)(DIMENTION * 4f), (int)(DIMENTION * 1.1f));
    }

    /**
     * Methos to update the module
     *
     * @param weatherModuleManager
     */
    @Override
    public void doUpdate(AbstractModuleManager weatherModuleManager) {

        if (!(weatherModuleManager instanceof WeatherModuleManager)) {
            throw new IllegalStateException("Weather module not allowed");
        }

        final WeatherModuleManager wm = (WeatherModuleManager)weatherModuleManager;

        this.code = wm.getCurrentCode();
        this.temperature = Math.round(wm.getCurrentTemp());

        this.repaint();
    }


}
