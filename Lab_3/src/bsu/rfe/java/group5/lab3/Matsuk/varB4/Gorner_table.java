package bsu.rfe.java.group5.lab3.Matsuk.varB4;

import javax.swing.table.AbstractTableModel;

public class Gorner_table extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public Gorner_table(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double get_from() {
        return from;
    }

    public Double get_to() {
        return to;
    }

    public Double get_step() {
        return step;
    }

    @Override
    public int getRowCount() {
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int row, int col) {
Double x = from + step * row;
   Double y=coefficients[0];
        for (int i = 1; i < coefficients.length; i++) {
            y = y * x + coefficients[i];
        }
        Boolean z = gcd(x.intValue(), y.intValue());
        switch (col){
            case 0: return x;
            case 1: return y;
            case 2: return z;
        }
        return null;
    }
    private Boolean gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a == 1;
    }

    @Override
    public String getColumnName(int col) {
switch(col){
    case 0:
        return "Значение Х";
    case 1:
        return "Значение многочлена";
    case 2:
        return "Взаимно простые";
}
return " ";
    }

    public Class <?> getColumnClass(int col){
       if(col==2)
           return Boolean.class;
       return Double.class;
    }

}