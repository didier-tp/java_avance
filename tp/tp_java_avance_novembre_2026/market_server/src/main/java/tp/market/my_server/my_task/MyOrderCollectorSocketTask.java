package tp.market.my_server.my_task;

import lombok.extern.slf4j.Slf4j;
import tp.market.message.MySimpleMessage;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

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

            String requestMessage = MySimpleMessage.readSimpleMessage(dataIn);

            if(requestMessage!=null)
                switch (requestMessage) {
                    case "last_news":
                        String lastNewsResponseMsg = "current time (server side) is " + LocalDateTime.now().toString();
                        MySimpleMessage.writeSimpleMessage(dataOut,lastNewsResponseMsg);
                        System.out.println("last_news response sent : "+lastNewsResponseMsg);
                        break;
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
