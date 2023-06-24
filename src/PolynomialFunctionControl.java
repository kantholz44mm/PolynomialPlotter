import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolynomialFunctionControl extends JPanel {

    PolynomialFunctionControl() {

        this.setBackground(Color.magenta);

        GridLayout PolyFunctionControlLayout = new GridLayout(10,0);
        this.setLayout(PolyFunctionControlLayout);

        FunctionInput functionInput = new FunctionInput(); //Initialisation of the Pane that holds the input for the Polynomial Function

        this.add(functionInput);


    }

    private class FunctionInput extends JPanel {

        int count = 0;
        Color[] Colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY};
        JComboBox<Color> colorPicker = new JComboBox<>(Colors);   //Combo Box to pick the Color of the Function

        GridBagLayout InputLayout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        private class ColorBoxListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = (Color) colorPicker.getSelectedItem(); //Gets the selected Color from the ComboBox (casted to Color)
                System.out.println("Selected color: " + selectedColor); //Test if the selected Color is correct
            }
        }

        private class CalculateActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (count < 3) {
                    count++;
                    PolynomialFunctionControl.this.add(new FunctionInput());
                    PolynomialFunctionControl.this.revalidate();
                }
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
            JTextField functionField = new JTextField(20);  //Textfield for the Function

            gbc.gridwidth = 2;
            gbc.gridx = 0;
            gbc.gridy = 0;  //Coordinates from where the Object is set
            gbc.ipady = 10; //Height of the TextField
            gbc.ipadx = 10; //Width of the TextField
            InputLayout.setConstraints(functionField, gbc);
            add(functionField);
        }

        public void createCalculateButton() {
            JButton calculate = new JButton("Calculate");       //Button to calculate the Function
            calculate.setFocusable(false);  //Removes the dotted line around the Button when it is clicked
            calculate.addActionListener(new CalculateActionListener());

            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            InputLayout.setConstraints(calculate, gbc);
            this.add(calculate);
        }

        public void createDeriveButton() {
            JButton derive = new JButton("Derive");     //Button to derive the Function
            derive.setFocusable(false);     //Removes the dotted line around the Button when it is clicked

            gbc.gridx = 1;
            gbc.gridy = 1;
            InputLayout.setConstraints(derive, gbc);
            this.add(derive);
        }

        public void createDeleteButton() {
            JButton delete = new JButton("Delete");     //Button to delete the Function
            delete.setFocusable(false);     //Removes the dotted line around the Button when it is clicked

            gbc.gridx = 2;
            gbc.gridy = 1;
            InputLayout.setConstraints(delete, gbc);
            this.add(delete);
        }

        public void createColorPicker() {
            JComboBox<Color> colorPicker = new JComboBox<>(Colors);   //Combo Box to pick the Color of the Function
            colorPicker.setRenderer(new ColorListRenderer());
            colorPicker.addActionListener(new ColorBoxListener());

            gbc.weightx = 1;
            gbc.gridx = 2;
            gbc.gridy = 0;  //Coordinates from where the Object is set
            InputLayout.setConstraints(colorPicker, gbc);
            this.add(colorPicker);
        }

        public FunctionInput() {

            this.setBackground(Color.gray);

            gbc.fill = GridBagConstraints.BOTH; //Sets the Object to fill the Horizontal Space
            gbc.insets = new Insets(5, 1, 5, 1); //Sets the distance between the Objects
            setLayout(InputLayout);

            createFunctionField();
            createColorPicker();
            createCalculateButton();
            createDeriveButton();
            createDeleteButton();
        }
    }
}
