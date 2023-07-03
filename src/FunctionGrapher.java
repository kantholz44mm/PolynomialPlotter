import GUI.GraphPlotterFrame;

import javax.swing.SwingUtilities;

public class FunctionGrapher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            GraphPlotterFrame GraphPlotterFrame = new GraphPlotterFrame();
        });
    }
}
