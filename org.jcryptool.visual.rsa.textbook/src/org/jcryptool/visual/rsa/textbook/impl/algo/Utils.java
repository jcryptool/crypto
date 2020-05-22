package org.jcryptool.visual.rsa.textbook.impl.algo;

import java.math.BigInteger;
import java.util.List;

public class Utils {

    public static String bigIntegerToString(List<BigInteger> list) {
        StringBuilder plainText = new StringBuilder();
        for (BigInteger bigInteger : list) {
            plainText.append(new String(bigInteger.toByteArray()));
        }
        return plainText.toString();
    }

    public static String bigIntegerSum(List<BigInteger> list) {
        BigInteger result = new BigInteger("0");
        for (BigInteger bigInteger : list) {
            result = result.add(bigInteger);
        }
        return result.toString();
    }
    
}
