package tp.ex;

import java.net.ServerSocket;
import java.net.Socket;

public class BasicServerV2App {
    static int port =9632; //par défaut
    private static boolean stop=false;
    //private static boolean withVirtualThread=false;
    private static boolean withVirtualThread=true;

    public static void main(String[] args) {
        try {
            ServerSocket socketServeur = new ServerSocket(port);
            System.out.println("initialisation du serveur , Socket  port="+port + " withVirtualThread=" + withVirtualThread);
            while (!stop) {
                Socket socketClient = socketServeur.accept();
                if (withVirtualThread) {
                        Thread.ofVirtual().start(new EvenOrOddV2Task(socketClient));
                } else {
                        Thread t = new Thread(new EvenOrOddV2Task(socketClient));
                        t.start();
                }
            }
        } catch (Exception e) {  e.printStackTrace();        }
    }
}
