package GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphPlotterFrame extends JFrame {
    public GraphPlotterFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1400,900);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(true);
        this.setTitle("Function Plotter");
        ImageIcon logo = new ImageIcon(".\\FunctionPlotterLogo.png");
        this.setIconImage(logo.getImage());

        GraphPanel graphPanel = new GraphPanel();
        ControlPanel controlPanel = new ControlPanel(graphPanel);
        controlPanel.addChangeListener(new TabChangeListener());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, controlPanel, graphPanel);
        splitPane.setOneTouchExpandable(true); // Adds buttons to collapse/expand the panels
        splitPane.setResizeWeight(0.05); // How the space is distributed between the two components. 0.5 means 50%-50%
        splitPane.setDividerSize(20); // Sets the size of the divider. Default is 10.
        this.add(splitPane);

        this.setVisible(true);
    }

    private static class TabChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            //TODO: Clear Graph Panel
        }
    }

}
