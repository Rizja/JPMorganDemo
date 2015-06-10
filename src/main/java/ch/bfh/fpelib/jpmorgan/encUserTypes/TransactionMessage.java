package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.messageSpace.StringMessageSpace;

public class TransactionMessage extends EncryptedString {

    private static final String REGEXP = "([A-Z]?[a-z]{1,12})( [A-Z]?[a-z]{1,12}){0,3}";
    private static final FPECipher<String> fpeCipher = new RankThenEncipher<>(new StringMessageSpace(REGEXP));

    public TransactionMessage() {
        super(fpeCipher);
    }
}
