package edu.mirror.gui.config;

import java.util.Arrays;

/**
 * Created by McadaC on 5/20/18.
 */
public class Constant {

    /** Empty string */
    public static final String EMPTY = "".intern();

    /** Space string */
    public static final String SPACE = " ".intern();

    /** Colon string */
    public static final String COLON = ":".intern();

    /**
     * Build a string using a {@link StringBuilder}
     * @param args
     * @return
     */
    public static String buildString(String... args){

        final StringBuilder builder = new StringBuilder();
        Arrays.stream(args)
                .forEach( value -> builder.append(value));

        return builder.toString();

    }
}
