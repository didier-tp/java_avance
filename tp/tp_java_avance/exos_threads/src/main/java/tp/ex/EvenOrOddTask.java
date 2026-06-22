package tp.ex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EvenOrOddTask  implements Runnable{
    private Socket socketClient;
    private boolean stop=false;
    private static int threadCounter =0;
    public EvenOrOddTask(Socket socketClient){
        this.socketClient=socketClient;
    }

    @Override
    public void run() {
        threadCounter++;
        System.out.println("EvenOrOddTask.run() with threadCounter="+threadCounter);
        try {
            InputStream in = socketClient.getInputStream();
            OutputStream out = socketClient.getOutputStream();
            int n = 0;
            while (!stop) {
                if (socketClient.isClosed()) {
                    //System.out.println("socketClient is closed , stop");
                    stop = true;
                }
                else {
                    try {
                        byte[] requestData = in.readNBytes(64);
                        String requestString = MyBytesUtil.stringFromUtf8Buffer(requestData);
                        int value = Integer.parseInt(requestString);
                        //System.out.println("incomming requestString=" + requestString + " value=" + value);
                        String responseString = (value % 2 == 0) ? "even" : "odd";
                        //System.out.println("responseString=" + responseString);
                        byte[] responseData = MyBytesUtil.utf8Buffer64FromLittleString(responseString);
                        out.write(responseData);
                    } catch (Exception e) {
                        stop=true;
                    }
                }
            }
            threadCounter--;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
