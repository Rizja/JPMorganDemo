package ch.bfh.fpelib.jpmorgan.encUserTypes;

import ch.bfh.fpelib.FPECipher;
import ch.bfh.fpelib.RankThenEncipher;
import ch.bfh.fpelib.messageSpace.StringMessageSpace;

public class Currency extends EncryptedString {

    private static final String REGEXP = "A(ED|FN|LL|MD|NG|OA|RS|UD|WG|ZN)|B(AM|BD|DT|GN|HD|IF|MD|ND|OB|OV|RL|SD|TN|WP|YR|ZD)|C(AD|DF|HE|HF|HW|LF|LP|NY|OP|OU|RC|UC|UP|VE|ZK)|D(JF|KK|OP|ZD)|E(GP|RN|TB|UR)|F(JD|KP)|G(BP|EL|HS|IP|MD|NF|TQ|YD)|H(KD|NL|RK|TG|UF)|I(DR|LS|NR|QD|RR|SK)|J(MD|OD|PY)|K(ES|GS|HR|MF|PW|RW|WD|YD|ZT)|L(AK|BP|KR|RD|SL|YD)|M(AD|DL|GA|KD|MK|NT|OP|RO|UR|VR|WK|XN|XV|YR|ZN)|N(AD|GN|IO|OK|PR|ZD)|OMR|P(AB|EN|GK|HP|KR|LN|YG)|QAR|R(ON|SD|UB|WF)|S(AR|BD|CR|DG|SP|EK|GD|HP|LL|OS|RD|TD|VC|YP|ZL)|T(HB|JS|MT|ND|OP|RY|TD|WD|ZS)|U(AH|GX|SD|YI|YU|ZS)|V(EF|ND|UV)|WST|X(AF|CD|OF|PF)|YER|Z(AR|MW|WL)";
    private static final FPECipher<String> fpeCipher = new RankThenEncipher<>(new StringMessageSpace(REGEXP));

    public Currency() {
        super(fpeCipher);
    }
}
