package GUI;

import MathExpression.ParametricExpression;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametricFunctionControl extends JPanel {
    GraphPanel graphPanel;

    public ParametricFunctionControl(GraphPanel graphPanel) {

        BorderLayout ParaFunctionControlLayout = new BorderLayout(0, 0);
        this.setLayout(ParaFunctionControlLayout);
        this.graphPanel = graphPanel;
        this.add(new ParametricFunctionControlPanel(graphPanel), BorderLayout.CENTER);
        this.add(new ParametricHelpPanel(), BorderLayout.PAGE_END);
    }

    private class ParametricHelpPanel extends JPanel {

        public class HelpActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Display Helpwindow
            }
        }

        public class ScreenshotActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.screenshotToFile();
            }
        }

        public class ResetActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphPanel.cameraReset();
            }
        }
        public class ValueTableActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                ParametricExpression expression = graphPanel.getParametricExpression();
                if (expression != null) {
                    Valuetable valuetable = new Valuetable(expression);
                }

            }
        }



        ParametricHelpPanel() {
            GridLayout paraHelpLabelLayout = new GridLayout(1, 2);
            setLayout(paraHelpLabelLayout);
            setPreferredSize(new Dimension(ParametricFunctionControl.this.getWidth(), 30));

            createResetButton();
            createHelpButton();
            createScreenshotButton();
            createTableButton();

        }

        public void createHelpButton() {
            JButton help = new JButton("Help");
            help.setFocusable(false);     //Removes the dotted line around the Button when it is clicked
            help.addActionListener(new HelpActionListener());
            add(help);
        }

        public void createScreenshotButton() {
            JButton screenshot = new JButton("Screenshot");
            screenshot.setFocusable(false);
            screenshot.addActionListener(new ScreenshotActionListener());
            add(screenshot);
        }

        public void createResetButton() {
            JButton reset = new JButton("Reset");
            reset.setFocusable(false);
            reset.addActionListener(new ResetActionListener());
            add(reset);
        }
        public void createTableButton() {
            JButton table = new JButton("Table");
            table.setFocusable(false);
            table.addActionListener(new ValueTableActionListener());
            add(table);
        }

    }
}



