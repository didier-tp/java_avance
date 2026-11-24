package tp.market.mytrader;

import tp.market.message.MySimpleMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyTraderSocketClient {
    static int port = 9632;

    public static void main(String[] args) {
        System.out.println("MyTraderSocketClient");
        // à facultativement coder en TP en toute liberté (pages 88 et 94)
        String host = (args.length>0)?args[0]:"localhost";
        InetAddress serveur = null;
        try {
            serveur = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try (Socket socket = new Socket(serveur, port)){
            send_stockList_message(socket);
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
    }

    public static void send_stockList_message(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);

            //v1 préliminaire :
            // - requete "last_news" et affichage réponse simple (heure courante du serveur sous forme de string)

            //send request to server:
            MySimpleMessage.writeSimpleMessage(dataOut,"last_news");
            System.out.println("lastNews request sent , waiting for response ...");
            //waiting & retreive response:
            String lastNews = MySimpleMessage.readSimpleMessage(dataIn);
            System.out.println("responseMessage (lastNews)="+lastNews);


            //V2: vers 14h30:
            // - requete "stock_list" et réponse "liste d'actions (liste d'objet java)"
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
