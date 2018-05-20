package edu.mirror.gui.view.animation;

import edu.mirror.gui.view.animation.interpolators.Interpolator;
import edu.mirror.gui.view.animation.interpolators.LinearInterpolator;
import edu.mirror.gui.view.animation.listener.EndListener;
import edu.mirror.gui.view.animation.listener.UpdateListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Animation Manager
 *
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 */
public final class AnimationManager {

    /** Listeners to animate */
    private List<UpdateListener> listeners;

    /** Listener that implement EndListeners interface */
    private List<EndListener> endListeners;

    /** Interpolator (Android impletation)*/
    private Interpolator interpolator;

    /** Duration time */
    private long duration = 1000;//default value

    /** Delayed time */
    private long delay = 0;

    /** */
    private final float startVal;

    /** */
    private final float endVal;

    /**
     * Constructor
     *
     * @param startValue starting value
     * @param endValue ending value to interpolate
     */
    public AnimationManager(float startValue, float endValue) {

        this.startVal = startValue;
        this.endVal = endValue;
        this.listeners = new ArrayList<>();
        this.endListeners = new ArrayList<>();
        this.interpolator = new LinearInterpolator();

    }

    /**
     * Sets duration to animation manager
     *
     * @param duration
     * @return AnimationManager
     */
    public AnimationManager withDuration(long duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets delay to animation manager
     *
     * @param delay in milliseconds
     * @return the animator being built
     */
    public AnimationManager withDelay(long delay) {

        this.delay = delay;
        return this;
    }

    /**
     * Sets an interpolator to animation manager
     *
     * @param interpolator time interpolator
     * @return the animator being built
     */
    public AnimationManager withInterpolator(Interpolator interpolator) {

        this.interpolator = interpolator;
        return this;
    }

    /**
     * Adds an update listener
     *
     * @param listener
     * @return AnimationManager
     */
    public AnimationManager addUpdateListener(final UpdateListener listener) {

        this.listeners.add(listener);
        return this;
    }

    /**
     * Add an End listener
     *
     * @param listener
     * @return AnimationManager
     */
    public AnimationManager addEndListener(final EndListener listener) {

        this.endListeners.add(listener);
        return this;
    }

    /**
     * To start thread incharge of animation
     */
    public void start() {

        new Thread(() -> {

            try {

                Thread.sleep(this.delay);
                final long startTime = System.currentTimeMillis();
                long timeleft = duration - (System.currentTimeMillis() - startTime);

                while (timeleft >= 0) {

                    timeleft = duration - (System.currentTimeMillis() - startTime);

                    final float frac = 1f - ((float)timeleft / (float)duration);
                    final float iFrac = this.interpolator.getInterpolation(frac);


                    final float val = startVal + iFrac * (endVal - startVal);

                    for (UpdateListener l : listeners) {
                        l.onUpdate(val);
                    }

                    Thread.sleep(Math.min(20, Math.max(timeleft, 0)));
                }

                for (EndListener el : endListeners) {
                    el.onEnd();
                }

            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }


}
