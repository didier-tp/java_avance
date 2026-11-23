package tp.market.exec;

/*
Les tâches exécutées par certrains threads vont au sein de boucles internes
régulièrement vérifier MarketTaskControl.isStop() et s'arrêter lorsque isStop() retournera true
----
pour contrôler l'arrêt progressif de tous les threads il suffira d'appeler MarketTaskControl.setStop(true);
 */


public class MarketTaskControl {
    private  static boolean stop=false;

    public static synchronized boolean isStop() {
        return stop;
    }

    public static synchronized void setStop(boolean stop) {
        MarketTaskControl.stop = stop;
    }
}
