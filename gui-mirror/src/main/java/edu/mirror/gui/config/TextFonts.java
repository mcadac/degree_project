package edu.mirror.gui.config;

import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 * Texts fonts used by mirror interface
 *
 * @author dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public final class TextFonts {

    /** {@link Font} Bold type */
    public static Font bold;

    /** {@link Font} Light type */
    public static Font light;

    /** {@link Font} Regular type */
    public static Font regular;

    /** {@link Font} Thin type */
    public static Font thin;

    /**
     * Loads fonts froms ttf files resources
     *
     * @throws IOException
     * @throws FontFormatException
     */
    public static void load() throws IOException, FontFormatException {

        bold = Font.createFont(Font.TRUETYPE_FONT, TextFonts.class.getResourceAsStream("/fonts/Roboto-Bold.ttf"));
        light = Font.createFont(Font.TRUETYPE_FONT, TextFonts.class.getResourceAsStream("/fonts/Roboto-Light.ttf"));
        regular = Font.createFont(Font.TRUETYPE_FONT, TextFonts.class.getResourceAsStream("/fonts/Roboto-Regular.ttf"));
        thin = Font.createFont(Font.TRUETYPE_FONT, TextFonts.class.getResourceAsStream("/fonts/Roboto-Thin.ttf"));

        LoadLocalGraphicsEnv();

    }


    /**
     * Loads local graphics environment
     */
    private static void LoadLocalGraphicsEnv() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(bold);
        ge.registerFont(light);
        ge.registerFont(regular);
        ge.registerFont(thin);
    }


}
