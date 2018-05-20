package edu.mirror.gui.view.animation.interpolators;

/**
 * Interface to use Android interpolator
 *
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 */
public interface Interpolator {

  /**
   * Maps the value given a fraction as input
   *
   * @param input current input
   * @return interpolation at the current fraction
   */
  float getInterpolation(float input);
}
