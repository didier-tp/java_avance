package tp.market.my_server.my_task;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.message.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

        /*
        //phase 1 du Tp :
        //recevoir requete ultra simple "jour" ou "mois"
        //et répondre  "mercredi" ou "octobre" ou ...
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);

            //receive simple request :
            String requestMessage = null;
            try {
                requestMessage = MySimpleMessage.readSimpleMessage(dataIn);
            } catch (EndReadLoopException e) {
                //endLoop=true;
            }

            //send simple response:
            String responseMsg = "???";
            if(requestMessage.equals("jour"))
                responseMsg = "mercredi ou autre";
            else if(requestMessage.equals("mois"))
                responseMsg = "novembre ou autre";

            MySimpleMessage.writeSimpleMessage(dataOut,responseMsg);
            System.out.println("response sent : "+responseMsg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/


        //phase 2 du Tp
        //demande de liste d'actions , ...
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);

            //receive simple request :
            String requestMessage = null;
            try {
                requestMessage = MySimpleMessage.readSimpleMessage(dataIn);
            } catch (EndReadLoopException e) {
                //endLoop=true;
            }
            switch (requestMessage){
                case MyTradingMessage.STOCK_LIST_REQUEST_MESSAGE :
                    List<Stock> listeActions = new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
                    MyMessageWithLengthAndDataBuffer.writeMessageWithSerializedData(dataOut,
                            MyTradingMessage.STOCK_LIST_RESPONSE_MESSAGE,
                            listeActions);
                    System.out.println("STOCK_LIST_RESPONSE_MESSAGE sent with nbActions="+listeActions.size());
                    break;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
