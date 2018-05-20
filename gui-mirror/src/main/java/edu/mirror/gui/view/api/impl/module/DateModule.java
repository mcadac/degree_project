package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.ClockModuleManager;

import java.awt.*;

import static edu.mirror.gui.config.Constant.EMPTY;

/**
 * Created by McadaC on 5/20/18.
 */
public class DateModule extends AbstractModule {

    /** Module dimention*/
    private float DIMENTION = 50;

    /** Date */
    private String date = EMPTY;

    /**
     * Paints each of the components in this container.
     *
     * @param graphics the graphics context.
     * @see Component#paint
     * @see Component#paintAll
     */
    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);
        final Graphics2D graphics2D = getGraphics2D(graphics);

        graphics2D.setFont(TextFonts.regular.deriveFont(DIMENTION));

        final FontMetrics fm = getFontMetrics(graphics2D.getFont());
        int width = fm.stringWidth(this.date);

        graphics2D.drawString(this.date, 0, DIMENTION);
    }

    /**
     * Used by Box Layout to Force Preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(2000, (int) DIMENTION + 10);
    }


    /**
     * Will be called by the model every time it updates
     *
     * @param abstractModuleManager model that calls this method
     */
    @Override
    public void doUpdate(AbstractModuleManager abstractModuleManager) {

        if (!(abstractModuleManager instanceof ClockModuleManager)) {
            throw new IllegalStateException("Date module cannot be created");
        }

        this.date = ((ClockModuleManager)abstractModuleManager).format("EEEE, MMMM d");
        this.repaint();
    }


}
