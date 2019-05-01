import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    //all operation with file ecnryption/decryption

    static void fileProcessor(int cipherMode, String key, File inputFile, File outputFile) {
        //main encrpytion/decrypton process
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            PasswordError.passwordState = 0;

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            System.out.println("Exeption Wrong Password");
            PasswordError.passwordState = 1;
        }
    }

    private static void encryption(File inputFile, File encryptedFile, String key) {
        //save encrypted file
        fileProcessor(Cipher.ENCRYPT_MODE, key, inputFile, encryptedFile);
        System.out.println("File encrypted");
    }

    private static void decryption(File inputFile, File decryptedFile, String key) {
        //save decrypted file
        fileProcessor(Cipher.DECRYPT_MODE, key, inputFile, decryptedFile);
        System.out.println("File decrypted");
    }

    private static String stripExtension(String str) {
        //function, that handle the path and strip file extention
        if (str == null) return null;
        int pos = str.lastIndexOf(".");
        if (pos == -1) return str;
        return str.substring(0, pos);
    }

    public static void actionWithFile(File inputFile, String action, String password) throws IOException, InvalidKeyException {
        //actions with income file
        String FilePath, FilePathFormatted;
        File encryptedFile, decryptedFile;
        if (action.equals("encryption")) {
            FilePath = inputFile.getAbsolutePath();
            FilePathFormatted = FilePath + ".secured";
            encryptedFile = new File(FilePathFormatted);
            System.out.println(encryptedFile.getPath());
            System.out.println(inputFile.getAbsolutePath());
            Crypto.encryption(inputFile, encryptedFile, password);
        } else if (action.equals("decryption")) {
            FilePath = inputFile.getAbsolutePath();
            FilePathFormatted = stripExtension(FilePath);
            decryptedFile = new File(FilePathFormatted);
            Crypto.decryption(inputFile, decryptedFile, password);
        }
    }

    public static void main(String[] args) {
        String key = "This is a secret";
        String falseKey = "S0baka";
        String shaKey = String.valueOf(falseKey.hashCode());
        File inputFile = new File("/Users/k01/Downloads/Safenet-Luna-Network-HSM7.png");
        File encryptedFile = new File("test.txt.secured");
        File decryptedFile = new File("decrypted-text.txt");
        File encFile = new File("test.txt.secured");

        try {
            //Crypto.actionWithFile(inputFile,"encryption",key);
            Crypto.actionWithFile(encryptedFile, "decryption", key);
            System.out.println("Sucess");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
