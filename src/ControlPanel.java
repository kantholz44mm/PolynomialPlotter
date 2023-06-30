import javax.swing.*;
import java.awt.*;

    public class ControlPanel extends JTabbedPane {

        ControlPanel() {

            this.setBackground(Color.red);

            PolynomialFunctionControl polyFC = new PolynomialFunctionControl(); //Initialisation of the Pane that holds the controls for the Polynomial Function
            ParametricFunctionControl paraFC = new ParametricFunctionControl(); //Initialisation of the Pane that holds the loading Area for the Parametric Function

            JLabel Tab1 = new JLabel("Polynomial Functions");
            Tab1.setPreferredSize(new Dimension(160,25));   //Sets Size of the Label
            Tab1.setHorizontalAlignment(CENTER);    //Centers the displayed text

            JLabel Tab2 = new JLabel("Parametric Functions");
            Tab2.setHorizontalAlignment(CENTER);
            Tab2.setPreferredSize(new Dimension(160,25));

            this.addTab("Polynomial Functions", null, polyFC, "Open settings menu for Polynomial Functions"); //Adds Tab to the TabbedPane
            this.setBackgroundAt(0, Color.white); //Sets TabColor
            this.addTab("Parametric Functions", null, paraFC, "Open loading area for Parametric Functions"); //Adds Tab to the TabbedPane
            this.setBackgroundAt(1, Color.white); //Sets TabColor
        }


    }


