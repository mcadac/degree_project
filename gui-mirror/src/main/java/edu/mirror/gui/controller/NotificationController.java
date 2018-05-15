package edu.mirror.gui.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Notification controller to gets information e.g face recognition component
 *
 * @author dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/notification")
public class NotificationController {


    /**
     * Gets gui mirror status
     *
     * @return String message
     */
    @RequestMapping("/status")
    public String status() {
        return "Gui mirror running";
    }
}
