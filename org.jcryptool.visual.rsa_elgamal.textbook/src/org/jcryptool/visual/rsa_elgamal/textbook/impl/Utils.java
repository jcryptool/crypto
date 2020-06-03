package org.jcryptool.visual.rsa_elgamal.textbook.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.diffplug.common.base.Errors;
import com.google.common.collect.Lists;

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
    
    public static List<BigInteger> elementwise(Function<BigInteger, BigInteger> fun, Collection<? extends BigInteger> collection) {
    	return collection.stream().map(fun).collect(Collectors.toList());
    }
    
}
