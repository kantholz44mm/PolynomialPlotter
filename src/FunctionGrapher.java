import GUI.GraphPlotterFrame;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class FunctionGrapher {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(GraphPlotterFrame::new);
    }
}
