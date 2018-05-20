package edu.mirror.gui.view.api.impl.module;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.animation.AnimationManager;
import edu.mirror.gui.view.animation.interpolators.AccelerateInterpolator;
import edu.mirror.gui.view.animation.interpolators.OvershootInterpolator;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.NewsModuleManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by McadaC on 5/20/18.
 */
public class NewsModule extends AbstractModule implements ActionListener {

    /** Dimention */
    private float DIMEN = 40f;

    /** Mac Line count */
    private int LINE_COUNT = 2;

    /** News head lines*/
    private String[] headlines;

    /** News positions */
    private int[] positions;

    /** Showing */
    private boolean showing = false;

    /** Scroll timer*/
    private Timer scrollTimer;

    /** Animarion scroll */
    private float[] scrollPer; // to animate scrolling

    /** Going left */
    private boolean goingLeft = true; // for scrolling direction

    /** Current news */
    private int currentNews = 0; // current news shown

    /**
     * Called when the view is initialized
     */
    @Override
    public void onInitialize() {

        new Timer(3000 * LINE_COUNT, this).start();
        positions = new int[LINE_COUNT];
        scrollPer = new float[LINE_COUNT];
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

        if (headlines == null) {
            return;
        }

        final Graphics2D graphics2D = getGraphics2D(graphics);

        for (int i = currentNews; i < headlines.length &&
                i < currentNews + LINE_COUNT; i++) {

            int pos = i - currentNews;
            if (pos >= 0) {
                drawLine(headlines[i], i - currentNews, positions[pos], graphics2D);
            }
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
        int iconW = Icons.Material.iconWidth("newspaper", DIMEN * .6f, g2d);
        int hgap = iconW / 4;

        Icons.Material.draw("newspaper",
                getWidth() - hgap + x, (int)(y + DIMEN * .08f),
                DIMEN * .6f, g2d);

        g2d.setFont(TextFonts.regular.deriveFont(DIMEN * .6f));

        FontMetrics fm = getFontMetrics(g2d.getFont());
        int lineW = fm.stringWidth(line);
        int textWidth = getWidth() - hgap * 2 - iconW;

        int diff = lineW - textWidth;
        if (diff < 0)
            diff = 0;

        g2d.setClip(x, (int)(y - DIMEN + fm.getDescent()), textWidth, (int)DIMEN);
        g2d.drawString(line, textWidth - lineW + x
                + (diff * scrollPer[level]), y);
        g2d.setClip(null);
    }

    /**
     * Used by Box Layout to Force Preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1500, (int) (LINE_COUNT * DIMEN * 1.1f));
    }

    /**
     * Will be called by the model every time it updates
     *
     * @param  abstractModuleManager that calls this method
     */
    @Override
    public void doUpdate(AbstractModuleManager abstractModuleManager) {

        if (!(abstractModuleManager instanceof NewsModuleManager)) {
            throw new IllegalStateException("News module cannot be created");
        }

        NewsModuleManager newsModuleManager = (NewsModuleManager)abstractModuleManager;
        headlines = newsModuleManager.getHeadlines();
        animateEntry();
    }

    /**
     * Invoked when an action occurs.
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

                    currentNews += LINE_COUNT;

                    if (currentNews >= headlines.length) {
                        currentNews = 0;
                    }

                    animateEntry();
                });
            }

            anim.start();
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
     *
     * @return
     */
    @Override
    public JPanel createJpanel() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createHorizontalGlue());
        return panel;
    }
}
