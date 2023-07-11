package GUI;
import Core.ParametricFunction;
import Core.PolynomialFunction;
import Core.Vector2D;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Valuetable extends JDialog{
    private Double[][] valuetablefigures = null;
    private String[][] valuetablestrings = null;
    private ParametricFunction parametricFunction = null;
    private JTable table = null;
    private final GridLayout panelLayout = new GridLayout(2,0);
    private JPanel valuesforcalculation = new JPanel();

    private final JSpinner rangeStart = new JSpinner(new SpinnerNumberModel(0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
    private final JSpinner rangeEnd = new JSpinner(new SpinnerNumberModel(1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
    private final JSpinner stepSpinner = new JSpinner(new SpinnerNumberModel(1,  0.001,Double.POSITIVE_INFINITY, 1));
    private final JButton calculate = new JButton("Calculate");
    private  final JTextField startlabel = new JTextField("Start:");
    private  final JTextField endlabel = new JTextField("End:");
    private  final JTextField steplabel = new JTextField("Steps:");

    private void calculateValueTable(double start, double end, double steps) {
        DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
        tableModel.setRowCount(0);
        table.revalidate();
        steps = Math.abs(steps);

        if (steps == 0) {
            steps = 1;
        }

        int cellamount = (int) (Math.ceil((end - start) / steps))+1;
        int size = ++cellamount;

        valuetablefigures = new Double[size][size];
        valuetablestrings = new String[size][size];


        for (int i = 0; i <= cellamount; i++) {
            double currentposition = i * steps;
            Vector2D result = parametricFunction.evaluate(currentposition).round(6);
            if (parametricFunction instanceof PolynomialFunction) {
                valuetablestrings[i][0] = "X: " + currentposition;
                valuetablefigures[i][0] = currentposition;
                valuetablestrings[i][1] = "Y: " + result.y;
                valuetablefigures[i][1] = result.y;
            } else {
                valuetablestrings[i][0] = "T: " + currentposition;
                valuetablefigures[i][0] = currentposition;
                valuetablestrings[i][1] = "X: " + result.x;
                valuetablefigures[i][1] = result.x;
                valuetablestrings[i][2] = "Y: " + result.y;
                valuetablefigures[i][2] = result.y;
            }
            tableModel.addRow(valuetablestrings[i]);
        }
    }



    public Valuetable(ParametricFunction parametricFunction)
    {

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
        valuesforcalculation.setLayout(panelLayout);
        startlabel.setEditable(false);
        valuesforcalculation.add(startlabel);
        endlabel.setEditable(false);
        valuesforcalculation.add(endlabel);
        steplabel.setEditable(false);
        valuesforcalculation.add(steplabel);
        valuesforcalculation.add(new JLabel());
        valuesforcalculation.add(rangeStart);
        valuesforcalculation.add(rangeEnd);
        valuesforcalculation.add(stepSpinner);
        valuesforcalculation.add(calculate);
        calculate.addActionListener(new Valuetable.CalculateActionListener());


        valuesforcalculation.setPreferredSize(new Dimension(500,100));
        this.add(valuesforcalculation);

        this.setSize(500, 600);
        this.setVisible(true);

    }
    private class CalculateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            calculateValueTable((double)rangeStart.getValue(),(double)rangeEnd.getValue(),(double)stepSpinner.getValue());


        }
    }

}
