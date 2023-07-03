package GUI;

import javax.swing.*;
import java.awt.*;

    public class ControlPanel extends JTabbedPane {

        public ControlPanel(GraphPanel graphPanel) {

            PolynomialFunctionControl polyFC = new PolynomialFunctionControl(graphPanel);
            ParametricFunctionControl paraFC = new ParametricFunctionControl(graphPanel);

            JLabel Tab1 = new JLabel("Polynomial Functions");
            Tab1.setHorizontalAlignment(CENTER);

            JLabel Tab2 = new JLabel("Parametric Functions");
            Tab2.setHorizontalAlignment(CENTER);

            this.addTab("Polynomial Functions", null, polyFC, "Open settings menu for Polynomial Functions");
            this.setBackgroundAt(0, Color.white); //Sets TabColor
            this.addTab("Parametric Functions", null, paraFC, "Open parameter settings for Parametric Functions");
            this.setBackgroundAt(1, Color.white);
        }
    }


