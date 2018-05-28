package edu.mirror.gui;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.config.TextFonts;
import edu.mirror.gui.view.api.AbstractModule;
import edu.mirror.gui.view.api.AbstractModuleManager;
import edu.mirror.gui.view.api.impl.manager.*;
import edu.mirror.gui.view.api.impl.module.*;
import edu.mirror.gui.view.window.MainWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to start application
 *
 * @author dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@SpringBootApplication
public class GuiMirrorMain extends JFrame implements CommandLineRunner {

    /** Mirror modules */
    private static List<AbstractModule> modules;

    /** Mirror module managers*/
    private static List<AbstractModuleManager> managers;

    /** Recognition module manager*/
    @Autowired
    private RecognitionModuleManager recognitionModuleManager;

    /** Weather module manager */
    @Autowired
    private WeatherModuleManager weatherModuleManager;

    /** Recommendation module manager */
    @Autowired
    private RecommendationModuleManager recommendationModuleManager;



    /**
     * Main application
     *
     * @param args
     * @throws IOException
     * @throws FontFormatException
     */
    public static void main(String... args) throws IOException, FontFormatException {

        SpringApplication.run(GuiMirrorMain.class,args);

    }

    /**
     * Run application
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {

        TextFonts.load();
        Icons.loadAlls();

        modules = new ArrayList<>();
        managers = new ArrayList<>();

        final ClockModuleManager clockModuleManager = new ClockModuleManager(1);
        managers.add(clockModuleManager);

        final NewsModuleManager newsModuleManager = new NewsModuleManager(1);
        managers.add(newsModuleManager);
        managers.add(weatherModuleManager);
        managers.add(recognitionModuleManager);
        managers.add(recommendationModuleManager);

        addModule(new DateModule(), clockModuleManager);
        addModule(new WeatherModule(), weatherModuleManager);
        addModule(new WeatherForecastModule(), weatherModuleManager);
        addModule(new ClockModule(), clockModuleManager);
        addModule(new RecognitionModule(), recognitionModuleManager);
        addModule(new RecommendationModule(), recommendationModuleManager);
        addModule(new NewsModule(), newsModuleManager);

        new MainWindow().create(modules);
        for (AbstractModuleManager m : managers) {
            m.initialize();
        }

    }

    /**
     * Add a new module
     *
     * @param module
     * @param moduleManager
     */
    private static void addModule(final AbstractModule module, final AbstractModuleManager moduleManager) {

        if (module != null && moduleManager != null) {

            modules.add(module);
            moduleManager.addModule(module);

        }
    }


}
