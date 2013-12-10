package pt.ulisboa.tecnico.bank.security;

import org.junit.Before;
import org.junit.Test;
import org.apache.log4j.Logger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.prefs.Preferences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: SONY
 * Date: 12/9/13
 * Time: 8:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class HashUtilTest {
    private final Logger logger = Logger.getLogger(HashUtilTest.class);

    String passwordHash;
    Integer iterations = 10000;
    String _salt = "salty";

    @Before
    public void setup() {
        passwordHash = HashUtil.hashPassword("Passw0rd", _salt, iterations);
        System.out.println(passwordHash);
    }

    @Test
    public void testCorrectHashing() {
        String newpassw = new String("Passw0rd");
        byte[] salt = new byte[0];
        byte[] hash = null;

            salt = _salt.getBytes();

        try {
            hash = fromHex(passwordHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        PBEKeySpec spec = new PBEKeySpec(newpassw.toCharArray(), salt, iterations, hash.length*8);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        byte[] testHash = null;
        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            assertTrue(passwordHash.equals(toHex(testHash)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Test
    public void testIncorrectHashing() {
        String newpassw = new String("Password");
        byte[] salt = new byte[0];
        byte[] hash = null;

            salt = _salt.getBytes();


        try {
            hash = fromHex(passwordHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        PBEKeySpec spec = new PBEKeySpec(newpassw.toCharArray(), salt, iterations, hash.length*8);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        byte[] testHash = null;
        try {
            testHash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        try {
            assertFalse(passwordHash.equals(toHex(testHash)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
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
