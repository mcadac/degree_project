package edu.mirror.gui.controller;

/**
 * Created by McadaC on 5/20/18.
 */
public enum PersonGender {

    /** Man */
    MAN("Hola Amigo!, ¿como estas?"),

    /** Woman */
    WOMAN("Hola Amiga, ¿Como estas?");

    /** Greeting */
    private String greeting;

    /** Constructor  */
    private PersonGender (String greeting){

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
        return greeting;
    }


}
