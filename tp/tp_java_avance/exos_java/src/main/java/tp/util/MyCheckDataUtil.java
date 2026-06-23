package tp.util;

import tp.annotations.CsvIgnore;
import tp.annotations.MyMinimum;
import tp.data.Person;
import tp.data.Product;

import java.lang.reflect.Field;

public class MyCheckDataUtil {

    //si invalid on pourra afficher un message d'erreur et/ou lever une exception
    public void checkValidObject(Object obj){
        try {
            Class c = obj.getClass();
            for(Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                Object valueOfField = f.get(obj);
                String sValueOfField = valueOfField!=null?valueOfField.toString():"null";
                MyMinimum annotMini = f.getAnnotation(MyMinimum.class);
                if(annotMini!=null){
                    int minValue = annotMini.value();
                    String message = annotMini.message();
                    int iValueOfField = Integer.parseInt(sValueOfField);
                    if(iValueOfField < minValue){
                        System.err.println("Error: " + message + " (field: " + f.getName() + ", value: " + valueOfField + ")");
                        // Optionally, throw an exception
                        throw new IllegalArgumentException(message);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        MyCheckDataUtil checkUtil = new MyCheckDataUtil();

        try {
            Person p1 = new Person(1L, "jean" , "Bon");
            p1.setEmail("jb@gmail.com");
            p1.setAge(40);
            p1.setTaille(180);
            checkUtil.checkValidObject(p1);
        } catch (Exception e) {
            System.err.println("Validation failed for p1: " + e.getMessage());
        }

        try {
            Person p2 = new Person(2L, "axelle" , "Aire");
            p2.setEmail("aa@gmail.com");
            p2.setAge(30);
            p2.setTaille(20); //invalid taille (20 < 25)
            checkUtil.checkValidObject(p2);
        } catch (Exception e) {
            System.err.println("Validation failed for p2: " + e.getMessage());
        }


    }
}
