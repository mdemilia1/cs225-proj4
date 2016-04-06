/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd.ManagerSystem;

//import EMS_Database.impl.RootKey;
import EMS_Database.impl.RootKey;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 *
 * @author David Tersoff
 */
public class KeyThing {

    public static void main(String args[]) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.genKeyPair();
        Key publicKey = kp.getPublic();
        Key privateKey = kp.getPrivate();

        KeyFactory fact = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pub = fact.getKeySpec(kp.getPublic(),
                RSAPublicKeySpec.class);
        RSAPrivateKeySpec priv = fact.getKeySpec(kp.getPrivate(),
                RSAPrivateKeySpec.class);
        
        RootKey root = new RootKey();
        
        
        root.addPubKey(pub.getModulus(), pub.getPublicExponent());
        root.addPrivKey(priv.getModulus(), priv.getPrivateExponent());
    }
}
