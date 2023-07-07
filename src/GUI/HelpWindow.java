import GUI.GraphPlotterFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

public class HelpWindow extends JDialog {

    public HelpWindow() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1200, 1150));
        setUndecorated(true);
        setLayout(new BorderLayout());
        setResizable(false);
        JTextPane helpText = new JTextPane();
        helpText.setEditable(false);

        helpText.setContentType("text/html");
        try {
            URL filePath = getClass().getResource("/help.html");
            helpText.setPage(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JScrollPane scrollPane = new JScrollPane(helpText);
        add(scrollPane, BorderLayout.CENTER);
        addWindowListener(new ClickOutListener());
        pack();

        setLocationRelativeTo(GraphPlotterFrame.getInstance());
        setVisible(true);
    }


    private class ClickOutListener extends WindowAdapter {
        @Override
        public void windowDeactivated(WindowEvent e) {
            dispose();
        }
    }


}
