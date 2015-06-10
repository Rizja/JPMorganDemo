package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.Key;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class EncryptedInteger implements UserType {

    protected static final Key KEY = new Key(System.getProperty("master_key").getBytes(Charset.forName("UTF-8")));
    protected static final byte[] TWEAK = new byte[0];
    private FPECipher<BigInteger> encryption;

    public EncryptedInteger(FPECipher<BigInteger> encryption) {
        this.encryption = encryption;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor spi, Object owner) throws SQLException {
        BigInteger value = BigInteger.valueOf(rs.getLong(names[0]));
        if (value == null) { // if NULL in database return null
            return null;
        }
        int encColumnIndex; // find column 'encrypted' from result set
        for (encColumnIndex=1;encColumnIndex<=rs.getMetaData().getColumnCount();encColumnIndex++) {
            if (rs.getMetaData().getColumnName(encColumnIndex).equals("encrypted")) break;
            else if (encColumnIndex==rs.getMetaData().getColumnCount()) throw new RuntimeException("Encrypted field in entity missing.");
        }
        if (rs.getBoolean(encColumnIndex)) value = decrypt(value); // only decrypt if 'encrypted' flag is set
        return value;
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor spi) throws SQLException {
        if (value != null) {
            BigInteger encrypted = encrypt((BigInteger)value);
            st.setLong(index, encrypted.longValue());
        } else {
            st.setNull(index, Types.VARCHAR); //if NULL in Java set to NULL in database
        }
    }

    public Class<BigInteger> returnedClass() {
        return BigInteger.class;
    }

    public int[] sqlTypes() {
        return new int[]{Types.BIGINT};
    }

    public Object assemble(Serializable cached, Object owner) {
        return cached.toString();
    }

    public Object deepCopy(Object value) {
        return new BigInteger(value.toString());
    }

    public Serializable disassemble(Object value) {
        return value.toString();
    }

    public boolean equals(Object x, Object y) {
        return (x.equals(y));
    }

    public int hashCode(Object x) {
        return x.hashCode();
    }

    public boolean isMutable() {
        return true;
    }

    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    private BigInteger encrypt(BigInteger value) {
        return encryption.encrypt(value, KEY, TWEAK);
    }

    private BigInteger decrypt(BigInteger value) {
        return encryption.decrypt(value, KEY, TWEAK);
    }
}