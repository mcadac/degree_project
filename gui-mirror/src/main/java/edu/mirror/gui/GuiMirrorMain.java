package edu.mirror.gui;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.List;

/**
 * Class to start application
 *
 * @author dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@SpringBootApplication
public class GuiMirrorMain {

    /** Mirror modules */
    private static List<AbstractModule> modules;

    /** Mirror module managers*/
    private static List<AbstractModuleManager> managers;


    /**
     * Main application
     *
     * @param args
     * @throws IOException
     * @throws FontFormatException
     */
    public static void main(String... args) throws IOException, FontFormatException {

        SpringApplication.run(GuiMirrorMain.class,args);

        TextFonts.load();
        Icons.loadAlls();
    }
}
