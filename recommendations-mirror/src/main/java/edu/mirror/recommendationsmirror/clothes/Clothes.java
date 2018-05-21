package edu.mirror.recommendationsmirror.clothes;

import java.util.Arrays;
import java.util.List;

/**
 * Clothes messages
 *
 * @author dcespitiam@unipanamericana.edu.co
 */
public class Clothes {

    /**
     * Men men clothes
     */
    private static final List<String> MEN_CLOTHES;
    static{
        MEN_CLOTHES = Arrays.asList(
                "Chaqueta",
                "Suadadera",
                "Corbata"
        );
    }

    /**
     * Private women clothes
     */
    private static final List<String> WOMEN_CLOTHES;
    static{
        WOMEN_CLOTHES = Arrays.asList(
                "Flada",
                "Blusa",
                "Bufanda"
        );
    }

}
