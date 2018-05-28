package edu.mirror.recommendationsmirror.temperature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by McadaC on 5/21/18.
 */
public class TemperatureRank {

    /** Empty string */
    private static final String EMPTY = "";

    /**
     * Temperature messages
     */
    private static final Map<Float, String[]> TEMP_MESSAGE;
    static{
        TEMP_MESSAGE = new HashMap<>();

        TEMP_MESSAGE.put(10.0f, new String[]{"Te sugiero ropa abrigada",
                "Deberías llevar sombrilla",
                "Te sugiero usar guantes",
                "Te sugiero usar buena chaqueta",
                "Hay alta probabilidad de lluvia"
        });

        TEMP_MESSAGE.put(15.0f, new String[]{"Hoy puedes usar ropa abrigada", "Es una buena opción la sombrilla",
                "Hay posibilidad de lluvia",
                "Te sugiero usar chaqueta",
                "Te sugiero usar bufanda"
        });

        TEMP_MESSAGE.put(20.0f, new String[]{"Hoy puedes usar ropa poco abrigada",
                "Probabilidad baja de lluvia",
                "Deberias usar un abrigo ligero",
                "No esta de mas llevar sombrilla",
        });

        TEMP_MESSAGE.put(25.0f, new String[]{"Ropa poco abrigada, probabilidad muy baja de lluvia"});
        TEMP_MESSAGE.put(30.0f, new String[]{"Ropa ligera, hoy no llovera"});
        TEMP_MESSAGE.put(100.0f, new String[]{"Hoy puedes usar ropa muy ligera!"});
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

                final String[] messages = TEMP_MESSAGE.get(temperature);;

                final Random random = new Random();
                int low = 0;
                int high = messages.length;
                int result = random.nextInt(high - low) + low;

                return messages[result];
            }
        }

        return EMPTY;
    }


}
