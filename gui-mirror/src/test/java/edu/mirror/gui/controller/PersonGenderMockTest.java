package edu.mirror.gui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by McadaC on 5/26/18.
 */
public class PersonGenderMockTest {

    /** Logger */
    private static final Logger LOG = LoggerFactory.getLogger(PersonGenderMockTest.class);

    /**
     * Validates grettings
     */
    //@Test(invocationCount = 100)
    public void validatesGretting_woman(){

        PersonGender personGender = PersonGender.valueOfString("MAN");
        assert personGender != null;
        LOG.info("Gretting: {}", personGender.getGreeting());


    }

    /**
     * Validates grettings
     */
    //@Test(invocationCount = 100)
    public void validatesGretting_man(){

        PersonGender personGender = PersonGender.valueOfString("WOMAN");
        assert personGender != null;
        LOG.info("Gretting: {}", personGender.getGreeting());


    }

}
