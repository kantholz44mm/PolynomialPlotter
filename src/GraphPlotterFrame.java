import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GraphPlotterFrame extends JFrame {
    public GraphPlotterFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1400,900); //Sets the Size in case the Window is not maximized
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(true);
        this.setTitle("Function Plotter");
        ImageIcon logo = new ImageIcon(".\\FunctionPlotterLogo.png"); //"converts" the .png into an ImageIcon
        this.setIconImage(logo.getImage());

        ControlPanel controlPanel = new ControlPanel();
        GraphPanel graphPanel = new GraphPanel();

        GridBagLayout frameLayout = new GridBagLayout();
        setLayout(frameLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0,0,0,0);   //Distance to Grid-"Lines" (that are not visible)

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        frameLayout.setConstraints(controlPanel, gbc);
        this.add(controlPanel);
        controlPanel.addChangeListener(new TabChangeListener());


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 10;
        // TODO: Find optimal weighting between the two panels, current weighting is 1:10 (consult Team)
        gbc.weighty = 1;
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
