package tp.market.mytrader;

import tp.market.message.StockListRequestMessage;
import tp.market.message.StockListResponseMessage;
import tp.market.message.TradingMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

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
            //send_jour_message(socket);
            send_stockList_message(socket);
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
    }

        //phase 1 du Tp :
        //envoyer requete ultra simple "jour" ou "mois"
        //et afficher réponse simple du serveur "mercredi" ou "octobre"

        public static void send_jour_message(Socket socket){
            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                //send request to server:
                PrintStream ps = new PrintStream(out);
                ps.println("jour");
                //ps.flush();
                System.out.println("request 'jour' was sent ");
                //waiting & retreive response:
               Scanner scanner = new Scanner(in);
               String reponseSimple = scanner.nextLine();
                System.out.println("reponseSimple="+reponseSimple);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        //phase 2 du Tp
        //demande de liste d'actions , ...
        public static void send_stockList_message(Socket socket){
            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                //send request to server:

                StockListRequestMessage requeteMessage = new StockListRequestMessage();
                requeteMessage.writeMessageBytes(out);

                //Recuperer la réponse (liste d'actions)
                StockListResponseMessage responseMessage = new StockListResponseMessage();
                responseMessage.getStockList();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
