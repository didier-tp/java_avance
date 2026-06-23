package tp.ex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BasicClientV2App {

    static int port = 9632;
    static long totalExecTime=0L;
    static int nbEven=0;
    static int nbOdd=0;

    public static void main(String[] args) {
        String host = (args.length>0)?args[0]:"localhost";
        InetAddress serveur = null;
        try {
            serveur = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        final InetAddress fServeur = serveur;
        for(int n=0;n<1500;n++) {
            //1500 clients en parallèle qui se connectent au même serveur:
            /*Thread t = new Thread(()->aSocketClientConnectedToServer(fServeur, port));
            t.start();*/
            Thread.ofVirtual().start(()->aSocketClientConnectedToServer(fServeur, port));
            try { Thread.sleep(5);  } catch (InterruptedException e) {  throw new RuntimeException(e); }
        }
        try {
            Thread.sleep(3000 * 4); //attente avant d'afficher totalExecTime
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("nbEven="+nbEven + " nbOdd="+nbOdd);
        System.out.println("totalExecTime (ms)="+totalExecTime / 1000000);
        //ex:  1738 , 1445 , 1342 avec virtualThread coté serveur
        //ex:  2267 , 2119 , 2076 (ms) sans virtualThread coté serveur
    }

    public static void aSocketClientConnectedToServer(InetAddress serveur , int port) {
        try  (Socket socket = new Socket(serveur, port)){
            //System.out.println("connected to serveur socket");
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            for(int i=0;i<3;i++) {
                String isEven = isEvenRequestResponseMessage(out, in);
                //System.out.println("isEven=" + isEven);
                if(isEven.equals("even"))
                    nbEven++;
                else
                     nbOdd++;
                Thread.sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket .
        // et coté serveur : in.readNBytes(...) retournera exceptionnelement un tableau de taille 0.
    }

    static String isEvenRequestResponseMessage(OutputStream out, InputStream in){
        String responseString = "?";
        try {
            double r = Math.random();
            int randomInt = (int) (r*1000);
            String randomIntAsString = String.valueOf(randomInt);
            //System.out.println("randomIntAsString="+randomIntAsString);
            byte[] requestData = MyBytesUtil.utf8Buffer64FromLittleString(randomIntAsString);
            long startTime = System.nanoTime();
            out.write(requestData);
            byte[] responseData = in.readNBytes(64);
            responseString= MyBytesUtil.stringFromUtf8Buffer(responseData);
            long endTime = System.nanoTime();
            totalExecTime += (endTime - startTime);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseString;
    }


}
