package GUI;
import Core.ParametricFunction;
import Core.PolynomialFunction;
import Core.Vector2D;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValueTable extends JDialog {
    private ParametricFunction parametricFunction = null;
    private JTable table = null;
    private final JSpinner startSpinner = new JSpinner(new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
    private final JSpinner endSpinner = new JSpinner(new SpinnerNumberModel(1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
    private final JSpinner stepSpinner = new JSpinner(new SpinnerNumberModel(1,  0.001,Double.POSITIVE_INFINITY, 1));

    private void calculateValueTable(double start, double end, double steps) {
        String[][] valueTableStrings = null;
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        tableModel.setRowCount(0);
        table.revalidate();
        steps = Math.abs(steps);
        if (steps == 0) {
            steps = 1;
        }

        int cellAmount = (int) (Math.ceil((end - start) / steps));
        int size = ++cellAmount;
        valueTableStrings = new String[size][3];

        for (int i = 0; i <= cellAmount; i++) {
            double currentPosition = start + i * steps;
            if (currentPosition <= end) {
                Vector2D result = parametricFunction.evaluate(currentPosition).round(6);
                if (parametricFunction instanceof PolynomialFunction) {
                    valueTableStrings[i][0] = "X: " + currentPosition;
                    valueTableStrings[i][1] = "Y: " + result.y;
                } else {
                    valueTableStrings[i][0] = "T: " + currentPosition;
                    valueTableStrings[i][1] = "X: " + result.x;
                    valueTableStrings[i][2] = "Y: " + result.y;
                }
                tableModel.addRow(valueTableStrings[i]);
            }
        }
    }

    public ValueTable(ParametricFunction parametricFunction) {
        final JButton   calculateButton = new JButton("Calculate");
        final JTextField startLabel = new JTextField("Start:");
        final JTextField endLabel = new JTextField("End:");
        final JTextField stepLabel = new JTextField("Steps:");
        final GridLayout panelLayout = new GridLayout(2,0);
        final JPanel calculationParameters = new JPanel();

        new JDialog();
        this.parametricFunction = parametricFunction;
        this.setTitle("ValueTable");
        BoxLayout valueTableLayout = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
        this.setLayout(valueTableLayout);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        String[] columnNames = null;

        if (parametricFunction instanceof PolynomialFunction) {
            columnNames = new String[]{"x - Values", "f(x) - Values"};
        }
        else {
            columnNames = new String[]{"t - Values", "x - Values", "f(x) - Values"};
        }

        DefaultTableModel defaultTableModel = new DefaultTableModel(columnNames,0);
        table = new JTable(defaultTableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setBounds(30, 40, 200, 300);
        this.add(new JScrollPane(table));

        startLabel.setEditable(false);
        endLabel.setEditable(false);
        stepLabel.setEditable(false);

        calculationParameters.setLayout(panelLayout);
        calculationParameters.add(startLabel);
        calculationParameters.add(endLabel);
        calculationParameters.add(stepLabel);
        calculationParameters.add(new JLabel());
        calculationParameters.add(startSpinner);
        calculationParameters.add(endSpinner);
        calculationParameters.add(stepSpinner);
        calculationParameters.add(calculateButton);
        calculationParameters.setPreferredSize(new Dimension(500,100));
        calculateButton.addActionListener(new ValueTable.CalculateActionListener());

        this.add(calculationParameters);
        this.setSize(500, 600);
        this.setVisible(true);
    }

    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            calculateValueTable((double)startSpinner.getValue(), (double)endSpinner.getValue(), (double)stepSpinner.getValue());
        }
    }
}
