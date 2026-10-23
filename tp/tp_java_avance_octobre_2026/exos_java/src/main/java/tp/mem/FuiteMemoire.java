package tp.mem;

import java.util.ArrayList;
import java.util.List;

public class FuiteMemoire {
    // Static list
    private static List<Object> staticList = new ArrayList<>();
    public static void addObject(Object obj) {
        staticList.add(obj);
    }

    public static void main(String[] args) {
        memoryLeakExample();
    }

    public static void xyz(){
        byte[] obj  = new byte[1024 * 1024];
        System.out.println("taille de obj="+obj.length);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //en fin de méthode la reférence obj sera détruite (variable locale)
        //l'objet construit via new ... ne seraplus référencé et donc libéré par le GC
    }

    public static void memoryLeakExample() {
        for (int i = 0; i < 1000; i++) {
            Object obj = new byte[1024 * 1024];
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("add in memory leak ...");
            addObject(obj); // Add the object to the static list
            xyz();
        }
    }
}
