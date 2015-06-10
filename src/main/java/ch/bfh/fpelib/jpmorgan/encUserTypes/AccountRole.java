package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.messageSpace.StringMessageSpace;

public class AccountRole extends EncryptedString {

    private static final String REGEXP = "ROLE_(USER|ADMIN)";
    private static final FPECipher<String> fpeCipher = new RankThenEncipher<>(new StringMessageSpace(REGEXP));

    public AccountRole() {
        super(fpeCipher);
    }
}
