package hospital.java.sources;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Properties;

public class Auth {

    public static final String AES = "AES";

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte value : b) {
            int v = value & 0xff;
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
        String tempkey;
        Properties prop = new Properties();
        BufferedReader input;
        System.out.println();
        input = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Auth.class.getResourceAsStream("/hospital/key.properties"))));
        prop.load(input);
        tempkey = prop.getProperty("Key");
        byte[] bytekey = hexStringToByteArray(tempkey);
        SecretKeySpec sks = new SecretKeySpec(bytekey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
        byte[] encrypted = cipher.doFinal(password.getBytes());
        return byteArrayToHexString(encrypted);
    }

    private static String decrypt(String password) throws InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException, NoSuchAlgorithmException, NoSuchPaddingException {
        String tempkey;
        Properties prop = new Properties();
        BufferedReader input;
        String path = getDirPath("hospital/key.properties");
        System.out.println(path);
        input = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Auth.class.getResourceAsStream("/hospital/key.properties"))));
        prop.load(input);
        tempkey = prop.getProperty("Key");

        byte[] bytekey = hexStringToByteArray(tempkey);
        SecretKeySpec sks = new SecretKeySpec(bytekey, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] decrypted = cipher.doFinal(hexStringToByteArray(password));
        return new String(decrypted);
    }

    public static String getDirPath(String path) {
        String PROGRAM_DIRECTORY;
        try {
            PROGRAM_DIRECTORY = Objects.requireNonNull(Auth.class.getClassLoader().getResource(path)).getPath(); // Gets the path of the class or jar.
            System.out.println(PROGRAM_DIRECTORY);
            try {
                PROGRAM_DIRECTORY = PROGRAM_DIRECTORY.substring(0, PROGRAM_DIRECTORY.lastIndexOf('!'));
                System.out.println(PROGRAM_DIRECTORY);
            } catch (Exception ignored) {
            }

            PROGRAM_DIRECTORY = PROGRAM_DIRECTORY.substring(0, PROGRAM_DIRECTORY.lastIndexOf('/') + 1);
            System.out.println(PROGRAM_DIRECTORY);
            if (PROGRAM_DIRECTORY.startsWith("/")) PROGRAM_DIRECTORY = PROGRAM_DIRECTORY.substring(1);
            System.out.println(PROGRAM_DIRECTORY);
            if (PROGRAM_DIRECTORY.startsWith("file:/")) PROGRAM_DIRECTORY = PROGRAM_DIRECTORY.substring(6);

            System.out.println(PROGRAM_DIRECTORY);
        } catch (Exception e) {
            e.printStackTrace();
            PROGRAM_DIRECTORY = "";
        }
        System.out.println(PROGRAM_DIRECTORY);
        return PROGRAM_DIRECTORY + "/" + path;
    }

    public static boolean matches(String password, String encryptedPassword) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        return decrypt(encryptedPassword).equals(password);
    }

}
