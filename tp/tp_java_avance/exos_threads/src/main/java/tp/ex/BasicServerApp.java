package tp.ex;

import java.net.ServerSocket;
import java.net.Socket;

public class BasicServerApp {
    static int port =9632; //par défaut
    private static boolean stop=false;
    //private static boolean withVirtualThread=false;
    private static boolean withVirtualThread=true;

    public static void main(String[] args) {
        try {
            ServerSocket socketServeur = new ServerSocket(port);
            System.out.println("initialisation du serveur , Socket  port="+port + " withVirtualThread=" + withVirtualThread);
            while (!stop) {
                try(Socket socketClient = socketServeur.accept()) {
                    if (withVirtualThread) {
                        Thread.ofVirtual().start(new EvenOrOddTask(socketClient));
                    } else {
                        Thread t = new Thread(new EvenOrOddTask(socketClient));
                        t.start();
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }//automatic close of socketClient
            }
        } catch (Exception e) {  e.printStackTrace();        }
    }


}
