package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.messageSpace.StringMessageSpace;

public class AccountEmail extends EncryptedString {

    private static final String REGEXP = "[0-9a-zA-z.\\-@]{1,20}";
    private static final FPECipher<String> fpeCipher = new RankThenEncipher<>(new StringMessageSpace(REGEXP));

    public AccountEmail() {
        super(fpeCipher);
    }
}
