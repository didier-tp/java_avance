package tp.market.mytrader;

import tp.market.core.model.Stock;
import tp.market.message.MyMessageWithLengthAndDataBuffer;
import tp.market.message.MySimpleMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

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
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataInputStream dataIn = new DataInputStream(in);
            DataOutputStream dataOut = new DataOutputStream(out);
            send_last_news_message(dataIn,dataOut);
            send_stockList_message(dataIn,dataOut);
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
    }

    public static void send_last_news_message(DataInputStream dataIn ,DataOutputStream dataOut ) {
        try {

            // - requete "last_news" et affichage réponse simple (heure courante du serveur sous forme de string)

            //send request to server:
            MySimpleMessage.writeSimpleMessage(dataOut,"last_news");
            System.out.println("lastNews request sent , waiting for response ...");
            //waiting & retreive response:
            String lastNews = MySimpleMessage.readSimpleMessage(dataIn);
            System.out.println("responseMessage (lastNews)="+lastNews);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void send_stockList_message(DataInputStream dataIn ,DataOutputStream dataOut ) {
        try {

            // - requete "stock_list" et réponse "liste d'actions (liste d'objet java)"
            MySimpleMessage.writeSimpleMessage(dataOut,"stock_list");
            System.out.println("stock_list request sent , waiting for response ...");
            //waiting & retreive response:
            String stockListMessage = MySimpleMessage.readSimpleMessage(dataIn);//lecture première partie du message (type de réponse ou message)
            System.out.println("responseMessage (stock_list)="+stockListMessage);

            //lecture des données qui accompagne le message de réponse
            List<Stock> listeActions = ( List<Stock> ) MyMessageWithLengthAndDataBuffer.readMessageDataObject(dataIn);
            System.out.println("listeActions="+listeActions.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
