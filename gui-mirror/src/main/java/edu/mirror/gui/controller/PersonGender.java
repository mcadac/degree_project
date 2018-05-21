package edu.mirror.gui.controller;

/**
 * Created by McadaC on 5/20/18.
 */
public enum PersonGender {

    MAN,

    WOMAN;

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

}
