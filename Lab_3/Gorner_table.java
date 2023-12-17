package bsu.rfe.java.group5.lab3.Matsuk.varB4;

import javax.swing.table.AbstractTableModel;

public class Gorner_table extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    private final Object[][] table;
    private final int number_of_rows;
    public Gorner_table(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
        this.number_of_rows=(int) Math.ceil((to-from)/step)+1;
        this.table = new Object[number_of_rows][3];
        precalculate();
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
        return table[row][col];
    }

    @Override
    public String getColumnName(int col) {
switch(col){
    case 0:
        return "Значение Х";
    case 1:
        return "Значение многочлена";
    case 2:
        return "Разносторонние";
}
return " ";
    }
    private double calculate(double x, Double[] coeff) {

        Double[] gornerElems = new Double[coeff.length];
        gornerElems[0] = coeff[0];
        for (int i = 1; i < gornerElems.length; i++) {
            gornerElems[i] = gornerElems[i-1] * x + coeff[i];
        }
        return gornerElems[gornerElems.length - 1];
    }
    private void precalculate() {
        for (int i = 0; i < number_of_rows; i++) {
            table[i][0] = from + step * i;
            table[i][1] = calculate((Double) table[i][0], coefficients);
            table[i][2] = Equilateral((Double) table[i][1]);
        }
    }

    private boolean Equilateral(double num)
    {

        String[] check = Double.toString(num).split("\\."); // массив из двух элементов (до и после точки)
        System.out.println(check[0] + "   " + check[1]);
        double fractionalPart = Double.parseDouble(check[1]);
        return checkEven(check[0]) && checkOdd(Double.toString(fractionalPart).substring(0,3)) || checkOdd(check[0]) && checkEven(Double.toString(fractionalPart).substring(0,3));
    }

    private boolean checkEven(String str) {
        return str.chars().allMatch(ch -> (ch - '0') % 2 == 0);
    }

    // Метод для проверки, что все цифры в строке нечетные
    private boolean checkOdd(String str) {
        return str.chars().allMatch(ch -> (ch - '0') % 2 != 0);
    }

    public Class <?> getColumnClass(int col){
       if(col==2)
           return Boolean.class;
       return Double.class;
    }

}