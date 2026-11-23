package tp.ex;

import java.net.ServerSocket;
import java.net.Socket;

public class BasicServerV1App {
    static int port =9632; //par défaut
    private static boolean stop=false;

    public static void main(String[] args) {
        try {
            ServerSocket socketServeur = new ServerSocket(port);
            System.out.println("initialisation du serveur , Socket  port="+port );
            while (!stop) {
                Socket socketClient = socketServeur.accept();
                Thread t = new Thread(new EvenOrOddV1Task(socketClient));
                t.start();
            }
        } catch (Exception e) {  e.printStackTrace();        }
    }


}
