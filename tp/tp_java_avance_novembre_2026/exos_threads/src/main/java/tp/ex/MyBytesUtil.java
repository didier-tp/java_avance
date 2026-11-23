package tp.ex;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MyBytesUtil {

    //transform little string (<=64 char) as utf8 bytes array of fixed size=64
    public static byte[] utf8Buffer64FromLittleString(String s){
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        //System.out.println("initial length:" + bytes.length);
        byte[] expanded = Arrays.copyOf(bytes, 64); //fill last chars by \0
        //System.out.println("expanded length:" + expanded.length);
        return expanded;
    }

    //transform utf8 bytes array of fixed size=64 or ... to little String
    public static String stringFromUtf8Buffer(byte[] bytes){
        String s = new String(bytes, StandardCharsets.UTF_8);
        //System.out.println("s="+s);
        int firstNullPos = s.indexOf('\0');
        s=s.substring(0,firstNullPos); //remove null chars at end of string
        //System.out.println("s="+s);
        return s;
    }

}
