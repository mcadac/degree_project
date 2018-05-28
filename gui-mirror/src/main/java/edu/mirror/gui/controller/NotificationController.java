package edu.mirror.gui.controller;

import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.RecognitionModuleManager;
import edu.mirror.gui.view.api.impl.manager.RecommendationModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Notification controller to gets information e.g face recognition component
 *
 * @author dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/notification")
public class NotificationController {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    /** Message received */
    private static final String RECEIVED = "RECEIVED";

    /** Manager to notify changes */
    @Autowired
    private RecognitionModuleManager recognitionModuleManager;

    /** Recommendation module manager */
    @Autowired
    private RecommendationModuleManager recommendationModuleManager;


    /**
     * Gets gui mirror status
     *
     * @return String message
     */
    @RequestMapping("/status")
    public String status() {
        return "Gui mirror running";
    }


    /**
     * Notify a person recognized
     *
     * @param gender
     * @return
     */
    @RequestMapping("/personRecognized/{gender}")
    public String notifyPersonRecognition(@PathVariable String gender){

        LOGGER.info("Person recognize : {}", gender);
        PersonGender personGender = PersonGender.valueOfString(gender);
        recognitionModuleManager.notifyPerson( prepareMessage(personGender));
        recommendationModuleManager.setPersonGender(personGender);

        return RECEIVED;
    }


    /**
     * Prepare message
     *
     * @param personGender
     * @return
     */
    private String[] prepareMessage(PersonGender personGender){

        if(personGender == null){
            return null;
        }

        return new String[]{personGender.getGreeting()};
    }


}
