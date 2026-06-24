package tp.ex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BasicClientV1App {
     //filtre pour wireshark : tcp.port==9632
    static int port = 9632;
    static long totalExecTime=0L;

    public static void main(String[] args) {
        String host = (args.length>0)?args[0]:"localhost";
        InetAddress serveur = null;
        try {
            serveur = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        try  (Socket socket = new Socket(serveur, port)){
            //System.out.println("connected to serveur socket");
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
           for(int i=0;i<3;i++) {
                String isEven = isEvenRequestResponseMessage(out, in);
                System.out.println("isEven=" + isEven);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } //try with auto_closeable resource=socket
          // et coté serveur : in.readNBytes(...) retournera exceptionnelement un tableau de taille 0.
    }

    static String isEvenRequestResponseMessage(OutputStream out, InputStream in){
        String responseString = "?";
        try {
            double r = Math.random();
            int randomInt = (int) (r*1000);
            String randomIntAsString = String.valueOf(randomInt);
            System.out.println("randomIntAsString="+randomIntAsString);
            byte[] requestData = MyBytesUtil.utf8Buffer64FromLittleString(randomIntAsString);
            out.write(requestData);
            byte[] responseData = in.readNBytes(64);
            responseString= MyBytesUtil.stringFromUtf8Buffer(responseData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseString;
    }


}
