package bsu.rfe.java.group5.lab2.Matsuk.varB4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Formula extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;
    private JTextField textFieldResult;
    private JTextField text_mem1;
    private JTextField text_mem2;
    private JTextField text_mem3;
    private ButtonGroup radioButtons_calculations = new ButtonGroup();
    private ButtonGroup radioButtons_memory = new ButtonGroup();
    //private ButtonGroup radio_buttons = new ButtonGroup();
    private Box hboxFormulaType = Box.createHorizontalBox();
    private int FormulaId_calculations = 1;
    private int FormulaId_memory=1;
    public Double formula_1(Double x, Double y, Double z) {
        return Math.sin(Math.sin(y) + Math.exp(Math.cos(y)) + z * z) * Math.pow(Math.sin(3.1415 * y) + Math.log(x * x), 0.25);
    }

    public Double formula_2(Double x, Double y, Double z) {
        return Math.pow(x, x) / (Math.pow(y * y * y + 1, 0.5) + z * Math.log(x));
    }
    private void add_radio_button_memory(String buttonName, final int formulaId, ButtonGroup radioButtons, Box boxForButtons) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Formula.this.FormulaId_memory = formulaId;
            }
        });
        radioButtons.add(button);
        boxForButtons.add(button);
    }
    private void add_radio_button(String button_name, final int formulaId,ButtonGroup radio_buttons, Box boxForButtons) {
        JRadioButton button = new JRadioButton(button_name);
        button.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent ev){Formula.this.FormulaId_calculations=formulaId;
}
        });
        radio_buttons.add(button);
        boxForButtons.add(button);
    }
    public Formula() {
        super("Вычисление формулы");
        setSize(WIDTH,HEIGHT);
        Toolkit kit=Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                     kit.getScreenSize().height-HEIGHT/2);
        hboxFormulaType.add(Box.createHorizontalGlue());
        Box box_for_radio_buttons = Box.createHorizontalBox();
        box_for_radio_buttons.add(Box.createHorizontalGlue());
        add_radio_button("Формула 1", 1, radioButtons_calculations, box_for_radio_buttons);
        add_radio_button("Формула 2", 2, radioButtons_calculations, box_for_radio_buttons);
        radioButtons_calculations.setSelected(radioButtons_calculations.getElements().nextElement().getModel(),true);
        box_for_radio_buttons.add(Box.createHorizontalGlue());
        box_for_radio_buttons.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Box box_for_radio_buttons_memories = Box.createHorizontalBox();
        add_radio_button_memory("mem 1", 1, radioButtons_memory, box_for_radio_buttons_memories);
        add_radio_button_memory("mem 2", 2, radioButtons_memory,box_for_radio_buttons_memories);
        add_radio_button_memory("mem 3", 3, radioButtons_memory, box_for_radio_buttons_memories);
        radioButtons_memory.setSelected(radioButtons_memory.getElements().nextElement().getModel(), true);

        JLabel label_for_x = new JLabel("X =");
        textFieldX = new JTextField(10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel label_for_y = new JLabel("Y =");
        textFieldY = new JTextField(10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel label_for_z=new JLabel("Z =");
        textFieldZ = new JTextField(10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());

        Box box_variebles=Box.createHorizontalBox();
        box_variebles.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        box_variebles.add(Box.createHorizontalGlue());
        box_variebles.add(label_for_x);
        box_variebles.add(Box.createHorizontalStrut(15));
        box_variebles.add(textFieldX);
        box_variebles.add(Box.createHorizontalStrut(50));
        box_variebles.add(label_for_y);
        box_variebles.add(Box.createHorizontalStrut(15));
        box_variebles.add(textFieldY);
        box_variebles.add(Box.createHorizontalStrut(40));
        box_variebles.add(label_for_z);
        box_variebles.add(Box.createHorizontalStrut(15));
        box_variebles.add(textFieldZ);
        box_variebles.add(Box.createHorizontalGlue());

        JLabel label_for_Result = new JLabel("Результат = ");
        textFieldResult=new JTextField("0",10);
        Box boxResult=Box.createHorizontalBox();
        boxResult.add(Box.createHorizontalGlue());
        boxResult.add(label_for_Result);
        boxResult.add(Box.createVerticalStrut(10));
        boxResult.add(label_for_Result);
        boxResult.add(Box.createHorizontalGlue());
        boxResult.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        JLabel label_mem1 = new JLabel("mem1:");
        text_mem1 = new JTextField("0", 12);
        text_mem1.setMaximumSize(text_mem1.getPreferredSize());

        JLabel label_mem2 = new JLabel("mem2:");
        text_mem2 = new JTextField("0", 12);
        text_mem2.setMaximumSize(text_mem2.getPreferredSize());

        JLabel label_mem3 = new JLabel("mem3:");
        text_mem3 = new JTextField("0", 12);
        text_mem3.setMaximumSize(text_mem3.getPreferredSize());

        JButton button_calc= new JButton("Посчитать = ");
        button_calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try{
                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;
                    if(FormulaId_calculations == 1)
                        result=formula_1(x,y,z);
                    else
                        result=formula_2(x,y,z);
                    label_for_Result.setText(result.toString());
                }
                catch(NumberFormatException ex){
                    System.out.println("Ты попуск");
                }
            }
        });
        JButton button_reset = new JButton("Очистка");
        button_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });
        JButton button_MC = new JButton("MC");
        button_MC.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ev){
                try{
                    if (FormulaId_memory == 1)
                        text_mem1.setText("0");
                    else if (FormulaId_memory == 2)
                        text_mem2.setText("0");
                    else
                        text_mem3.setText("0");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Formula.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JButton button_MPlus = new JButton("M+");
        button_MPlus.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent ev){
                try{
                    Double result = Double.parseDouble(textFieldResult.getText());
                    if (FormulaId_memory == 1)
                        text_mem1.setText(Double.toString(Double.parseDouble(text_mem1.getText()) + result));
                    else if (FormulaId_memory == 2)
                        text_mem2.setText(Double.toString(Double.parseDouble(text_mem2.getText()) + result));
                    else
                        text_mem3.setText(Double.toString(Double.parseDouble(text_mem3.getText()) + result));
                    textFieldResult.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Formula.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        Box hbox_buttons = Box.createHorizontalBox();
        hbox_buttons.add(Box.createHorizontalGlue());
        hbox_buttons.add(button_calc);
        hbox_buttons.add(Box.createHorizontalStrut(15));
        hbox_buttons.add(button_reset);
        hbox_buttons.add(Box.createHorizontalGlue());
        hbox_buttons.setBorder(BorderFactory.createLineBorder(Color.CYAN));

        Box box_for_memory_1 = Box.createHorizontalBox();
        box_for_memory_1.add(Box.createHorizontalGlue());
        box_for_memory_1.add(label_mem1);
        box_for_memory_1.add(Box.createHorizontalStrut(15));
        box_for_memory_1.add(text_mem1);
        box_for_memory_1.add(Box.createHorizontalGlue());

        Box box_for_memory_2 = Box.createHorizontalBox();
        box_for_memory_2.add(Box.createHorizontalGlue());
        box_for_memory_2.add(label_mem2);
        box_for_memory_2.add(Box.createHorizontalStrut(15));
        box_for_memory_2.add(text_mem2);
        box_for_memory_2.add(Box.createHorizontalGlue());

        Box box_for_memory_3 = Box.createHorizontalBox();
        box_for_memory_3.add(Box.createHorizontalGlue());
        box_for_memory_3.add(label_mem3);
        box_for_memory_3.add(Box.createHorizontalStrut(15));
        box_for_memory_3.add(text_mem3);
        box_for_memory_3.add(Box.createHorizontalGlue());


        //Коробки для memory информации
        Box box_for_memories = Box.createVerticalBox();
        box_for_memories.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box_for_memories.add(box_for_memory_1);
        box_for_memories.add(box_for_memory_2);
        box_for_memories.add(box_for_memory_3);

        hbox_buttons.add(Box.createHorizontalGlue());
        hbox_buttons.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        box_for_radio_buttons_memories.add(Box.createHorizontalGlue());
        hbox_buttons.add(button_MC);
        hbox_buttons.add(Box.createHorizontalStrut(30));
        hbox_buttons.add(button_MPlus);
        box_for_radio_buttons_memories.add(Box.createHorizontalGlue());

    Box contentBox = Box.createVerticalBox();
    contentBox.add(Box.createVerticalGlue());
    contentBox.add(box_for_radio_buttons);
    contentBox.add(box_variebles);
    contentBox.add(box_for_memories);
    contentBox.add(box_for_radio_buttons_memories);
    contentBox.add(boxResult);
    contentBox.add(hbox_buttons);
    contentBox.add(Box.createVerticalGlue());
    getContentPane().add(contentBox, BorderLayout.CENTER);

}
    public static void main(String[] args){
        Formula formula = new Formula();
        formula.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formula.setVisible(true);
    }
}

