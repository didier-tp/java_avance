package tp.market.my_server.my_task;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
public class MyOrderCollectorSocketTask implements Runnable {
    private Socket socketClient;


    public MyOrderCollectorSocketTask(Socket socketClient){
        this.socketClient=socketClient;
    }
    @Override
    public void run() {
       // à coder librement en Tp
        System.out.println("MyOrderCollectorSocketTask.run");

        //phase 1 du Tp :
        //recevoir requete ultra simple "jour" ou "mois"
        //et répondre  "mercredi" ou "octobre"

        //phase 2 du Tp
        //demande de liste d'actions , ...
    }
}
