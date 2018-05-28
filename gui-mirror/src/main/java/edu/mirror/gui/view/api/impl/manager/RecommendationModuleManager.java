package edu.mirror.gui.view.api.impl.manager;

import edu.mirror.gui.client.api.RecommendationsService;
import edu.mirror.gui.config.Constant;
import edu.mirror.gui.controller.PersonGender;
import edu.mirror.gui.view.api.AbstractModuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by McadaC on 5/26/18.
 */
@Component
public class RecommendationModuleManager extends AbstractModuleManager {

    /** interval change*/
    public static final int INTERVAL_CHANGE = 8000;

    /** Default message*/
    private static final  String[] DEFAULT_MESSAGE = {};

    /** Recommendations service */
    @Autowired
    private RecommendationsService recommendationService;

    /** Messages */
    private String[] message;

    /** Current temp */
    private float currentTemp = 0.0f;

    /** Person gender */
    private PersonGender personGender;

    /** requiere message change flag */
    private boolean requireChange = false;

    /**
     * Constructor
     */
    public RecommendationModuleManager(){
        super(1);
        this.message = DEFAULT_MESSAGE;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected int getInterval() {
        return INTERVAL_CHANGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onUpdate() {

        message = changeRecommendation();

    }

    /**
     * Setter for property 'currentTemp'.
     *
     * @param currentTemp Value to set for property 'currentTemp'.
     */
    public void setCurrentTemp(float currentTemp) {

        this.currentTemp = currentTemp;

    }

    /**
     * Setter for property 'personGender'.
     *
     * @param personGender Value to set for property 'personGender'.
     */
    public void setPersonGender(final PersonGender personGender) {

        this.requireChange = true;
        this.personGender = personGender;
    }

    /**
     * Changes recommendation
     */
    public String[] changeRecommendation(){

        String[] newMessage = message;

        if( currentTemp > 0 && requireChange){

            newMessage = new String[]{recommendationService.getRecommendationByTemp(currentTemp)};
            this.requireChange = false;

        }

        message = DEFAULT_MESSAGE;
        return newMessage;

    }
}
