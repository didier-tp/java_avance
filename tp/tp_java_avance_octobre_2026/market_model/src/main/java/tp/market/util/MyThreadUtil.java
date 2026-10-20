package tp.market.util;

public class MyThreadUtil {
    public static void pause(long delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
