package bsu.rfe.java.group5.lab_1.Matsuk_Vladislav.varB4;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
public class Main {
    public static int equalsFood(Food obj1, Food[] breakfast){
        int count = 0;
        for(Food item : breakfast){
            if (item == null)
                break;
            else if (item.equals(obj1))
                count++;
        }
        return count;
    }
    public static void main(String[] args) {
        Food[] breakfast = new Food[20];
        boolean sortFlag = false, caloriesFlag = false;
        int items = 0;
        for (String arg : args) {
            if (arg.equals("-sort")) {
                sortFlag = true;
                continue;
            }
            if (arg.equals("-calories")) {
                caloriesFlag = true;
                continue;
            }
            else{
                String[] parts = arg.split("/");
                if (parts[0].equals("Сыр")) {
                    breakfast[items] = new Cheese();
                }
                if (parts[0].equals("Картошка")) {
                    breakfast[items] = new Potatoes(parts[1]);
                }
                if (!parts[0].equals("Сыр") && !parts[0].equals("Картошка"))
                    continue;
                items++;
            }
        }
        for (Food item : breakfast) {
            if (item == null)
                break;
            else
                item.print_name();
        }
        //
        System.out.println(equalsFood(breakfast[0], breakfast));
       if (caloriesFlag) {
            int sum = 0;
            for (Food item : breakfast) {
                if (item != null) {
                    sum += item.calculateCalories();
                } else
                    break;
            }
           System.out.println("Калорий в завтраке = " + sum);
        }


        for (Food item : breakfast)
            if (item != null)
                item.consume();
            else
                break;
        System.out.println("Удачи");
        if (sortFlag) {
            Arrays.sort(breakfast, new Comparator() {
                public int compare(Object f1, Object f2) {
                    if (f1 == null) return 1;
                    if (f2 == null) return -1;
                    if (((Food)f1).getName().equals("Картошка")  && ((Food)f2).getName().equals("Картошка"))
                    {
                        char[] arr1 = (((Food) f1).getType()).toString().toCharArray();
                        char[] arr2 = (((Food) f2).getType()).toString().toCharArray();
                        return arr1[0] - arr2[0];
                    }
                    return 1;
                }
            });
        }
      
        for (Food item : breakfast) {
            if (item == null)
                break;
            else
                item.print_name();
        }
    }

}