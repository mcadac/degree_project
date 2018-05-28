package edu.mirror.gui.view.api.impl.manager;

import edu.mirror.gui.view.api.AbstractModuleManager;
import org.springframework.stereotype.Component;

import java.util.List;

import static edu.mirror.gui.config.Constant.EMPTY;

/**
 * Created by McadaC on 5/20/18.
 */
@Component
public class RecognitionModuleManager extends AbstractModuleManager {

    /** interval change*/
    public static final int INTERVAL_CHANGE = 9000;

    /** Default message*/
    private static final  String[] DEFAULT_MESSAGE = {};

    /** Messages */
    private String[] messages;

    /**
     * Default constructor
     */
    public RecognitionModuleManager(){
        super(1);
        messages = new String[] {"Hola, ¿Como Estas?"};
    }

    /**
     * Constructor
     *
     * @param initModules
     */
    public RecognitionModuleManager(int initModules, String[] messages) {
        super(initModules);
        this.messages = messages;
    }

    /**
     * Get inerval of change
     *
     * @return
     */
    @Override
    protected int getInterval() {
        return INTERVAL_CHANGE;
    }

    /**
     * On update process
     */
    @Override
    public void onUpdate() {

        messages = changeMessage();
    }


    /**
     * Notify person recognized
     *
     * @param newMessages
     */
    public void notifyPerson(final String[] newMessages){

        if (newMessages != null){
            messages = newMessages;
        }

    }

    /**
     * Changes recognition message
     *
     * @return
     */
    public String[] changeMessage(){

        String[] newMessage = messages;
        messages = DEFAULT_MESSAGE;

        return newMessage;
    }
}
