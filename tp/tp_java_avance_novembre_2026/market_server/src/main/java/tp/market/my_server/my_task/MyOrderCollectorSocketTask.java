package tp.market.my_server.my_task;

import lombok.extern.slf4j.Slf4j;
import tp.market.core.model.MyStockMarket;
import tp.market.core.model.Stock;
import tp.market.message.EndReadLoopException;
import tp.market.message.MyMessageWithLengthAndDataBuffer;
import tp.market.message.MySimpleMessage;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);
            boolean stop=false;
           while(!stop) {
               String requestMessage = null;
               try {
                   requestMessage = MySimpleMessage.readSimpleMessage(dataIn);
               } catch (EndReadLoopException e) {
                   stop=true; continue;
               }

               if (requestMessage != null)
                   switch (requestMessage) {
                       case "last_news":
                           String lastNewsResponseMsg = "current time (server side) is " + LocalDateTime.now().toString();
                           MySimpleMessage.writeSimpleMessage(dataOut, lastNewsResponseMsg);
                           System.out.println("last_news response sent : " + lastNewsResponseMsg);
                           break;
                       case "stock_list":
                           List<Stock> listeActions = new ArrayList<>(MyStockMarket.getInstance().getStockMap().values());
                           MyMessageWithLengthAndDataBuffer.writeMessageWithSerializedData(dataOut, "stock_list_response", listeActions);
                           System.out.println("stock_list response sent : " + listeActions.toString());
                           break;
                       default:
                           MySimpleMessage.writeSimpleMessage(dataOut, "default response for unknown request ");
                   }
           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
