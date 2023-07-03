package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FunctionControlPanel extends JPanel {

    private final GridBagLayout InputLayout = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private int countOfInstances;
    private final List<FunctionControlPanel> functionControlPanelList;
    private final JPanel functionInputPosition;
    private final PolynomialFunctionControl polynomialFunctionControl;

    JTextField functionField = new JTextField(20);

    public FunctionControlPanel(int count, List<FunctionControlPanel> functionControlPanelList, JPanel functionInputPosition, PolynomialFunctionControl polynomialFunctionControl) {
        this.functionControlPanelList = functionControlPanelList;
        this.functionInputPosition = functionInputPosition;
        this.countOfInstances = count;
        this.polynomialFunctionControl = polynomialFunctionControl;


        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 1, 4, 1); //Sets the distance between the Objects
        setLayout(InputLayout);

        createFunctionField();
        createColorPicker();
        createCalculateButton();
        createDeriveButton();
        createDeleteButton();
    }

    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: Connect to the function calculator
        }
    }

    private class DeriveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: Connect to the function deriver
        }
    }

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removeFunctionControlPanel(FunctionControlPanel.this);
            //TODO: Connect to the function deleter to delete function on the GUI.GraphPanel
        }
    }

    private void removeFunctionControlPanel(FunctionControlPanel functionControlPanel) {

        if (functionControlPanelList.size() > 1) {
            functionInputPosition.remove(functionControlPanel);
            functionControlPanelList.remove(functionControlPanel);
            polynomialFunctionControl.revalidate();
            polynomialFunctionControl.repaint();
            PolynomialFunctionControl.decrementCount();
            countOfInstances--;
        }
    }

        public void createFunctionField() {

            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.ipady = 10; //Height of the TextField
            gbc.ipadx = 10; //Width of the TextField
            InputLayout.setConstraints(functionField, gbc);
            add(functionField);
        }
        public void createColorPicker() {
            ComboBoxColorPicker colorPicker = new ComboBoxColorPicker();

            gbc.weightx = 1;
            gbc.gridx = 2;
            gbc.gridy = 0;
            InputLayout.setConstraints(colorPicker, gbc);
            this.add(colorPicker);
        }

        public void createCalculateButton() {
            JButton calculate = new JButton("Calculate");
            calculate.setFocusable(false);  //Removes the dotted line around the Button when it is clicked
            calculate.addActionListener(new CalculateActionListener());

            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            InputLayout.setConstraints(calculate, gbc);
            this.add(calculate);
        }

        public void createDeriveButton() {
            JButton derive = new JButton("Derive");
            derive.setFocusable(false);
            derive.addActionListener(new DeriveActionListener());

            gbc.gridx = 1;
            gbc.gridy = 1;
            InputLayout.setConstraints(derive, gbc);
            this.add(derive);
        }

        public void createDeleteButton() {
            JButton delete = new JButton("Delete");
            delete.setFocusable(false);
            delete.addActionListener(new DeleteActionListener());

            gbc.gridx = 2;
            gbc.gridy = 1;
            InputLayout.setConstraints(delete, gbc);
            this.add(delete);
        }
}
