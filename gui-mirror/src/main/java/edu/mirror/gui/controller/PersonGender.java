package edu.mirror.gui.controller;

import java.util.Random;

/**
 * Created by McadaC on 5/20/18.
 */
public enum PersonGender {

    /** WOMAN */
    WOMAN(new String[]{"Hola Amiga!, ¿como estas?",
            "Amiga mia!",
            "Estas hermosas el día de hoy",
            "Señorita, feliz dia!",
            "Hola hermosa",
            "Señorita, luces estupendo!"
    }),

    /** MAN */
    MAN(new String[]{"Hola Amigo, ¿Como estas?",
            "Estimado Amigo",
            "Hola caballero!",
            "Compañero, feliz dia",
            "Buen día amigo",
            "Caballero, simpre tan elegante",
            "Amigo mio!"
    });

    /** Greeting */
    private String[] greeting;

    /** Constructor  */
    private PersonGender (String[] greeting){

        this.greeting = greeting;
    }

    /**
     * Value of String
     *
     * @param gender
     * @return
     */
    public static PersonGender valueOfString(final String gender){

        PersonGender personGender = null;

        try{

            if(gender != null){
                personGender = PersonGender.valueOf(gender.toUpperCase());
            }

        } catch (final Exception exception){

        }

        return personGender;

    }

    /**
     * Gets greeting
     *
     * @return
     */
    public String getGreeting() {

        final Random random = new Random();
        int low = 0;
        int high = this.greeting.length;
        int result = random.nextInt(high - low) + low;

        return greeting[result];
    }


}
