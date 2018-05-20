package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.config.Constant;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.ClockModuleManager;

import javax.swing.*;
import java.awt.*;
import static edu.mirror.gui.config.Constant.EMPTY;
import static edu.mirror.gui.config.Constant.COLON;
import static edu.mirror.gui.config.Constant.SPACE;

/**
 * Created by McadaC on 5/19/18.
 */
public class ClockModule extends AbstractModule{

    /** Clock module dimention */
    private float DIMENTION = 180f;

    /** Hour */
    private String hh = EMPTY;

    /** Minute */
    private String mm = EMPTY;

    /** aaw */
    private String aa = EMPTY;

    /**
     * Paints each of the components in this container.
     *
     * @param graphics the graphics context.
     * @see Component#paint
     * @see Component#paintAll
     */
    @Override
    public void paintComponent(final Graphics graphics) {

        super.paintComponent(graphics);
        final Graphics2D graphics2D = getGraphics2D(graphics);

        final Font hhf = TextFonts.regular.deriveFont(DIMENTION);
        final Font mmf = TextFonts.regular.deriveFont(DIMENTION);
        final Font aaf = TextFonts.regular.deriveFont(DIMENTION / 2f);

        FontMetrics fontMetrics = getFontMetrics(hhf);
        int hhw = fontMetrics.stringWidth(hh);

        fontMetrics = getFontMetrics(mmf);
        int mmw = fontMetrics.stringWidth( COLON + mm);

        fontMetrics = getFontMetrics(aaf);
        int aaw = fontMetrics.stringWidth(SPACE + aa);

        graphics2D.setFont(hhf);
        graphics2D.drawString(hh, this.getWidth() - hhw - mmw - aaw, DIMENTION);

        graphics2D.setFont(mmf);
        graphics2D.drawString(COLON + mm, this.getWidth() - mmw - aaw, DIMENTION);

        graphics2D.setFont(aaf);
        graphics2D.drawString( SPACE + aa, this.getWidth() - aaw, DIMENTION);
    }

    /**
     * Gets preferred size
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, (int) DIMENTION + 5);
    }

    /**
     * Will be called by the model every time it updates
     */
    @Override
    public void doUpdate(AbstractModuleManager clockModuleManager) {

        if (!(clockModuleManager instanceof ClockModuleManager)) {
            throw new IllegalStateException("ClockView not linked to ClockModel");
        }

        ClockModuleManager cm = (ClockModuleManager)clockModuleManager;
        hh = cm.format("h");
        mm = cm.format("mm");
        aa = cm.format("aa");
        this.repaint();
    }

    /**
     * Create Jpanel
     *
     * @return
     */
    @Override
    public JPanel createJpanel(){

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(Box.createHorizontalGlue());
        return panel;
    };

}
