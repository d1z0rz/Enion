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

public class Krüpteerija {
    //all operation with file ecnryption/dekrüpteerimine

    private static void failiProtsessor(int cipherMode, String võti, File sisendFail, File väljundFail) {
        //main encrpytion/decrypton process
        try {
            Key salaVõti = new SecretKeySpec(võti.getBytes(), "AES");
            Cipher siffer = Cipher.getInstance("AES");
            siffer.init(cipherMode, salaVõti);

            FileInputStream sisendVoog = new FileInputStream(sisendFail);
            byte[] sisendBaidid = new byte[(int) sisendFail.length()];
            sisendVoog.read(sisendBaidid);

            byte[] väljundBaidid = siffer.doFinal(sisendBaidid);

            FileOutputStream väljundVoog = new FileOutputStream(väljundFail);
            väljundVoog.write(väljundBaidid);

            sisendVoog.close();
            väljundVoog.close();
            ParooliViga.passwordState = 0;

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            ParooliViga.passwordState = 1;
        }
    }

    private static void krüpteerimine(File sisendFail, File krüpteeritudFail, String võti) {
        //save encrypted file
        failiProtsessor(Cipher.ENCRYPT_MODE, võti, sisendFail, krüpteeritudFail);
        System.out.println("File encrypted");
    }

    private static void dekrüpteerimine(File sisendFail, File dekrüpteeritudFail, String võti) {
        //save decrypted file
        failiProtsessor(Cipher.DECRYPT_MODE, võti, sisendFail, dekrüpteeritudFail);
        System.out.println("File decrypted");
    }

    private static String eemaldaLaiend(String sõne) {
        //function, that handle the path and strip file extention
        if (sõne == null) return null;
        int positsioon = sõne.lastIndexOf(".");
        if (positsioon == -1) return sõne;
        return sõne.substring(0, positsioon);
    }

    public static void tegevusFailiga(File sisendFail, String tegevus, String parool) throws IOException, InvalidKeyException {
        //actions with income file
        String failiAsukoht, failiAsukohtFormateeritud;
        File krüpteeritudFail, dekrüpteeritudFail;
        if (tegevus.equals("krüpteerimine")) {
            failiAsukoht = sisendFail.getAbsolutePath();
            failiAsukohtFormateeritud = failiAsukoht + ".secured";
            krüpteeritudFail = new File(failiAsukohtFormateeritud);
            Krüpteerija.krüpteerimine(sisendFail, krüpteeritudFail, parool);
        } else if (tegevus.equals("dekrüpteerimine")) {
            failiAsukoht = sisendFail.getAbsolutePath();
            failiAsukohtFormateeritud = eemaldaLaiend(failiAsukoht);
            dekrüpteeritudFail = new File(failiAsukohtFormateeritud);
            Krüpteerija.dekrüpteerimine(sisendFail, dekrüpteeritudFail, parool);
        }
    }
/**
    public static void main(String[] args) {
        String key = "This is a secret";
        String falseKey = "S0baka";
        String shaKey = String.valueOf(falseKey.hashCode());
        File inputFile = new File("/Users/k01/Downloads/Safenet-Luna-Network-HSM7.png");
        File encryptedFile = new File("test.txt.secured");
        File decryptedFile = new File("decrypted-text.txt");
        File encFile = new File("test.txt.secured");

        try {
            //Krüpteerija.tegevusFailiga(inputFile,"krüpteerimine",key);
            //Krüpteerija.tegevusFailiga(encryptedFile, "dekrüpteerimine", key);
            System.out.println("Sucess");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}
