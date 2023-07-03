package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ComboBoxColorPicker extends JPanel {
    private final Color[] graphColors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.GRAY};
    private final JComboBox<Color> colorPicker = new JComboBox<>(graphColors);
    public Color currentColor;

    public ComboBoxColorPicker() {
        setLayout(new BorderLayout());
        colorPicker.setRenderer(new ColorListRenderer());
        colorPicker.addActionListener(new ColorBoxListener());
        currentColor = (Color) colorPicker.getSelectedItem();
        add(colorPicker, BorderLayout.CENTER);
    }

    private class ColorBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentColor = (Color) colorPicker.getSelectedItem();
        }
    }

    private class ColorListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Color color && component instanceof JLabel) {
                ((JLabel) component).setText(getColorName(color));
                ((JLabel) component).setIcon(new ColorIcon(color, 16, 16));
            }
            return component;
        }
    }

    private String getColorName(Color color) {
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

    private class ColorIcon implements Icon {
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