package tp.market.v2.task;

import java.net.ServerSocket;
import java.net.Socket;

public class OrderCollectorTaskSocketV2 implements Runnable{

    static int port =9632; //par défaut

    private boolean stop=false;
    private String traderId;

    public OrderCollectorTaskSocketV2(String traderId) {
        this.traderId = traderId;
    }

    public OrderCollectorTaskSocketV2(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {

        try {
            ServerSocket socketServeur = new ServerSocket(port);
            System.out.println("initialisation du serveur , Socket  port="+port);
            while (true) {
                Socket socketClient = socketServeur.accept();
                Thread t = new Thread(new OrderCollectorSocketTask(socketClient));
                t.start();
            }
        } catch (Exception e) {  e.printStackTrace();        }


    }
}

