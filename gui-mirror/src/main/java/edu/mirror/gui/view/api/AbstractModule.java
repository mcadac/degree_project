package edu.mirror.gui.view.api;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;

import javax.swing.*;
import java.awt.*;

/**
 * Created by McadaC on 5/15/18.
 */
public abstract class AbstractModule extends JPanel implements IUpdateStrategy {

    /**
     * Default constructor
     */
    public AbstractModule() {
        this.setBackground(Color.BLACK);
    }

    /**
     * Called when the view is initialized
     */
    protected void onInitialize() {
        //Default behavior
    }

    /**
     * The three methods below are used by the box layout
     * to force the preferred size
     */
    @Override
    public abstract Dimension getPreferredSize();

    /**
     *
     * @return
     */
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    /**
     *
     * @return
     */
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    /**
     * Returns a Graphics2D with anti alias enabled
     * and the color set to white for convenience
     */
    protected Graphics2D getGraphics2D(final Graphics graphics) {

        final Graphics2D graphics2D = (Graphics2D) graphics;
        final RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        graphics2D.setRenderingHints(rh);
        graphics2D.setColor(Color.WHITE);

        return graphics2D;
    }

    /**
     * Draws string centered on the x axis
     *
     * @param str String to draw
     * @param x center x position
     * @param y y position of base of text
     * @param g2d graphics(will use it's font size)
     */
    protected void centerString(String str, int x, int y, Graphics2D g2d) {

        int width = getFontMetrics(g2d.getFont()).stringWidth(str);
        g2d.drawString(str, x - width / 2, y);
    }



    /**
     * Draws the headline attached to the left
     *
     * @param line headline
     * @param x position of line where 0 shows all and getWidth() hides all
     * @param y position of the base of the text
     * @param g2d graphics (will use it's font size
     */
    protected void drawLine(String line, String icon, int x, int y, float size, Graphics2D g2d) {

        final int iconW = Icons.Material.iconWidth(icon, size * .6f, g2d);
        final int hgap = iconW / 4;

        Icons.Material.draw(icon,
                getWidth() - hgap + x,
                (int)(y + size * .08f),
                size * .6f, g2d);

        g2d.setFont(TextFonts.light.deriveFont(size * .4f));
        final int lineW = g2d.getFontMetrics().stringWidth(line);

        g2d.drawString(line, getWidth() - iconW - lineW - hgap * 2 + x, y);
    }



}
