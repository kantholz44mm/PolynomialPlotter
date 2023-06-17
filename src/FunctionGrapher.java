import javax.swing.*;

public class FunctionGrapher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Polynomial Plotter");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            GraphPanel graphPanel = new GraphPanel("Heart");
            frame.add(graphPanel);

            frame.setVisible(true);
        });
    }
}
