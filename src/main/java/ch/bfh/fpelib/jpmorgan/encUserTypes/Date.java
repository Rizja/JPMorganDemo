package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.messageSpace.StringMessageSpace;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Date extends EncryptedString {

    private static final String REGEXP = "((((19|20)([2468][048]|[13579][26]|0[48])|2000)-02-29|((19|20)[0-9]{2}-(0[4678]|1[02])-(0[1-9]|[12][0-9]|30)|(19|20)[0-9]{2}-(0[1359]|11)-(0[1-9]|[12][0-9]|3[01])|(19|20)[0-9]{2}-02-(0[1-9]|1[0-9]|2[0-8]))) ([01][0-9]|2[0-3]):([012345][0-9]):([012345][0-9]))\\.0";
    private static final FPECipher<String> fpeCipher = new RankThenEncipher<>(new StringMessageSpace(REGEXP));

    public Date() {
        super(fpeCipher);
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor spi, Object owner) throws SQLException {
        Object timestamp = super.nullSafeGet(rs, names, spi, owner);
        return Timestamp.valueOf(timestamp.toString());
    }
}
