package edu.mirror.gui.view.animation.listener;

/**
 * Created by McadaC on 5/19/18.
 */
public interface UpdateListener {

    /**
     * Called every frame of the animation
     *
     * @param value current value determined by
     *              the start and end values
     */
    void onUpdate(float value);

}
