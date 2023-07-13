package GUI;

import javax.swing.*;
import MathExpression.ParametricExpression;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametricFunctionControlPanel extends JPanel {

    private final GridBagLayout ParametricInputPosition = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JTextField functionFieldX = new JTextField();
    private final JTextField functionFieldY = new JTextField();
    private final JSpinner rangeStart = new JSpinner(new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
    private final JSpinner rangeEnd = new JSpinner(new SpinnerNumberModel(1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
    private final JSpinner stepSpinner = new JSpinner(new SpinnerNumberModel(300, 100, 10000, 1));
    private final GraphPanel graphPanel;
    protected boolean valueTableIsActive = false;

    public ParametricFunctionControlPanel(GraphPanel graphPanel) {

        this.graphPanel = graphPanel;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 2, 4, 2);
        setLayout(ParametricInputPosition);

        createFunctionFieldX();
        createFunctionFieldY();
        createRangeSetters();
        createDeleteButton();
        createCalculateButton();

    }

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            functionFieldX.setText(null);
            functionFieldY.setText(null);
            graphPanel.setParametricFunction(null);
            valueTableIsActive = false;
        }
    }

    public class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ParametricExpression expression = new ParametricExpression(functionFieldX.getText(), functionFieldY.getText(), (double)rangeStart.getValue(), (double)rangeEnd.getValue(), (int)stepSpinner.getValue());
            graphPanel.setParametricFunction(expression);
            valueTableIsActive = true;
        }
    }

    public  boolean getvalueTableIsActive(){
        return valueTableIsActive;
    }
    public void setgetvalueTableIsActive(boolean valueTableIsActive){
    this.valueTableIsActive = valueTableIsActive;
    }

    public void createFunctionFieldX() {

        //Creates the "X:"
        JTextField X = new JTextField("X:");
        X.setEditable(false);
        X.setHorizontalAlignment(JTextField.RIGHT);
        X.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.ipadx = 5;
        gbc.ipady = 0;
        ParametricInputPosition.setConstraints(X, gbc);
        add(X);

        //Creates the TextField for the actual input
        functionFieldX.setHorizontalAlignment(JTextField.CENTER);
        functionFieldX.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.ipady = 20;

        ParametricInputPosition.setConstraints(functionFieldX, gbc);
        add(functionFieldX);
    }

    public void createFunctionFieldY() {

        //Creates the "Y:"
        JTextField Y = new JTextField("Y:");
        Y.setEditable(false);
        Y.setHorizontalAlignment(JTextField.RIGHT);
        Y.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 5;
        gbc.ipady = 0;
        ParametricInputPosition.setConstraints(Y, gbc);
        add(Y);

        //Creates the TextField for the actual input
        functionFieldY.setHorizontalAlignment(JTextField.CENTER);
        functionFieldY.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        gbc.weightx = 3;
        gbc.ipady = 20;

        ParametricInputPosition.setConstraints(functionFieldY, gbc);
        add(functionFieldY);
    }

    private void createRangeSetters() {

        //Creates the "Range:" label
        JTextField rangeLabel = new JTextField("Range:");
        rangeLabel.setEditable(false);
        rangeLabel.setHorizontalAlignment(JTextField.RIGHT);
        rangeLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 5;
        gbc.ipady = 0;
        ParametricInputPosition.setConstraints(rangeLabel, gbc);
        add(rangeLabel);

        //Creates the first Box to declare the Range
        rangeStart.setPreferredSize(new Dimension(80, 20));
        rangeStart.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 20;
        gbc.weightx = 1;
        ParametricInputPosition.setConstraints(rangeStart, gbc);
        add(rangeStart);

        //Creates the "> = t < =" in the middle
        JTextField rangeParameter = new JTextField("< = t < =");
        rangeParameter.setEditable(false);
        rangeParameter.setHorizontalAlignment(JTextField.CENTER);
        rangeParameter.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 2;
        gbc.gridy = 3;
        ParametricInputPosition.setConstraints(rangeParameter, gbc);
        add(rangeParameter);

        //Creates the second Box to declare the Range
        rangeEnd.setPreferredSize(new Dimension(80, 20));
        rangeEnd.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.ipady = 20;
        ParametricInputPosition.setConstraints(rangeEnd, gbc);
        add(rangeEnd);

        //Creates the "Steps:" label
        JTextField stepsLabel = new JTextField("Steps:");
        stepsLabel.setEditable(false);
        stepsLabel.setHorizontalAlignment(JTextField.RIGHT);
        stepsLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipadx = 5;
        gbc.ipady = 0;
        ParametricInputPosition.setConstraints(stepsLabel, gbc);
        add(stepsLabel);

        //Creates the spinner to set step count
        rangeEnd.setPreferredSize(new Dimension(80, 20));
        rangeEnd.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.ipady = 20;
        ParametricInputPosition.setConstraints(stepSpinner, gbc);
        add(stepSpinner);
    }

    private void createDeleteButton() {
        JButton delete = new JButton("Delete");
        delete.setPreferredSize(new Dimension(80, 20));
        delete.setFocusable(false);
        delete.addActionListener(new DeleteActionListener());
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.ipady = 20;
        ParametricInputPosition.setConstraints(delete, gbc);
        add(delete);
    }


    private void createCalculateButton() {
        JButton calculate = new JButton("Calculate");
        calculate.setPreferredSize(new Dimension(80, 20));
        calculate.setFocusable(false);
        calculate.addActionListener(new CalculateActionListener());
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.ipady = 20;
        ParametricInputPosition.setConstraints(calculate, gbc);
        add(calculate);
    }
}
