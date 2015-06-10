package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.intEnc.FFXIntegerCipher;
import ch.bfh.fpelib.messageSpace.StringMessageSpace;

import java.math.BigInteger;

public class Amount extends EncryptedInteger {

    private static final BigInteger MAX = BigInteger.valueOf(1_000_000_000_000l);
    private static final FPECipher<BigInteger> fpeCipher = new FFXIntegerCipher(MAX);

    public Amount() {
        super(fpeCipher);
    }
}
