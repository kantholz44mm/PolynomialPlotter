import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FunctionControlPanel extends JPanel {

    private final Color[] graphColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY};
    private JComboBox<Color> colorPicker = new JComboBox<>(graphColors);   //Combo Box to pick the Color of the Function
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

    private class ColorBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color selectedColor = (Color) colorPicker.getSelectedItem(); //Gets the selected Color from the ComboBox (cast to Color)
            //TODO: Connect to the function color "setter"
        }
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
            //TODO: Connect to the function deleter to delete function on the GraphPanel
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

    private class ColorListRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //Sets the component to the default ListCellRenderer
                if (value instanceof Color color && component instanceof JLabel) { //Checks if the value is a Color and the component is a JLabel

                    ((JLabel) component).setText(getColorName(color)); //Sets the text of the JLabel to the name of the Color

                    //Set a small colored square as the icon
                    ((JLabel) component).setIcon(new ColorIcon(color, 16, 16)); //Sets the icon of the JLabel to a small colored square
                }
                return component;
            }
        }

        private String getColorName(Color color) { //Returns the name of the Color
            if (color.equals(Color.RED)) {
                return "Red";
            } else if (color.equals(Color.BLUE)) {
                return "Blue";
            } else if (color.equals(Color.YELLOW)) {
                return "Yellow";
            } else if (color.equals(Color.GREEN)) {
                return "Green";
            } else if (color.equals(Color.GRAY)) {
                return "Gray";
            }
            return color.toString();
        }

        private class ColorIcon implements Icon { //Creates a small colored square as the icon
            private final Color color;
            private final int width;
            private final int height;

            public ColorIcon(Color color, int width, int height) {
                this.color = color;
                this.width = width;
                this.height = height;
            }

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillRect(x, y, width, height);
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
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
            colorPicker = new JComboBox<>(graphColors);
            colorPicker.setRenderer(new ColorListRenderer());
            colorPicker.addActionListener(new ColorBoxListener());

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