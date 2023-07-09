package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class HelpWindow extends JDialog {

    private static HelpWindow instance;
    public HelpWindow() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setResizable(false);
        JTextPane helpText = new JTextPane();
        helpText.setEditable(false);
        helpText.setFocusable(false);

        helpText.setContentType("text/html");
        try {
            URL filePath = getClass().getResource("help.html");
            helpText.setPage(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JScrollPane scrollPane = new JScrollPane(helpText);
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            GraphPlotterFrame frame = GraphPlotterFrame.getInstance();
            // Determine new size based on GraphPlotterFrame.
            Dimension frameSize = frame.getSize();
            int newWidth = (int) (frameSize.width * 0.85); // 85% of frame width
            int newHeight = (int) (frameSize.height * 0.85); // 85% of frame height
            this.setSize(newWidth, newHeight);

            // Calculate the new position of the HelpWindow to center it on the GraphPlotterFrame.
            Point frameLocation = frame.getLocation();
            int centerX = frameLocation.x + frameSize.width / 2 - newWidth / 2;
            int centerY = frameLocation.y + frameSize.height / 2 - newHeight / 2;
            this.setLocation(centerX, centerY);
        }
        super.setVisible(b);
    }

    public static HelpWindow getInstance() {
        if (instance == null) {
            instance = new HelpWindow();
        }
        return instance;
    }
}
