import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PolynomialFunctionControl extends JPanel {

    public static int count = 0;
    private final JPanel functionInputPosition = new JPanel(); //Panel that holds the FunctionInput (for Layout purposes)

    private final List<FunctionControlPanel> functionControlPanelList = new ArrayList<>();

    private void addNewFunctionControlPanel() {
        if(count < 3) {
            FunctionControlPanel functionControlPanel = new FunctionControlPanel(count, functionControlPanelList, functionInputPosition, this);
            functionInputPosition.add(functionControlPanel);
            functionControlPanelList.add(functionControlPanel);
            revalidate();
            repaint();
            count++;
        }
    }
    static void decrementCount() {
        count--;
    }

    public PolynomialFunctionControl() {

        //Layout of the PolynomialFunctionControl Panel which holds the FunctionInputPosition (functionInput) and the HelpPanel
        BorderLayout PolyFunctionControlLayout = new BorderLayout(5, 0);
        this.setLayout(PolyFunctionControlLayout);

        //GridLayout in which the Instances of the FunctionControlPanel are stacked
        GridLayout functionInputPositionLayout = new GridLayout(0, 1, 10, 4);
        functionInputPosition.setLayout(functionInputPositionLayout);
        functionInputPosition.setBackground(Color.black); //Color is used to create lines between the instances of the FunctionControlPanel
        addNewFunctionControlPanel();

        this.add(functionInputPosition, BorderLayout.PAGE_START);
        this.add(new PolynomialHelpPanel(), BorderLayout.PAGE_END);

    }

    private class PolynomialHelpPanel extends JPanel {

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

        public class AddActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewFunctionControlPanel();
            }
        }

        private PolynomialHelpPanel() {

            this.setPreferredSize(new Dimension(PolynomialFunctionControl.this.getWidth(), 60));
            GridLayout helpLabelLayout = new GridLayout(2, 2);
            setLayout(helpLabelLayout);
            createAddButton();
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

        public void createAddButton() {
            JButton add = new JButton("Add");
            add.setFocusable(false);     //Removes the dotted line around the Button when it is clicked
            add.addActionListener(new AddActionListener());
            add(add);
        }
    }
}
