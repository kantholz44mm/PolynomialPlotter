import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Frame extends JFrame {
    Frame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1400,900); //Sets the Size in case the Window is not maximized
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(true);        //Frame is resizable
        this.setTitle("Function Plotter");
        ImageIcon logo = new ImageIcon(".\\FunctionPlotterLogo.png"); //"converts" the .png into an ImageIcon
        this.setIconImage(logo.getImage()); //Sets the ImageIcon as the Icon for the Frame

        ControlPanel controlPanel = new ControlPanel(); //Initialisation of the ControlPanel
        GraphPanel graphPanel = new GraphPanel();       //Initialisation of the GraphPanel

        GridBagLayout frameLayout = new GridBagLayout();
        setLayout(frameLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);   //Distance to Grid-"Lines" (that are not visible)

        gbc.gridx = 0;
        gbc.gridy = 0;  //Coordinates from where the Object is set
        gbc.weightx = 1;    //Weighting of the available space on the X-Axis
        gbc.weighty = 1;    //Weighting of the available space on the Y-Axis
        frameLayout.setConstraints(controlPanel, gbc);
        this.add(controlPanel);
        controlPanel.addChangeListener(new TabChangeListener());


        gbc.gridx = 1;
        gbc.gridy = 0;  //Coordinates from where the Object is set
        gbc.weightx = 10;      //Weighting of the X-Axis is 1:10 (ControlPanel:GraphPanel)
        // TODO: Find optimal weighting between the two panels (consult Team)
        gbc.weighty = 1;        //Weighting of the Y-Axis is 1:1
        frameLayout.setConstraints(graphPanel, gbc);
        this.add(graphPanel);

        this.setVisible(true);
    }

    private class TabChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            //TODO: Clear Graph Panel
        }
    }

}
