package hospital.java.sources;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Auth {

    private final static String IS_LOGGED_IN = "isLoggedIn";
    private final static String EMAIL = "email";
    private final static String ID = "id";

    // public static boolean isLoggedIn() {
    //     try {
    //         if (Preferences.userRoot().nodeExists("Auth")) {
    //             Preferences prefs = Preferences.userRoot().node("Auth");
    //             return prefs.getBoolean(IS_LOGGED_IN, false);
    //         } else {
    //             return false;
    //         }
    //     } catch (BackingStoreException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    // public static void setUserData(String email, int id) {
    //     Preferences prefs = Preferences.userRoot().node("Auth");
    //     prefs.putBoolean(IS_LOGGED_IN, true);
    //     prefs.put(EMAIL, email);
    //     prefs.putInt(ID, id);

    //     Usersource.instance.setEmail(email);
    //     Usersource.instance.setId(id);
    // }

    // public static void getUserData() {
    //     try {
    //         if (Preferences.userRoot().nodeExists("Auth")) {
    //             Preferences prefs = Preferences.userRoot().node("Auth");
    //             String email = prefs.get(EMAIL, null);
    //             int id = prefs.getInt(ID, 0);
    //              Usersource.instance.setEmail(email);
    //              Usersource.instance.setId(id);
    //         }
    //     } catch (BackingStoreException e) {
    //         e.printStackTrace();
    //     }
    // }

    public static void logOut () {
        Preferences prefs = Preferences.userRoot().node("Auth");
        prefs.putBoolean(IS_LOGGED_IN, false);
        prefs.put(EMAIL, null);
        prefs.putInt(ID, 0);
    }

    public static final String AES = "AES";

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public static String encrypt(String password)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
        String tempkey = "";
        Properties prop = new Properties();
        InputStream input = null;
        System.out.println();
        input = new FileInputStream(System.getProperty("user.dir") + "/src/hospital/key.properties");
        // load a properties file
        prop.load(input);
        tempkey = prop.getProperty("Key");
        byte[] bytekey = hexStringToByteArray(tempkey);
        SecretKeySpec sks = new SecretKeySpec(bytekey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
        byte[] encrypted = cipher.doFinal(password.getBytes());
        String encryptedpwd = byteArrayToHexString(encrypted);
        // System.out.println("****************  Encrypted Password  ****************");
        // System.out.println(encryptedpwd);
        // System.out.println("****************  Encrypted Password  ****************");
        return encryptedpwd;
    }

    private static String decrypt(String password) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchPaddingException {
        String tempkey = "";
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream(System.getProperty("user.dir") + "/src/hospital/key.properties");
        // load a properties file
        prop.load(input);
        tempkey = prop.getProperty("Key");

        byte[] bytekey = hexStringToByteArray(tempkey);
        SecretKeySpec sks = new SecretKeySpec(bytekey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] decrypted = cipher.doFinal(hexStringToByteArray(password));
        String OriginalPassword = new String(decrypted);
        // System.out.println("****************  Original Password  ****************");
        // System.out.println(OriginalPassword);
        // System.out.println("****************  Original Password  ****************");
        return OriginalPassword;
    }

    public static boolean matches (String password, String encryptedPassword) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        return decrypt(encryptedPassword).equals(password);
    }

}
