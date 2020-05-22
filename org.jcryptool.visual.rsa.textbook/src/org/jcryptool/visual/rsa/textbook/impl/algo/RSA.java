package org.jcryptool.visual.rsa.textbook.impl.algo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {

    public final static BigInteger ONE = new BigInteger("1");

    public BigInteger privateKey;
    public BigInteger e;
    public BigInteger modulus;
    public BigInteger p;
    public BigInteger q;
    public final BigInteger phi;

    public RSA(BigInteger p, BigInteger q, BigInteger e) {
        phi = (p.subtract(ONE)).multiply(q.subtract(ONE));
        this.e = e;
        this.p = p;
        this.q = q;
        modulus = p.multiply(q);
        privateKey = e.modInverse(phi); 
    }

    public BigInteger encrypt(BigInteger bigInteger) {
        if (isModulusSmallerThanMessage(bigInteger)) {
            throw new IllegalArgumentException("Could not encrypt - message bytes are greater than modulus");
        }
        return bigInteger.modPow(e, modulus);
    }

    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public BigInteger sign(BigInteger bigInteger) {
        return bigInteger.modPow(privateKey, modulus);
    }

    public BigInteger verify(BigInteger signedMessage) {
        return signedMessage.modPow(e, modulus);
    }

    public boolean isVerified(BigInteger signedMessage, BigInteger message) {
        return this.verify(signedMessage).equals(message);
    }

    private List<BigInteger> getValidEncryptionBlocks(List<String> messages) {
        List<BigInteger> validBlocks = new ArrayList<BigInteger>();
        BigInteger messageBytes = new BigInteger(messages.get(0).getBytes());
        if (!isModulusSmallerThanMessage(messageBytes)) {
            for (String msg : messages) {
                validBlocks.add(new BigInteger(msg.getBytes()));
            }
            return validBlocks;
        } else {
            return getValidEncryptionBlocks(StringUtil.splitMessages(messages));
        }
    }
    
    private boolean isModulusSmallerThanMessage(BigInteger messageBytes) {
        return modulus.compareTo(messageBytes) == -1;
    }
}