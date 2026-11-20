package tp.market.mytrader;

import tp.market.core.model.Stock;
import tp.market.message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class MyTraderSocketClient {

    static int port = 9632;

    public static void main(String[] args) {
        System.out.println("MyTraderSocketClient");
        // à facultativement coder en TP en toute liberté

        String host = (args.length > 0) ? args[0] : "localhost";
        InetAddress serveur = null;
        try {
            serveur = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try (Socket socket = new Socket(serveur, port)) {
            InputStream in = socket.getInputStream();
            DataInputStream dataIn = new DataInputStream(in);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);
            //send_jour_message(dataIn,dataOut);
            send_stockList_message(dataIn,dataOut);
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
    }

        //phase 1 du Tp :
        //envoyer requete ultra simple "jour" ou "mois"
        //et afficher réponse simple du serveur "mercredi" ou "octobre"

        public static void send_jour_message(DataInputStream dataIn, DataOutputStream dataOut){
            //send request to server:
            MySimpleMessage.writeSimpleMessage(dataOut,"jour");
            System.out.println("jour request sent , waiting for response ...");
            //waiting & retreive response:
            String jourResponse = MySimpleMessage.readSimpleMessage(dataIn);
            System.out.println("responseMessage (jour)="+jourResponse);
        }

        //phase 2 du Tp
        //demande de liste d'actions , ...
        public static void send_stockList_message(DataInputStream dataIn, DataOutputStream dataOut){
            try {
                //send request to server:
                long td =System.nanoTime();

                MySimpleMessage.writeSimpleMessage(dataOut,MyTradingMessage.STOCK_LIST_REQUEST_MESSAGE);
                System.out.println("stockList request sent , waiting for response ...");

                //Recuperer la réponse (liste d'actions)
                String responseMessageType=MySimpleMessage.readSimpleMessage(dataIn);
                System.out.println("received response type:" + responseMessageType);
                //byte[] data = MyMessageWithLengthAndDataBuffer.readMessageData(dataIn);
                //List<Stock> listeActions = (List<Stock>) MySerializableUtil.deserializeData(data);
                List<Stock> listeActions = (List<Stock>)  MyMessageWithLengthAndDataBuffer.readMessageDataObject(dataIn);
                long tf =System.nanoTime();
                long tempReponse = (tf - td);
                System.out.println("tempReponse (via socket) en ns : " + tempReponse);
                System.out.println("tempReponse (via socket) en ms : " + tempReponse/ 1000000);
                System.out.println("listeActions="+listeActions);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

}
