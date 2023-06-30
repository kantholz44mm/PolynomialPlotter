import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametricFunctionControl extends JPanel {


    ParametricFunctionControl() {

        BorderLayout ParaFunctionControlLayout = new BorderLayout(0, 0);
        this.setLayout(ParaFunctionControlLayout);

        this.add(new ParametricFunctionControlPanel(), BorderLayout.CENTER);
        this.add(new ParametricHelpPanel(), BorderLayout.PAGE_END);
    }

    private class ParametricHelpPanel extends JPanel {

        GridLayout ParaHelpLabelLayout = new GridLayout(1, 2);

        public class HelpActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Display Helpwindow
            }
        }

        public class ScreenshotActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Implement Screenshot function
            }
        }

        public class ResetActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Connect to Reset functionality
            }
        }

        ParametricHelpPanel() {
            setLayout(ParaHelpLabelLayout);
            setPreferredSize(new Dimension(ParametricFunctionControl.this.getWidth(), 30));

            createResetButton();
            createHelpButton();
            createScreenshotButton();
        }

        public void createHelpButton() {
            JButton help = new JButton("Help");
            help.setFocusable(false);     //Removes the dotted line around the Button when it is clicked
            help.addActionListener(new HelpActionListener());
            add(help);
        }

        public void createScreenshotButton() {
            JButton screenshot = new JButton("Screenshot");
            screenshot.setFocusable(false);     //Removes the dotted line around the Button when it is clicked
            screenshot.addActionListener(new ScreenshotActionListener());
            add(screenshot);
        }

        public void createResetButton() {
            JButton reset = new JButton("Reset");
            reset.setFocusable(false);     //Removes the dotted line around the Button when it is clicked
            reset.addActionListener(new ResetActionListener());
            add(reset);
        }
    }
}



