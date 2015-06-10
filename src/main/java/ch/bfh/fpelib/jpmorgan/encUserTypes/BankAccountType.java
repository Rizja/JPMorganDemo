package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.jpmorgan.bankAccount.BankAccount;
import ch.bfh.fpelib.messageSpace.EnumerationMessageSpace;
import org.hibernate.engine.spi.SessionImplementor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class BankAccountType extends EncryptedString {

    private static final List<String> TYPES = Arrays.asList("SAVINGS", "TRANSACTION", "TIME", "CALL");
    private static final FPECipher<String> fpeCipher = new RankThenEncipher<>(new EnumerationMessageSpace<>(TYPES));

    public BankAccountType() {
        super(fpeCipher);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor spi, Object owner) throws SQLException {
        //Convert String to Enum
        Object object = super.nullSafeGet(rs, names, spi, owner);
        object = Enum.valueOf(BankAccount.AccountType.class, object.toString());
        return object;
    }
}
