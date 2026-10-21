package tp.market.my_server.my_task;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.message.BytesMessage;
import tp.market.message.StockListRequestMessage;
import tp.market.message.StockListResponseMessage;
import tp.market.message.TradingMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
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
*/


        //phase 2 du Tp
        //demande de liste d'actions , ...
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();

            //receive  request :
            int messageType = BytesMessage.readIntegerFromBytes(in);
            System.out.println("messageType="+messageType);
            switch (messageType){
                case TradingMessage.STOCK_LIST_REQUEST_MESSAGE_TYPE :
                    StockListRequestMessage stockListRequestMessage = new StockListRequestMessage();
                    stockListRequestMessage.readSizeAndDataMessageBytes(in);

                    List<Stock> listeActions = new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
                    StockListResponseMessage stockListResponseMessage = new StockListResponseMessage(listeActions);
                    stockListResponseMessage.writeMessageBytes(out);
                    break;
            }
            //send  response:


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
