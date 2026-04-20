package tp.market.my_v2.my_task;

import java.net.ServerSocket;
import java.net.Socket;

public class MyOrderCollectorTaskSocketV2 implements Runnable{

    static int port =9632; //par défaut

    private boolean stop=false;
    private String traderId;

    public MyOrderCollectorTaskSocketV2(String traderId) {
        this.traderId = traderId;
    }

    public MyOrderCollectorTaskSocketV2(){
        this("_trader_"+Thread.currentThread().getName());
    }

    @Override
    public void run() {

        try {
            ServerSocket socketServeur = new ServerSocket(port);
            System.out.println("initialisation du serveur , Socket  port="+port);
            while (true) {
                Socket socketClient = socketServeur.accept();
                Thread t = new Thread(new MyOrderCollectorSocketTask(socketClient));
                t.start();
            }
        } catch (Exception e) {  e.printStackTrace();        }


    }
}

