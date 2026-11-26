package tp.mem;

public class BigObject {
    private byte[] data  = new byte[1024 * 1024];

    public int length(){
        return data.length;
    }
}
