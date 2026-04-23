package tp.market.message;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

@Getter
@Setter
public abstract class TradingMessage implements BytesMessage {
    public static final int ORDER_MESSAGE_TYPE=1;
    public static final int STOCK_LIST_REQUEST_MESSAGE_TYPE=3;
    public static final int STOCK_LIST_RESPONSE_MESSAGE_TYPE=4;
    protected Integer type; //message_type
    protected Integer size;
    protected byte[] data; //serialized java object data

    abstract public void initMessageType();

    public TradingMessage() {
        initMessageType();
    }

    public void readSizeAndDataMessageBytes(InputStream in){
        //a.read message data size:
        this.size = BytesMessage.readIntegerFromBytes(in);
        if(this.size>0) {
            //b.read message data (serialized java object):
            try {
                this.data = in.readNBytes(this.size);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeMessageBytes(OutputStream out){
        //1. write message type (ORDER_MESSAGE_TYPE):
        BytesMessage.writeIntegerAsBytes(this.getType(),out);
        //2. write data size:
        BytesMessage.writeIntegerAsBytes(this.getSize(),out);
        if(this.getSize()>0) {
            //3. write data (serialized java object):
            try {
                out.write(this.getData());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
