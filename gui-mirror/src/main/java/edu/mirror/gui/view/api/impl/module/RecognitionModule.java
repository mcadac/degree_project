package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.animation.AnimationManager;
import edu.mirror.gui.view.animation.interpolators.AccelerateInterpolator;
import edu.mirror.gui.view.animation.interpolators.OvershootInterpolator;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.NewsModuleManager;
import edu.mirror.gui.view.api.impl.manager.RecognitionModuleManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by McadaC on 5/20/18.
 */
public class RecognitionModule extends AbstractModule implements ActionListener {

    /** Dimention */
    private float DIMEN = 60f;

    /** New message */
    private String[] newMessages;

    /** Showing */
    private boolean showing = false;

    /** Animarion scroll */
    private float[] scrollPer;

    /** Mac Line count */
    private int LINE_COUNT = 1;

    /** News positions */
    private int[] positions;

    /** Going left */
    private boolean goingLeft = true;

    /** Scroll timer*/
    private Timer scrollTimer;

    /** Current message */
    private int currentMessage = 0;


    /**
     * Initialize module
     */
    @Override
    public void onInitialize() {
        //new Timer(3000 * LINE_COUNT, this).start();
        positions = new int[LINE_COUNT];
        scrollPer = new float[LINE_COUNT];
    }

    /**
     * Do update process
     *
     * @param moduleManager module that calls this method
     */
    @Override
    public void doUpdate(final AbstractModuleManager moduleManager) {

        if (!(moduleManager instanceof RecognitionModuleManager)) {
            throw new IllegalStateException("Recognition module cannot be created");
        }

        final RecognitionModuleManager recognitionModuleManager = (RecognitionModuleManager)moduleManager;
        newMessages = recognitionModuleManager.changeMessage();
        animateEntry();

    }

    /**
     * Gets preferred size
     *
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1200, (int) (DIMEN * 1.1f));
    }

    /**
     * Action performed
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (!showing)
            animateEntry();
        else {
            animateExit();
        }

    }


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

        if (newMessages == null) {
            return;
        }

        final Graphics2D graphics2D = getGraphics2D(graphics);

        for (int i = currentMessage; i < newMessages.length &&
                i < currentMessage + LINE_COUNT; i++) {

            int pos = i - currentMessage;

            if (pos >= 0) {

                drawLine(newMessages[i], i - currentMessage, positions[pos], graphics2D);

            }
        }
    }


    /**
     * Start scroller animation
     */
    private void startScroller() {

        for (int i=0; i<scrollPer.length; i++) {
            scrollPer[i] = 1f;
        }

        this.scrollTimer = new Timer(3000, event -> {
            new AnimationManager(goingLeft ? 1f : 0f, goingLeft ? 0f : 1f)
                    .withDuration(2000)
                    .addUpdateListener(value -> {
                        for (int i=0; i<scrollPer.length; i++) {
                            scrollPer[i] = value;
                        }
                        repaint();
                    })
                    .addEndListener(() -> {
                        goingLeft = !goingLeft;
                    })
                    .start();
        });

        this.scrollTimer.start();
    }


    /**
     * Animation Entry
     */
    private void animateEntry() {

        for (int i=0; i<scrollPer.length; i++) {
            scrollPer[i] = 1f;
        }

        goingLeft = false;
        showing = true;

        for (int i = 0; i < LINE_COUNT; i++) {

            positions[i] = getWidth();
            final int finalI = i;
            AnimationManager anim = new AnimationManager(getWidth(), 0)
                    .withDuration(500)
                    .withDelay(i * 120)
                    .withInterpolator(new OvershootInterpolator(.6f))
                    .addUpdateListener(value -> {positions[finalI] = (int)value; repaint();
                    });

            if (i == LINE_COUNT - 1) {
                anim.addEndListener(() -> {startScroller();});
            }

            anim.start();
        }
    }

    /**
     * Animation exit
     */
    private void animateExit() {

        showing = false;
        for (int i = 0; i < LINE_COUNT; i++) {
            final int finalI = i;
            AnimationManager anim = new AnimationManager(0, getWidth())
                    .withDuration(500)
                    .withDelay(i * 120)
                    .withInterpolator(new AccelerateInterpolator())
                    .addUpdateListener(value -> {positions[finalI] = (int)value; repaint();
                    });

            if (i == LINE_COUNT - 1) {

                anim.addEndListener(() -> {

                    if (scrollTimer != null) {
                        scrollTimer.stop();
                    }

                    currentMessage += LINE_COUNT;

                    if (currentMessage >= newMessages.length) {
                        currentMessage = 0;
                    }

                    animateEntry();
                });
            }

            anim.start();
        }
    }

    /**
     * Draws the headline attached to the left
     *
     * @param line headline
     * @param level int with 0 being the top line
     * @param x position of line where 0 shows all and getWidth() hides all
     */
    private void drawLine(String line, int level, int x, Graphics2D g2d) {

        int y = (int) (DIMEN * (level + 1));

        g2d.setFont(TextFonts.regular.deriveFont(DIMEN));

        FontMetrics fm = getFontMetrics(g2d.getFont());
        int lineW = fm.stringWidth(line);
        int textWidth = getWidth();

        int diff = lineW - textWidth;
        if (diff < 0)
            diff = 0;

        g2d.setClip(x, (int)(y - DIMEN + fm.getDescent()), textWidth, (int)DIMEN);
        g2d.drawString(line, textWidth - lineW + x + (diff * scrollPer[level]), y);
        g2d.setClip(null);
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
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(Box.createHorizontalGlue());
        return panel;
    };

}
