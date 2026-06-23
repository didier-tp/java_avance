package tp.ex;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EvenOrOddV1Task implements Runnable{
    private Socket socketClient;
    private boolean stop=false;
    public EvenOrOddV1Task(Socket socketClient){
        this.socketClient=socketClient;
    }
    @Override
    public void run() {
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();
            while (!stop) {
                byte[] requestData = in.readNBytes(64);
                //NB: in.readNBytes() renvoi un tableau de taille 0 si le flux est fermé par le client,
                // donc on peut utiliser cette condition pour arrêter la boucle.
                if(requestData.length==0){
                    System.out.println("requestData.length==0 , stop");
                    stop=true;   continue;
                }
                String requestString = MyBytesUtil.stringFromUtf8Buffer(requestData);
                int value = Integer.parseInt(requestString);
                System.out.println("incomming requestString=" + requestString + " value=" + value);
                String responseString = (value % 2 == 0) ? "even" : "odd";
                byte[] responseData = MyBytesUtil.utf8Buffer64FromLittleString(responseString);
                out.write(responseData);
                System.out.println("responseString=" + responseString + " sent to client");
                }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
