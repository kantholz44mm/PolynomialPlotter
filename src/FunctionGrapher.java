import GUI.GraphPanel;
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
        GraphPanel.valuetable valuetable = new GraphPanel.valuetable(20,40,2);
        //Create a valuetable: here for test purposes
    }
}
