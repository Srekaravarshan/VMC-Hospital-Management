package hospital.java.helpers;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;

public class MapString {
    public static String serialize(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static HashMap<String, String> deserialize(String s) throws IOException,
            ClassNotFoundException {
        if (s==null || s.isEmpty()) {
            return new HashMap<>();
        }
        byte[] data = Base64.getDecoder().decode((s));
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        HashMap<String, String> o = (HashMap<String, String>) ois.readObject();
        ois.close();
        return o;
    }
}
