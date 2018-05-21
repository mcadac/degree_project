package edu.mirror.api;

import feign.Param;
import feign.RequestLine;

/**
 * Created by McadaC on 5/20/18.
 */
public interface GuiMirrorClient {

    /**
     * Send a Get message to notify person recognized
     *
     * @param gender
     * @return
     */
    @RequestLine("GET /v1/notification/personRecognized/{gender}")
    String notifyRecognition(@Param("gender") String gender);
}
