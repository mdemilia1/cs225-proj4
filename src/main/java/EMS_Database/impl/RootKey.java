package EMS_Database.impl;

import EMS_Database.DoesNotExistException;
import EMS_Database.InitDB;
import static EMS_Database.InitDB.debugLog;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mike
 */
public class RootKey extends InitDB {

    private String tableName = "ROOTKEY";

    /**
     * This function is designed to store the private RSA mod/exp key pair.
     * @param mod the modulus value to be stored
     * @param exp the exponent value to be stored
     */
    public void addPrivKey(BigInteger mod, BigInteger exp) {
	BigDecimal dmod = new BigDecimal(mod);
	BigDecimal dexp = new BigDecimal(exp);
	try {
	    //Creating Statement
	    PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?)");
	    AddAddressStmt.setInt(1, 1);
	    AddAddressStmt.setLong(2, dmod.longValue());
	    AddAddressStmt.setLong(3, dexp.longValue());
	    //Execute Statement
	    AddAddressStmt.executeUpdate();
	} catch (SQLException sqle) {
	    debugLog.severe("Private RSA key could not be stored.");
	    System.err.println("Seriously dude? check debug log");
	}
    }

    /**
     * This function is designed to store the public RSA mod/exp key pair.
     * @param mod the modulus value to be stored
     * @param exp the exponent value to be stored
     */
    public void addPubKey(BigInteger mod, BigInteger exp) {
	BigDecimal dmod = new BigDecimal(mod);
	BigDecimal dexp = new BigDecimal(exp);
	try {
	    //Creating Statement
	    PreparedStatement AddAddressStmt = dbConnection.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?)");
	    AddAddressStmt.setInt(1, 2);
	    AddAddressStmt.setLong(2, dmod.longValue());
	    AddAddressStmt.setLong(3, dexp.longValue());
	    //Execute Statement
	    AddAddressStmt.executeUpdate();
	} catch (SQLException sqle) {
	    debugLog.severe("Public RSA key could not be stored.");
	    System.err.println("Seriously dude? check debug log");
	}
    }

    /**
     * A function to erase the currently stored private key.
     */
    public void removePrivKey() {
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("DELETE FROM " + tableName + " WHERE UID=?");
	    idQueryStmt.setInt(1, 1);
	    idQueryStmt.executeUpdate();

	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage());
	}
    }

    /**
     * A function to erase the currently stored private key.
     */
    public void removePubKey() {
	try {
	    PreparedStatement idQueryStmt = dbConnection.prepareStatement("DELETE FROM " + tableName + " WHERE UID=?");
	    idQueryStmt.setInt(1, 2);
	    idQueryStmt.executeUpdate();

	} catch (SQLException sqle) {
	    System.err.println(sqle.getMessage());
	}
    }

    /**
     * Also pretty self explanitory,
     *
     * @return the stored key as a BIGINT.
     */    
    public BigInteger getPrivMod() throws DoesNotExistException {
	try {
	    return getDBBigInt("MOD", tableName, 1);
	} catch (DoesNotExistException dnee) {
	    throw new DoesNotExistException("Private RSA mod key error. HOLY FUCKBALLS! " + dnee.getMessage());
	}
    }

    public BigInteger getPrivExp() throws DoesNotExistException {
	try {
	    return getDBBigInt("EXP", tableName, 1);
	} catch (DoesNotExistException dnee) {
	    throw new DoesNotExistException("Private RSA exp key error. HOLY FUCKBALLS! " + dnee.getMessage());
	}
    }

    public BigInteger getPubMod() throws DoesNotExistException {
	try {
	    return getDBBigInt("MOD", tableName, 2);
	} catch (DoesNotExistException dnee) {
	    throw new DoesNotExistException("Public RSA mod key error. HOLY FUCKBALLS! " + dnee.getMessage());
	}
    }

    public BigInteger getPubExp() throws DoesNotExistException {
	try {
	    return getDBBigInt("EXP", tableName, 2);
	} catch (DoesNotExistException dnee) {
	    throw new DoesNotExistException("Public RSA exp key error. HOLY FUCKBALLS! " + dnee.getMessage());
	}
    }
}
