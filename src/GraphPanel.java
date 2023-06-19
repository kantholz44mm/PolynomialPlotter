import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GraphPanel extends JPanel {
    private double zoom = 1.0;
    private Vector2D offset = new Vector2D(0,0);
    private double scale = 50.0;
    private final Color backgroundColor = new Color(0.13f, 0.16f, 0.2f);
    private Vector2D lastMousePosition = null;
    private JTextField functionField;
    public JButton resetButton;
    private final List<ParametricFunction> polynomials = new ArrayList<>();
    List<Color> colours = Arrays.asList(Color.WHITE, Color.BLUE, Color.GREEN, Color.RED, Color.PINK);
    private class GraphMouseListener extends MouseAdapter {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            zoom -= e.getPreciseWheelRotation() * 0.1;
            if (zoom < 0.1) zoom = 0.1;
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            lastMousePosition = new Vector2D(e.getX(), e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Vector2D currentMousePosition = new Vector2D(e.getX(), e.getY());
            Vector2D delta = lastMousePosition.delta(currentMousePosition);
            offset.x += delta.x / scale;
            offset.y -= delta.y / scale;
            lastMousePosition = currentMousePosition;
            repaint();
        }
    }

    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(polynomials.size() < 3){
                String function = functionField.getText();
                double minT = toWorldCoordinates(new Vector2D(0, 0)).x;
                double maxT = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;
                polynomials.add(new PolynomialFunction(function, minT, maxT));
                repaint();
            } else {
                GraphPanel.infoBox("You reached the maximum amount of Graphs", "MAX_GRAPHS_REACHED");
            }

        }
    }

    private class ResetActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            offset = new Vector2D(0,0);
            zoom = 1.0;
            repaint();
        }
    }

    private class DeriveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // will be reimplemented in the interface to the gui
            //((PolynomialFunction)polynomial).derive();
            repaint();
        }
    }

    public GraphPanel() {
        // Parametric function will be added through either a secondary instance of graph panel or reinitialitianio
        // default parametric function to test functionality. Using the textfield it will become a polynomial
//        polynomial = t -> new Vector2D(
//                16.0 * Math.pow(Math.sin(t), 3.0),
//                13.0 * Math.cos(t) - 5.0 * Math.cos(2.0 * t) - 2.0 * Math.cos(3.0 * t) - Math.cos(4.0 * t)
//        );

//        if(polynomial instanceof PolynomialFunction){
//            double minT = toWorldCoordinates(new Vector2D(0, 0)).x;
//            double maxT = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;
//            zeroPoints = ((PolynomialFunction)polynomial).getZeroPoints(minT, maxT, 0.01);
//            extremePoints = ((PolynomialFunction)polynomial).getExtremePoints(minT, maxT, 0.01);
//        }

        GraphMouseListener graphMouseListener = new GraphMouseListener();
        addMouseWheelListener(graphMouseListener);
        addMouseListener(graphMouseListener);
        addMouseMotionListener(graphMouseListener);

        createResetButton();
        createFunctionField();
        createCalculateButton();
        createDeriveButton();
    }

    private void createFunctionField() {
        functionField = new JTextField(20);
        add(functionField);
    }

    private void createCalculateButton() {
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateActionListener());
        add(calculateButton);
    }

    private void createResetButton() {
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetActionListener());
        add(resetButton);
    }
    private void createDeriveButton() {
        JButton deriveButton = new JButton("Derive");
        deriveButton.addActionListener(new DeriveActionListener());
        add(deriveButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        clearBackground(g2d, width, height);
        updateScale();
        double step = calculateGridStep();
        Vector2D zero = toScreenCoordinates(new Vector2D(0,0));

        drawAxes(g2d, width, height, zero);
        drawGrid(g2d, width, height, step);
        drawFunctions(g2d, width);
        drawLabelsAndScales(g2d, width, height, step);
        drawInformationWindows(g2d);
    }

    private void clearBackground(Graphics2D g2d, int width, int height) {
        g2d.setColor(backgroundColor);
        g2d.fillRect(0, 0, width, height);
    }

    private void updateScale() {
        scale = 50.0 * zoom;
    }

    private double calculateGridStep() {
        double step = 1.0;
        while (scale * step < 20) {
            step *= 2;
        }
        while (scale * step > 100) {
            step /= 2;
        }
        return step;
    }

    private void drawAxes(Graphics2D g2d, int width, int height, Vector2D zero) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.drawLine(0, (int)zero.y, width, (int)zero.y);
        g2d.drawLine((int)zero.x, 0, (int)zero.x, height);
    }

    private void drawGrid(Graphics2D g2d, int width, int height, double step) {
        g2d.setColor(new Color(200, 200, 200, 40));

        for (double x = step * Math.floor((-offset.x - width / (2.0 * scale)) / step); x <= -offset.x + width / (2.0 * scale); x += step) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(x,0));
            int screenX = (int) screenPoint.x;
            g2d.drawLine(screenX, 0, screenX, height);
        }

        for (double y = step * Math.floor((-offset.y - height / (2.0 * scale)) / step); y <= -offset.y + height / (2.0 * scale); y += step) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(0,y));
            int screenY = (int) screenPoint.y;
            g2d.drawLine(0, screenY, width, screenY);
        }
    }

    private void drawFunctions(Graphics2D g2d, int width) {
        GeneralPath path;

        double minT = toWorldCoordinates(new Vector2D(0, 0)).x;
        double maxT = toWorldCoordinates(new Vector2D(width, 0)).x;

        int numSteps = (int) Math.max(5000, 10000 * zoom);

        double tStep = (maxT - minT) / numSteps;


        for (int x = 0; x < polynomials.size(); x++) {
            path = new GeneralPath();
            double t = minT;

            Vector2D initialPosition = toScreenCoordinates(polynomials.get(x).evaluate(t));
            path.moveTo(initialPosition.x, initialPosition.y);

            for (int i = 0; i < numSteps; i += 3) {
                double t1 = t + tStep;
                double t2 = t1 + tStep;
                double t3 = t2 + tStep;

                Vector2D p1 = toScreenCoordinates(polynomials.get(x).evaluate(t1));
                Vector2D p2 = toScreenCoordinates(polynomials.get(x).evaluate(t2));
                Vector2D p3 = toScreenCoordinates(polynomials.get(x).evaluate(t3));

                path.curveTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);

                t = t3;
            }

            g2d.setStroke(new BasicStroke(2.0f));
            g2d.setColor(colours.get(x % colours.size()));
            g2d.draw(path);
        }
    }

    private void drawLabelsAndScales(Graphics2D g2d, int width, int height, double step) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        int tickSize = 3;

        for (double x = step * Math.floor((-offset.x - width / (2.0 * scale)) / step); x <= -offset.x + width / (2.0 * scale); x += step) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(x,0));
            Vector2D zeroPoint = toScreenCoordinates(new Vector2D(0,0));
            g2d.drawLine((int) screenPoint.x, (int) zeroPoint.y - tickSize, (int) screenPoint.x, (int) zeroPoint.y + tickSize);
            String label = String.format("%.2f", x);
            int textOffset = (x < 0) ? -2 - g2d.getFontMetrics().stringWidth(label) : 2;
            g2d.drawString(label, (int) screenPoint.x + textOffset, (int) zeroPoint.y - 2);
        }

        for (double y = step * Math.floor((-offset.y - height / (2.0 * scale)) / step); y <= -offset.y + height / (2.0 * scale); y += step) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(0,y));
            Vector2D zeroPoint = toScreenCoordinates(new Vector2D(0,0));
            g2d.drawLine((int) zeroPoint.x - tickSize, (int) screenPoint.y, (int) zeroPoint.x + tickSize, (int) screenPoint.y);
            String label = String.format("%.2f", y);
            int textOffset = (y < 0) ? g2d.getFontMetrics().getAscent() + 2 : -2;
            g2d.drawString(label, (int) zeroPoint.x + 2, (int) screenPoint.y + textOffset);
        }
    }

    private void drawInformationWindows(Graphics2D g2d) {
        int boxWidth = (getWidth() - 40) / 3;
        int boxY = getHeight() - 90;
        int boxHeight = 80;
        Font font = new Font("Arial", Font.PLAIN, 12);

        int index = 0;
        for (Object function : polynomials) {
            if (function instanceof PolynomialFunction polyFunction) {
                int boxX = 10 + index * (boxWidth + 10);

                g2d.setColor(new Color(100, 100, 100, 200));
                g2d.fillRect(boxX, boxY, boxWidth, boxHeight);

                g2d.setColor(Color.WHITE);
                g2d.setFont(font);

                String functionString = "Function: " + polyFunction.getFunctionString();
                String zeroPointsStr = "Roots: " + polyFunction.roots.stream()
                        .map(z -> String.format("%.2f", z))
                        .collect(Collectors.joining(", "));
                String extremePointsStr = "Extreme Points: " + polyFunction.extremePoints.stream()
                        .map(e -> String.format("%.2f", e))
                        .collect(Collectors.joining(", "));

                g2d.drawString(functionString, boxX + 10, boxY + 20);
                g2d.drawString(zeroPointsStr, boxX + 10, boxY + 40);
                g2d.drawString(extremePointsStr, boxX + 10, boxY + 60);

                index++;
            }
        }
    }

    private Vector2D toScreenCoordinates(Vector2D position) {
        int zeroX = getWidth() / 2 + (int) (offset.x * scale);
        int zeroY =  getHeight() / 2 - (int) (offset.y * scale);

        int screenX = zeroX + (int) (position.x * scale);
        int screenY = zeroY - (int) (position.y * scale);

        return new Vector2D(screenX, screenY);
    }

    private Vector2D toWorldCoordinates(Vector2D position) {
        int zeroX = getWidth() / 2 + (int) (offset.x * scale);
        int zeroY = getHeight() / 2 - (int) (offset.y * scale);

        double x = (position.x - zeroX) / scale;
        double y = (zeroY - position.y) / scale;

        return new Vector2D(x, y);
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

}
