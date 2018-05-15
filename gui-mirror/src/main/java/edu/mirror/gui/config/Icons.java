package edu.mirror.gui.config;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Icons to user interface
 *
 * @author dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public final class Icons {

    /** Used to read files */
    public static final String COMMA = ",";

    /** String empty*/
    private static final String EMPTY = "";

    /**
     * Loads all icons
     *
     * @throws IOException
     * @throws FontFormatException
     */
    public static void loadAlls() throws IOException, FontFormatException {
        Weather.load();
        Material.load();
    }


    /**
     * Class to load and gets weather icons
     */
    public static class Weather{

        /** Id in icon csv file */
        private static final String ID_ICON_CSV = "wi_owm_";

        /** Weather font */
        public static Font font;

        /** Weather codes */
        private static Map<String, String> codes;

        /** Weather names */
        private static Map<String, String> names;

        /**
         * Loads weather icons
         * @throws IOException
         * @throws FontFormatException
         */
        public static void load() throws IOException, FontFormatException {

            codes = new HashMap<>();
            names = new HashMap<>();

            Scanner scan = new Scanner(Weather.class.getResourceAsStream("/icons/weather_icon_code.csv"));

            while (scan.hasNext()) {

                String[] arr = scan.nextLine().split(COMMA);

                if (arr[0].contains(ID_ICON_CSV)) {
                    codes.put(arr[0], EMPTY + (char) Integer.parseInt(arr[1], 16));
                }
            }

            scan = new Scanner(Weather.class.getResourceAsStream("/icons/weather_icon_name.csv"));

            while (scan.hasNext()) {

                String[] arr = scan.nextLine().split(COMMA);
                names.put(arr[0], arr[1]);
            }

            scan.close();
            font = Font.createFont(Font.TRUETYPE_FONT, Weather.class.getResourceAsStream("/icons/weathericons-regular-webfont.ttf"));

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);

        }

        /**
         * Gets icon
         * @param code {@link String}
         * @return Icon as {@link String}
         */
        public static String getIcon(final String code) {
            return codes.get(ID_ICON_CSV + code);
        }

        /**
         * Gets icon's name
         * @param code {@link String}
         * @return Icon's name
         */
        public static String getName(final String code) {
            return names.get(code.substring(7));
        }


    }


    /**
     * Class to load and gets material icons
     */
    public static class Material{

        /** Material  font */
        public static Font font;

        /** Material icon codes */
        private static Map<String, String> codes;

        /**
         * Loads material icons
         *
         * @throws IOException
         * @throws FontFormatException
         */
        public static void load() throws IOException, FontFormatException {

            codes = new HashMap<>();

            Scanner scan = new Scanner(Material.class.getResourceAsStream("/icons/icons.csv"));
            String[] code = scan.nextLine().split(COMMA);
            String[] name = scan.nextLine().split(COMMA);
            scan.close();

            for (int i = 0; i < code.length; i++) {
                codes.put(name[i], EMPTY + (char) Integer.parseInt(code[i], 16));
            }

            font = Font.createFont(Font.TRUETYPE_FONT, Material.class.getResourceAsStream("/icons/materialdesignicons-webfont.ttf"));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
        }

        /**
         * Gets icon by name
         *
         * @param name
         * @return
         */
        public static String getIcon(final String name) {
            return name == null ? null : codes.get(name);
        }

        /**
         * Draws the given icon
         *
         * @param name name of the icon
         * @param x center x position
         * @param y y position of base of text
         * @param size size of the icon
         * @param g2d icon graphics
         */
        public static void draw(String name, int x, int y, float size, Graphics2D g2d) {

            String ic = getIcon(name);

            if (ic != null) {

                g2d.setFont(font.deriveFont(size));
                int iconW = g2d.getFontMetrics().stringWidth(ic);
                g2d.drawString(ic, x - iconW, y);
            }

        }

        /**
         * Returns the width of the icon if it where to be drawn
         *
         * @param name name of the icon
         * @param size size of the icon
         * @param g2d graphics
         * @return width in pixels or 0 if name is invalid
         */
        public static int iconWidth(final String name, final float size, final Graphics2D g2d) {

            String ic = getIcon(name);

            if (ic == null) {
                return 0;
            }

            FontMetrics fm = g2d.getFontMetrics(font.deriveFont(size));

            return fm.stringWidth(ic);
        }
    }

}
