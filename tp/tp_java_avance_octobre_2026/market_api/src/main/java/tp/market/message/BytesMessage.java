package tp.market.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface BytesMessage {
    public static void writeIntegerAsBytes(int value, OutputStream out){
        byte[] intValueBytes = ByteBuffer.allocate(Integer.BYTES).putInt(value).array(); // Integer.BYTES = 4
        try {
            out.write(intValueBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int readIntegerFromBytes(InputStream in){
        int value=0;
        try {
            byte[] iData = in.readNBytes(Integer.BYTES);// Integer.BYTES = 4
            if(iData==null || iData.length ==0)
                throw new EndReadLoopException();
            ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
            for(int i=0;i<4;i++)
                buffer.put(iData[i]);
            buffer.rewind();
            value = buffer.getInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
