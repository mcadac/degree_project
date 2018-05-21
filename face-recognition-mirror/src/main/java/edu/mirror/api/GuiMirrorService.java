package edu.mirror.api;

/**
 * Created by McadaC on 5/20/18.
 */
public interface GuiMirrorService {

    /**
     * Notify person recognized
     *
     * @param genderRecognized
     * @return string message response
     */
    public String notifyPersonRecognized(String genderRecognized);

}
