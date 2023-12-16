package bsu.rfe.java.group5.lab_1.Matsuk_Vladislav.varB4;

public class Cheese extends Food{
    public Cheese(){
        super("Сыр");
    }
    public void print_name(){
        System.out.println((this.name));
    }
    public void consume(){
        System.out.println((this.name + " съеден"));
    }
    public int calculateCalories(){
        return 150;
    }
}
