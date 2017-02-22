package pdd.util;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

public class Util {

    public static String arrayToString(Serializable[] array) {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (int i = 0; i < array.length; i++) {
            try {
                sb.append(delimiter);
                sb.append(toString(array[i]));
                delimiter = "#";
            } catch (IOException ex) {
                System.out.println(ex);
                sb.append(delimiter);
                delimiter = "#";                             
            }
        }
        return sb.toString();
    }

    public static Object[] arrayFromString(String s) {
        String[] parts = s.split("#");
        Object[] objects = new Object[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                objects[i] = fromString(parts[i]);
            } catch (IOException ex) {
                System.out.println(ex);
                objects[i] = null;
            }
        }
        return objects;
    }
    
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