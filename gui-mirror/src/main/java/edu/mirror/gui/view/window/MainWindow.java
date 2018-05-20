package edu.mirror.gui.view.window;

import edu.mirror.gui.view.api.AbstractModule;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by McadaC on 5/19/18.
 */
public class MainWindow extends JFrame {


    /**
     * Create main window
     *
     * @param widgets
     */
    public void create(final List<AbstractModule> widgets) {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);


        JPanel background = backgroundPane();

        for (AbstractModule module : widgets) {

            JPanel moduleJpnael = module.createJpanel();
            moduleJpnael.add(module);
            background.add(moduleJpnael);
            module.onInitialize();

        }

        add(background);
        pack();
        this.setVisible(true);
    }


    /**
     * Creates background panel
     *
     * @return
     */
    private JPanel backgroundPane() {

        JPanel background = new JPanel();
        background.setBackground(Color.BLUE);
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        //background.setLayout(new FlowLayout(FlowLayout.LEFT));
        int pad = 0;
        background.setBorder(BorderFactory.createEmptyBorder(pad, pad, pad, pad));
        return background;
    }

}

