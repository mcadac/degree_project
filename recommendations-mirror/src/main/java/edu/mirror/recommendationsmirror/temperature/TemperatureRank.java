package edu.mirror.recommendationsmirror.temperature;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by McadaC on 5/21/18.
 */
public class TemperatureRank {

    /** Empty string */
    private static final String EMPTY = "";

    /**
     * Temperature messages
     */
    private static final Map<Float, String> TEMP_MESSAGE;
    static{
        TEMP_MESSAGE = new HashMap<>();
        TEMP_MESSAGE.put(10.0f, "Ropa abrigada y sombrilla, alta probabilidad de lluvia");
        TEMP_MESSAGE.put(15.0f, "Ropa abrigada y sombrilla, posible lluvia");
        TEMP_MESSAGE.put(20.0f, "Ropa abrigada, probabilidad baja de lluvia");
        TEMP_MESSAGE.put(25.0f, "Ropa poco abrigada, probabilidad muy baja de lluvia");
        TEMP_MESSAGE.put(30.0f, "Rap ligera, hoy no llovera");
        TEMP_MESSAGE.put(100.0f, "Hoy puedes usar ropa muy ligera!");
    }

    /**
     * Gets message depending of temperature
     *
     * @param temp
     * @return
     */
    public static String getMessage(final float temp){

        for(final Float temperature : TEMP_MESSAGE.keySet()){

            if(temp <= temperature){
                return TEMP_MESSAGE.get(temperature);
            }
        }

        return EMPTY;
    }


}
