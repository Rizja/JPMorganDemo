package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.intEnc.FFXIntegerCipher;
import org.hibernate.engine.spi.SessionImplementor;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

public class IBAN extends EncryptedString {

    private static final Map<Integer,FPECipher<BigInteger>> fpeCiphers = new HashMap<>(); //FPECipher for different IBAN lengths

    public IBAN() {
        super(null);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor spi, Object owner) throws SQLException {
        String value = rs.getString(names[0]);
        if (value == null) { // if NULL in database return null
            return null;
        }
        int encColumnIndex; // find column 'encrypted' from result set
        for (encColumnIndex=1;encColumnIndex<=rs.getMetaData().getColumnCount();encColumnIndex++) {
            if (rs.getMetaData().getColumnName(encColumnIndex).equals("encrypted")) break;
            else if (encColumnIndex==rs.getMetaData().getColumnCount()) throw new RuntimeException("Encrypted field in entity missing.");
        }
        if (rs.getBoolean(encColumnIndex)) {  // only decrypt if 'encrypted' flag is set
            InternalIBAN iban = new InternalIBAN(value);
            if (!fpeCiphers.containsKey(iban.lengthBBAN)) fpeCiphers.put(iban.lengthBBAN, new FFXIntegerCipher(BigInteger.valueOf(10).pow(iban.lengthBBAN).subtract(BigInteger.ONE)));
            FPECipher<BigInteger> fpeCipher = fpeCiphers.get(iban.lengthBBAN);
            iban.bban = fpeCipher.encrypt(new BigInteger(iban.bban), KEY, TWEAK).toString();
            iban.padBBAN();
            iban.calcChecksum();
            value = iban.getIBAN();
        }
        return value;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor spi) throws SQLException {
        if (value != null) {
            InternalIBAN iban = new InternalIBAN(value.toString());
            if (!fpeCiphers.containsKey(iban.lengthBBAN)) fpeCiphers.put(iban.lengthBBAN, new FFXIntegerCipher(BigInteger.valueOf(10).pow(iban.lengthBBAN).subtract(BigInteger.ONE)));
            FPECipher<BigInteger> fpeCipher = fpeCiphers.get(iban.lengthBBAN);
            iban.bban = fpeCipher.decrypt(new BigInteger(iban.bban), KEY, TWEAK).toString();
            iban.padBBAN();
            iban.calcChecksum();
            st.setString(index, iban.getIBAN());
        } else {
            st.setNull(index, Types.VARCHAR); //if NULL in Java set to NULL in database
        }
    }

    private class InternalIBAN {
        private List<String> countries = Arrays.asList("AL","AD","AT","AZ","BH","BE","BA","BR","BG","CR","HR","CY","CZ","DK","DO","TL","EE","FO","FI","FR","GE","DE","GI","GR","GL","GT","HU","IS","IE","IL","IT","JO","KZ","XK","KW","LV","LB","LI","LT","LU","MK","MT","MR","MU","MC","MD","ME","NL","NO","PK","PS","PL","PT","QA","RO","SM","SA","RS","SK","SI","ES","SE","CH","TN","TR","AE","GB","VG");
        private List<Integer> lengths = Arrays.asList(28,24,20,28,22,16,20,29,22,21,21,28,24,18,28,23,20,18,18,27,22,22,23,27,18,28,28,26,22,23,27,30,20,20,30,21,28,21,20,20,19,31,27,30,27,24,22,18,15,24,29,28,25,29,24,27,24,22,24,19,24,24,21,24,26,23,22,24);
        private String country;
        private String checksum;
        private String bban;
        private int length;
        private int lengthBBAN;

        private InternalIBAN(String iban) {
            if (iban.length()<15 || iban.length()>34) throw new RuntimeException("Invalid IBAN. Length invalid. " + iban);
            country = iban.substring(0,2);
            checksum = iban.substring(2, 4);
            bban = iban.substring(4);
            int countryIndex = countries.indexOf(country.toUpperCase());
            if (countryIndex == -1) throw new RuntimeException("Invalid IBAN. Invalid country. " + iban);
            length = lengths.get(countryIndex);
            lengthBBAN = length-4;
            if (iban.length() != length) throw new RuntimeException("Invalid IBAN. Length invalid for country. " + iban);
            if (!calcChecksum()) throw new RuntimeException("Invalid IBAN. Invalid checksum. " + iban);
        }

        private String getIBAN() {
            return country + checksum + bban;
        }

        private void padBBAN() {
            while (bban.length() < lengthBBAN) {
                bban = "0" + bban;
            }
        }

        private boolean calcChecksum() {
            StringBuilder ibanNum = new StringBuilder();
            String iban = bban + country + "00";
            for (char c : iban.toCharArray()) {
                if (c < 'A') ibanNum.append(c); // Numbers
                else if (c < 'a') ibanNum.append(String.valueOf(c-55)); // Lowercase letter to number
                else ibanNum.append(String.valueOf(c-87)); // Capitals to number
            }
            BigInteger checksum = new BigInteger(ibanNum.toString());
            checksum = checksum.mod(BigInteger.valueOf(97));
            String newChecksum = BigInteger.valueOf(98).subtract(checksum).toString();
            if (newChecksum.length()==1) newChecksum = "0" + newChecksum;
            boolean equal = (newChecksum.equals(this.checksum));
            this.checksum = newChecksum;
            return equal;
        }
    }
}
