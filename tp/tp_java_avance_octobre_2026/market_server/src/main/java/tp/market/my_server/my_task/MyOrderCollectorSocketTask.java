package tp.market.my_server.my_task;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

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
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();

            //receive simple request :
            Scanner scanner = new Scanner(in);
            String requeteSimple = scanner.nextLine();
            System.out.println("requeteSimple="+requeteSimple);

            //send simple response:
            PrintStream ps = new PrintStream(out);
            if(requeteSimple.equals("jour"))
                 ps.println("mercredi");
            else if(requeteSimple.equals("mois"))
                ps.println("octobre");
          else ps.println("????");
          //ps.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        //phase 2 du Tp
        //demande de liste d'actions , ...
    }
}
