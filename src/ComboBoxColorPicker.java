import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ComboBoxColorPicker extends JPanel {
    private final Color[] graphColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY};
    private final JComboBox<Color> colorPicker = new JComboBox<>(graphColors);   // Combo Box to pick the Color of the Function

    public ComboBoxColorPicker() {

        setLayout(new BorderLayout());
        colorPicker.setRenderer(new ColorListRenderer());
        colorPicker.addActionListener(new ColorBoxListener());
        add(colorPicker, BorderLayout.CENTER);
    }

    private class ColorBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color selectedColor = (Color) colorPicker.getSelectedItem(); // Gets the selected Color from the ComboBox (cast to Color)
            //TODO: Connect to the color setter of the function
        }
    }

    private class ColorListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); // Sets the component to the default ListCellRenderer
            if (value instanceof Color color && component instanceof JLabel) { // Checks if the value is a Color and the component is a JLabel
                ((JLabel) component).setText(getColorName(color)); // Sets the text of the JLabel to the name of the Color
                // Set a small colored square as the icon
                ((JLabel) component).setIcon(new ColorIcon(color, 16, 16)); // Sets the icon of the JLabel to a small colored square
            }
            return component;
        }
    }

    private String getColorName(Color color) { // Returns the name of the Color
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

    private class ColorIcon implements Icon { // Creates a small colored square as the icon
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
}