import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametricFunctionControlPanel extends JPanel {

    private final GridBagLayout ParametricInputPosition = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JTextField functionFieldX = new JTextField();
    private final JTextField functionFieldY = new JTextField();
    private final JTextField rangeStart = new JTextField();
    private final JTextField rangeEnd = new JTextField();

    public ParametricFunctionControlPanel() {


        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 2, 4, 2);
        setLayout(ParametricInputPosition);

        createFunctionFieldX(); //Creates the TextArea for the "X:" and the TextField for the actual input
        createFunctionFieldY(); //Creates the TextArea for the "Y:" and the TextField for the actual input
        createRangeSetters();
        createDeleteButton();
        createCalculateButton();

        this.setBackground(Color.GRAY);
    }

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            functionFieldX.setText(null);
            functionFieldY.setText(null);
            rangeStart.setText(null);
            rangeEnd.setText(null);
        }
    }

    public class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            functionFieldX.getText();
            functionFieldY.getText();
            rangeStart.getText();
            rangeEnd.getText();
            //Maybe set variables for the functions and the range
            //TODO: Connect to parametric function calculator
        }
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

        //Creates the first Box to declare the Range
        rangeStart.setPreferredSize(new Dimension(80, 20));
        rangeStart.setHorizontalAlignment(JTextField.CENTER);
        rangeStart.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 20;
        gbc.weightx = 1;
        ParametricInputPosition.setConstraints(rangeStart, gbc);
        add(rangeStart);
        AbstractDocument RangeStartDoc = (AbstractDocument) rangeStart.getDocument(); //Sets a DocumentFilter to the TextField to only allow numeric input
        RangeStartDoc.setDocumentFilter(new RangeNumericFilter());

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
        rangeEnd.setHorizontalAlignment(JTextField.CENTER);
        rangeEnd.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.ipady = 20;
        ParametricInputPosition.setConstraints(rangeEnd, gbc);
        add(rangeEnd);
        AbstractDocument RangeEndDoc = (AbstractDocument) rangeEnd.getDocument(); //Sets a DocumentFilter to the TextField to only allow numeric input (exceptions: "." and null)
        RangeEndDoc.setDocumentFilter(new RangeNumericFilter());

    }

    private void createDeleteButton() {
        JButton delete = new JButton("Delete");
        delete.setPreferredSize(new Dimension(80, 20));
        delete.setFocusable(false);
        delete.addActionListener(new DeleteActionListener());
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.ipady = 0;
        ParametricInputPosition.setConstraints(delete, gbc);
        add(delete);
    }


    private void createCalculateButton() {
        JButton calculate = new JButton("Calculate");
        calculate.setPreferredSize(new Dimension(80, 20));
        calculate.setFocusable(false);
        calculate.addActionListener(new CalculateActionListener());
        gbc.gridx = 5;
        gbc.gridy = 3;
        ParametricInputPosition.setConstraints(calculate, gbc);
        add(calculate);
    }

    private static class RangeNumericFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String range, AttributeSet attr) throws BadLocationException {

            if (isNumeric(range))
                super.insertString(fb, offset, range, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String range, AttributeSet attrs) throws BadLocationException {

            if (isNumeric(range))
                super.replace(fb, offset, length, range, attrs);
        }

        public boolean isNumeric(String range) {

            if (range == null || range.equals(".")) return true;

            try {
                double d = Double.parseDouble(range);
            } catch (NumberFormatException nfe) {

                return false;
            }
            return true;
        }

    }
}
