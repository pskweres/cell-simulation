package pdd.util;

import java.util.*;
import java.io.*;

public class Util {
    
    public static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray()); 
    }

    public static Object fromString(String s) throws IOException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o;
        try {
            o = ois.readObject();
        } catch (ClassNotFoundException ex) {
            o = null;
        }
        ois.close();
        return o;
    }

}