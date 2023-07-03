import javax.swing.*;
import java.awt.*;

    public class ControlPanel extends JTabbedPane {

        public ControlPanel() {

            PolynomialFunctionControl polyFC = new PolynomialFunctionControl();
            ParametricFunctionControl paraFC = new ParametricFunctionControl();

            JLabel Tab1 = new JLabel("Polynomial Functions");
            Tab1.setHorizontalAlignment(CENTER);    //Centers the displayed text on the tab

            JLabel Tab2 = new JLabel("Parametric Functions");
            Tab2.setHorizontalAlignment(CENTER);

            this.addTab("Polynomial Functions", null, polyFC, "Open settings menu for Polynomial Functions");
            this.setBackgroundAt(0, Color.white); //Sets TabColor
            this.addTab("Parametric Functions", null, paraFC, "Open loading area for Parametric Functions");
            this.setBackgroundAt(1, Color.white);
        }
    }


