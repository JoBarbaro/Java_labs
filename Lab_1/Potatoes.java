package bsu.rfe.java.group5.lab_1.Matsuk_Vladislav.varB4;

import java.util.Objects;

public  class Potatoes extends Food {
    private String type;
    public void consume() {
        System.out.println(this.name + " " +  this.type +  " съедена" );
    }
    public Potatoes(String type) {
        super("Картошка");
        this.type = type;
    }
    @Override
    public void print_name(){
        System.out.println((this.name) + " " + this.type);
    }
    public String getType(){
        return type;
    }
    public void setType( String type) {
        this.type = type;
    }

    public boolean equals(Object arg0){
        if(super.equals(arg0)){
            if(!(arg0 instanceof Potatoes)) return false;
            return type.equals(((Potatoes)arg0).type);
        }
        else return false;
    }
    @Override
    public int calculateCalories(){
        int calories=0;
        if(Objects.equals(this.type, " Жареная")){
            calories+=200;
        }
        else if(Objects.equals(this.type,"Вареная")) {
            calories += 100;
        }
        else if(Objects.equals(this.type,"Фри")) {
            calories += 500;
        }
        return calories;
    }



    public String toString(){
        return super.toString()+ "типа " + type.toUpperCase() + "-";
    }
}
