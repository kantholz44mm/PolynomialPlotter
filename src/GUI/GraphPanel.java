package GUI;


import Core.ParametricFunction;
import Core.PolynomialFunction;
import Core.Vector2D;
import MathExpression.ParametricExpression;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphPanel extends JPanel {
    private final HashMap<UUID, ParametricFunction> functions = new HashMap<>();
    private List<Vector2D> intersections = new ArrayList<>();
    private ParametricExpression parametricExpression = null;
    private Vector2D offset = new Vector2D(0, 0);
    private Vector2D lastMousePosition = new Vector2D(0, 0);
    private double zoom = 1.0;
    private Vector2D currentGraphPoint = null;
    private final Map<String, Boolean>  options = new HashMap<>();

    public GraphPanel() {
        GraphMouseListener graphMouseListener = new GraphMouseListener();
        addMouseWheelListener(graphMouseListener);
        addMouseListener(graphMouseListener);
        addMouseMotionListener(graphMouseListener);

        options.put("Draw Intersection Points", true);
        options.put("Draw Information Windows", true);
        options.put("Draw Background Grid", true);
        options.put("Draw x- and y-axis", true);
        options.put("Draw Scales", true);
        options.put("Draw Mouse Intersection Point", true);

        createOptionsButton();
    }

    private class GraphMouseListener extends MouseAdapter {
        @Override
        public void mouseWheelMoved(MouseWheelEvent event) {
            double scaleDelta = -event.getPreciseWheelRotation();
            Vector2D preZoomPosition = toWorldCoordinates(new Vector2D(event.getX(), event.getY()));
            zoom += zoom * scaleDelta * 0.1;
            zoom = Math.max(0.1, Math.min(zoom, 500));
            Vector2D postZoomPosition = toWorldCoordinates(new Vector2D(event.getX(), event.getY()));
            Vector2D delta = preZoomPosition.delta(postZoomPosition);
            offset.x += delta.x;
            offset.y += delta.y;
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
            offset.x += delta.x / getScale();
            offset.y -= delta.y / getScale();
            lastMousePosition = currentMousePosition;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            lastMousePosition = new Vector2D(e.getX(), e.getY());
            currentGraphPoint = findNearestGraphPoint(lastMousePosition);
            repaint();
        }
    }

    private Vector2D findNearestGraphPoint(Vector2D screenPosition) {
        Vector2D worldPosition = toWorldCoordinates(screenPosition);
        double lowerScreenBound = toWorldCoordinates(new Vector2D(0, 0)).x;
        double upperScreenBound = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;

        final double MAX_DISTANCE = 1.0; // adjust mouse to graph distance
        Vector2D nearestPoint = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (ParametricFunction function : functions.values()) {
            for (double t = lowerScreenBound; t <= upperScreenBound; t += 0.001) {
                Vector2D point = function.evaluate(t);
                double distance = point.distance(worldPosition);
                if (distance < nearestDistance && distance <= MAX_DISTANCE) {
                    nearestDistance = distance;
                    nearestPoint = point;
                }
            }
        }

        return nearestPoint;
    }

    private void createOptionsButton() {
        setLayout(new BorderLayout());
        JButton optionsButton = new JButton(new ImageIcon("./gear.png"));
        optionsButton.addActionListener(e -> openOptionsMenu());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(null);
        buttonPanel.add(optionsButton);

        add(buttonPanel, BorderLayout.NORTH);
    }

    private void openOptionsMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (String option : options.keySet()) {
            JCheckBox checkBox = new JCheckBox(option, options.get(option));
            checkBox.addItemListener(e -> options.put(option, e.getStateChange() == ItemEvent.SELECTED));
            panel.add(checkBox);
        }

        JOptionPane.showMessageDialog(this, panel, "Options", JOptionPane.PLAIN_MESSAGE);
    }

    // addFunction gets the function as string and its requested color.
    // It returns the index of the listEntry so that the functionControl of the GUI
    // can identify to which function in the functions list it belongs to.
    public UUID addFunction(String functionString, Color currentColor) {
        double minT = toWorldCoordinates(new Vector2D(0, 0)).x;
        double maxT = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;
        try {
            PolynomialFunction function = new PolynomialFunction(functionString, currentColor);
            function.calcRootsAndExtremes(minT, maxT, 0.001);
            UUID id = UUID.randomUUID();  // generate a random UUID
            functions.put(id, function);
            calculateIntersections();
            repaint();
            return id;
        } catch (IllegalArgumentException e) {
            showErrorDialog(e.getMessage());
            return null;
        }
    }

    public void setParametricFunction(ParametricExpression expression) {
        this.parametricExpression = expression;
        repaint();
    }

    public ParametricFunction getFunction(UUID uuid) {
        return functions.get(uuid);
    }

    public ParametricExpression getParametricExpression() {
        return parametricExpression;
    }

    public void recalculateFunction(String functionString, Color currentColor, UUID uuid) {
        double minT = toWorldCoordinates(new Vector2D(0, 0)).x;
        double maxT = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;
        try {
            PolynomialFunction function = new PolynomialFunction(functionString, currentColor);
            functions.put(uuid, function);

            if (functions.get(uuid) instanceof PolynomialFunction polynomialFunction) {
                polynomialFunction.calcRootsAndExtremes(minT, maxT, 0.001);
            }
            calculateIntersections();
            repaint();
        } catch (IllegalArgumentException e) {
            showErrorDialog(e.getMessage());
        }
    }

    public void removeFunction(UUID uuid) {
        functions.remove(uuid);
        calculateIntersections();
        repaint();
    }

    public void deriveFunction(UUID uuid) {
        if (functions.get(uuid) instanceof PolynomialFunction polynomialFunction) {
            polynomialFunction.derive();
            calculateIntersections();
            repaint();
        }
    }

    public void cameraReset() {
        offset = new Vector2D(0, 0);
        zoom = 1.0;
        repaint();
    }

    public BufferedImage screenshot() {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        paint(image.createGraphics());
        return image;
    }

    public void screenshotToFile() {
        BufferedImage image = screenshot();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));

        if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return; // user aborted file selection
        }

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        File imageFile = new File(path);
        try {
            ImageIO.write(image, "png", imageFile);
            JOptionPane.showMessageDialog(this, "Saved to file: " + path, "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        int width = getWidth();
        int height = getHeight();

        g2d.setColor(new Color(0.13f, 0.16f, 0.2f));
        g2d.fillRect(0, 0, width, height);
        double step = calculateGridStep();
        Vector2D zero = toScreenCoordinates(new Vector2D(0, 0));

        if(options.get("Draw x- and y-axis"))drawAxes(g2d, width, height, zero);
        if(options.get("Draw Background Grid"))drawGrid(g2d, step);
        drawFunctions(g2d);
        if(options.get("Draw Scales")) drawLabelsAndScales(g2d, width, height, step);
        if(options.get("Draw Information Windows")) drawInformationWindows(g2d);
        if(options.get("Draw Intersection Points")) drawIntersections(g2d);
        if(options.get("Draw Mouse Intersection Point")) drawMouseIntersectionPoint(g2d);
    }

    private double getScale() {
        return 50.0 * zoom;
    }

    private double calculateGridStep() {
        double step = 1.0;
        while (getScale() * step < 20) {
            step *= 2;
        }
        while (getScale() * step > 100) {
            step /= 2;
        }
        return step;
    }

    private void drawAxes(Graphics2D g2d, int width, int height, Vector2D zero) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawLine(0, (int) zero.y, width, (int) zero.y);
        g2d.drawLine((int) zero.x, 0, (int) zero.x, height);
    }

    private void drawGrid(Graphics2D g2d, double step) {
        g2d.setColor(new Color(200, 200, 200, 40));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        Vector2D topLeft = toWorldCoordinates(new Vector2D(0, 0));
        Vector2D bottomRight = toWorldCoordinates(new Vector2D(getWidth(), getHeight()));
        topLeft = new Vector2D(Math.floor(topLeft.x / step) * step, Math.ceil(topLeft.y / step) * step);
        bottomRight = new Vector2D(Math.ceil(bottomRight.x / step) * step, Math.floor(bottomRight.y / step) * step);

        for (int x = 0; x < (bottomRight.x - topLeft.x) / step; x++) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(topLeft.x + x * step, 0));
            g2d.fillRect((int) screenPoint.x, 0, 1, getHeight());
        }

        for (int y = 0; y < (topLeft.y - bottomRight.y) / step; y++) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(0, bottomRight.y + y * step));
            g2d.fillRect(0, (int) screenPoint.y, getWidth(), 1);
        }
    }

    private void drawParametricFunction(Graphics2D g2d, ParametricFunction function, double lowerBound, double upperBound, int samples, Color color) {
        Path2D.Double path = new Path2D.Double();
        double sampleLength = (upperBound - lowerBound) / samples;

        Vector2D startPosition = toScreenCoordinates(function.evaluate(lowerBound));
        path.moveTo(startPosition.x, startPosition.y);

        for (int i = 1; i <= samples; i++) {
            double t = lowerBound + i * sampleLength;
            Vector2D position = toScreenCoordinates(function.evaluate(t));
            path.lineTo(position.x, position.y);
        }

        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.draw(path);
    }

    private void drawFunctions(Graphics2D g2d) {
        double lowerScreenBound = toWorldCoordinates(new Vector2D(0, 0)).x;
        double upperScreenBound = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;

        for (ParametricFunction parametricFunction : functions.values()) {
            PolynomialFunction polynomialFunction = (PolynomialFunction) parametricFunction;
            drawParametricFunction(g2d, polynomialFunction, lowerScreenBound, upperScreenBound, getWidth(), polynomialFunction.graphColor);
        }

        if (parametricExpression != null) {
            drawParametricFunction(g2d, parametricExpression, parametricExpression.lowerBound, parametricExpression.upperBound, parametricExpression.numSamples, Color.WHITE);
        }
    }

    private void drawLabelsAndScales(Graphics2D g2d, int width, int height, double step) {
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(0.5f));
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int tickSize = 3;

        for (double x = step * Math.floor((-offset.x - width / (2.0 * getScale())) / step); x <= -offset.x + width / (2.0 * getScale()); x += step) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(x, 0));
            Vector2D zeroPoint = toScreenCoordinates(new Vector2D(0, 0));
            g2d.drawLine((int) screenPoint.x, (int) zeroPoint.y - tickSize, (int) screenPoint.x, (int) zeroPoint.y + tickSize);
            String label = String.format("%.2f", x);
            int textOffset = (x < 0) ? -2 - g2d.getFontMetrics().stringWidth(label) : 2;
            g2d.drawString(label, (int) screenPoint.x + textOffset, (int) zeroPoint.y - 2);
        }

        for (double y = step * Math.floor((-offset.y - height / (2.0 * getScale())) / step); y <= -offset.y + height / (2.0 * getScale()); y += step) {
            Vector2D screenPoint = toScreenCoordinates(new Vector2D(0, y));
            Vector2D zeroPoint = toScreenCoordinates(new Vector2D(0, 0));
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
        for (Object function : functions.values()) {
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
                g2d.setColor(polyFunction.graphColor);
                g2d.fillOval(boxX + boxWidth - 30, boxY + 10, 20, 20);
                index++;
            }
        }
    }

    private void drawIntersections(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.YELLOW);
        Font font = new Font("Arial", Font.BOLD, 14);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);

        for (Vector2D intersection : intersections) {
            Vector2D screenPoint = toScreenCoordinates(intersection);
            g2d.fillOval((int) screenPoint.x - 6, (int) screenPoint.y - 6, 12, 12);
            if (screenPoint.distance(lastMousePosition) < 5) {
                drawPoint(g2d, intersection, screenPoint, metrics);
            }
        }
    }

    private void calculateIntersections() {
        intersections = new ArrayList<>();
        double minT = toWorldCoordinates(new Vector2D(0, 0)).x;
        double maxT = toWorldCoordinates(new Vector2D(getWidth(), 0)).x;
        double step = 0.001;

        if (functions.size() < 2) {
            return;
        }
        double EPSILON = 1E-8;

        for (ParametricFunction functionOuter : functions.values()) {
            for (ParametricFunction functionInner : functions.values()) {
                if (functionInner.equals(functionOuter)) continue;
                double prevY = functionOuter.evaluate(minT).y - functionInner.evaluate(minT).y;

                for (double x = minT + step; x <= maxT; x += step) {
                    double currY = functionOuter.evaluate(x).y - functionInner.evaluate(x).y;

                    if (Math.abs(currY) < EPSILON) {
                        intersections.add(new Vector2D(x, functionOuter.evaluate(x).y));
                    } else if (prevY * currY < 0) {
                        // Linearly interpolate to find a more accurate intersection point
                        double intersectX = (x - step) + step * Math.abs(prevY) / (Math.abs(prevY) + Math.abs(currY));
                        double intersectY = functionOuter.evaluate(intersectX).y;
                        intersections.add(new Vector2D(intersectX, intersectY));
                    }
                    prevY = currY;
                }
            }
        }
    }

    private void drawMouseIntersectionPoint(Graphics2D g2d){
        if (currentGraphPoint != null) {
            Font font = new Font("Arial", Font.BOLD, 14);
            g2d.setFont(font);
            FontMetrics metrics = g2d.getFontMetrics(font);

            Vector2D screenPoint = toScreenCoordinates(currentGraphPoint);
            g2d.setColor(Color.RED);
            g2d.fillOval((int)screenPoint.x - 5, (int)screenPoint.y - 5, 10, 10);

            drawPoint(g2d, currentGraphPoint, screenPoint, metrics);
        }
    }

    private void drawPoint(Graphics2D g2d, Vector2D point, Vector2D screenPoint, FontMetrics metrics){
        String graphPoint = String.format("(%.2f, %.2f)", point.x, point.y);
        int x = (int) screenPoint.x + 5;
        int y = (int) screenPoint.y - 5;

        int textWidth = metrics.stringWidth(graphPoint);
        int textHeight = metrics.getHeight();

        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(x - 2, y - textHeight, textWidth + 4, textHeight + 2);

        g2d.setColor(Color.YELLOW);
        g2d.drawString(graphPoint, x, y);
    }

    private Vector2D toScreenCoordinates(Vector2D position) {
        double zeroX = (double) getWidth() / 2.0 + (offset.x * getScale());
        double zeroY = (double) getHeight() / 2.0 - (offset.y * getScale());

        double screenX = zeroX + (position.x * getScale());
        double screenY = zeroY - (position.y * getScale());

        return new Vector2D(screenX, screenY);
    }

    private Vector2D toWorldCoordinates(Vector2D position) {
        double zeroX = (double) getWidth() / 2.0 + (offset.x * getScale());
        double zeroY = (double) getHeight() / 2.0 - (offset.y * getScale());

        double x = (position.x - zeroX) / getScale();
        double y = (zeroY - position.y) / getScale();

        return new Vector2D(x, y);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

