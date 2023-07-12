package GUI;

import Core.ParametricFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FunctionControlPanel extends JPanel {
    private final GraphPanel graphPanel;
    private int instanceID;
    private final GridBagLayout InputLayout = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final List<FunctionControlPanel> functionControlPanelList;
    private final JPanel functionInputPosition;
    private final PolynomialFunctionControl polynomialFunctionControl;
    private ComboBoxColorPicker colorPicker = null;
    private final JTextField functionField = new JTextField(20);

    public FunctionControlPanel(List<FunctionControlPanel> functionControlPanelList, JPanel functionInputPosition, PolynomialFunctionControl polynomialFunctionControl, GraphPanel graphPanel) {
        this.functionControlPanelList = functionControlPanelList;
        this.functionInputPosition = functionInputPosition;
        this.polynomialFunctionControl = polynomialFunctionControl;
        this.instanceID = -1;
        this.graphPanel = graphPanel;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 1, 4, 1); //Sets the distance between the Objects
        setLayout(InputLayout);

        createFunctionField();
        createColorPicker();
        createCalculateButton();
        createDeriveButton();
        createDeleteButton();
        createValueTableButton();
    }

    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(instanceID == -1){
                instanceID = graphPanel.addFunction(functionField.getText(), colorPicker.getCurrentColor());
            } else {
                graphPanel.recalculateFunction(functionField.getText(), colorPicker.getCurrentColor(), instanceID);
            }
        }
    }

    private class DeriveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            graphPanel.deriveFunction(instanceID);
        }
    }

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (instanceID != -1) {
                graphPanel.removeFunction(instanceID);
            }
            removeFunctionControlPanel(FunctionControlPanel.this);
        }
    }
    private class TableActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(graphPanel.getValueTableIsActive(instanceID)){
                graphPanel.setValueTableIsActiveFalse(instanceID);
            if (instanceID != -1) {
                ParametricFunction function = graphPanel.getFunction(instanceID);
                new ValueTable(function);
            }
            }

        }
    }

    private void removeFunctionControlPanel(FunctionControlPanel functionControlPanel) {
        functionInputPosition.remove(functionControlPanel);
        functionControlPanelList.remove(functionControlPanel);
        polynomialFunctionControl.revalidate();
        polynomialFunctionControl.repaint();
    }

    public void createFunctionField() {
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 10; //Height of the TextField
        gbc.ipadx = 10; //Width of the TextField
        InputLayout.setConstraints(functionField, gbc);
        add(functionField);
    }
    public void createColorPicker() {
        this.colorPicker = new ComboBoxColorPicker();
        gbc.weightx = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        InputLayout.setConstraints(this.colorPicker, gbc);
        this.add(this.colorPicker);
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
    public void createValueTableButton() {
        JButton table = new JButton("Table");
        table.setFocusable(false);
        table.addActionListener(new TableActionListener());

        gbc.gridx = 3;
        gbc.gridy = 1;
        InputLayout.setConstraints(table, gbc);
        this.add(table);
    }
}
