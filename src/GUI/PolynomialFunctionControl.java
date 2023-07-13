package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PolynomialFunctionControl extends JPanel {
    private final GraphPanel graphPanel;
    private final JPanel functionInputPanel = new JPanel(); //Panel that holds the FunctionInput (for Layout purposes)
    private final List<FunctionControlPanel> functionControlPanelList = new ArrayList<>();

    public PolynomialFunctionControl(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
        //Layout of the GUI.PolynomialFunctionControl Panel which holds the FunctionInputPosition (functionInput) and the HelpPanel
        BorderLayout PolyFunctionControlLayout = new BorderLayout(5, 0);
        this.setLayout(PolyFunctionControlLayout);

        //GridLayout, in which the Instances of the GUI.FunctionControlPanel are stacked
        GridLayout functionInputPositionLayout = new GridLayout(0, 1, 10, 4);
        functionInputPanel.setLayout(functionInputPositionLayout);
        addNewFunctionControlPanel();
        this.add(functionInputPanel, BorderLayout.PAGE_START);
        this.add(new PolynomialHelpPanel(), BorderLayout.PAGE_END);
    }

    private void addNewFunctionControlPanel() {
        if(functionControlPanelList.size() < 5) {
            FunctionControlPanel functionControlPanel = new FunctionControlPanel(functionControlPanelList, functionInputPanel, this, this.graphPanel);
            functionInputPanel.add(functionControlPanel);
            functionControlPanelList.add(functionControlPanel);
            revalidate();
            repaint();
        }
    }
    private class PolynomialHelpPanel extends JPanel {
        private PolynomialHelpPanel() {

            this.setPreferredSize(new Dimension(PolynomialFunctionControl.this.getWidth(), 60));
            GridLayout helpLabelLayout = new GridLayout(2, 2);
            setLayout(helpLabelLayout);
            createAddButton();
            createResetButton();
            createHelpButton();
            createScreenshotButton();
        }

        public class HelpActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HelpWindow();
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

        public class AddActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewFunctionControlPanel();
            }
        }

        public void createHelpButton() {
            JButton help = new JButton("Help");
            help.setFocusable(false);
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

        public void createAddButton() {
            JButton add = new JButton("Add");
            add.setFocusable(false);
            add.addActionListener(new AddActionListener());
            add(add);
        }
    }
}
