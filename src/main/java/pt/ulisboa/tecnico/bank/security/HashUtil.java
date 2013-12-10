package pt.ulisboa.tecnico.bank.security;

import com.sun.crypto.provider.PBKDF2HmacSHA1Factory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created with IntelliJ IDEA.
 * User: SONY
 * Date: 12/9/13
 * Time: 7:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class HashUtil {

    public static String hashPassword(String password, String salt, Integer numIter) {
        char[] psswChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        PBEKeySpec spec = new PBEKeySpec(psswChars, saltBytes, numIter, 512);
        try {
            SecretKeyFactory skfactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skfactory.generateSecret(spec).getEncoded();
            return toHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
}