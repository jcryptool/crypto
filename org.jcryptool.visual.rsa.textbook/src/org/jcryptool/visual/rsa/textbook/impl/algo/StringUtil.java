package org.jcryptool.visual.rsa.textbook.impl.algo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

	public static List<BigInteger> encryptMessage(final String message, RSA rsa) {
	    List<BigInteger> toEncrypt = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(message.getBytes());
	    if (isModulusSmallerThanMessage(rsa, messageBytes)) {
	        toEncrypt = getValidEncryptionBlocks(rsa, StringUtil.splitMessages(new ArrayList<String>() {
	            {
	                add(message);
	            }
	        }));
	    } else {
	        toEncrypt.add((messageBytes));
	    }
	    List<BigInteger> encrypted = new ArrayList<BigInteger>();
	    for (BigInteger bigInteger : toEncrypt) {
	        encrypted.add(rsa.encrypt(bigInteger));
	    }
	    return encrypted;
	}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BigInteger p;
        BigInteger q;
        BigInteger e;
        final String message;
        boolean isFile = false;
        if (args.length != 4) {//at leat four parametter should be given
            p = new BigInteger("5700734181645378434561188374130529072194886062117");
            q = new BigInteger("35894562752016259689151502540913447503526083241413");
            e = new BigInteger("33445843524692047286771520482406772494816708076993");
            message = "This is a test";
            
            //below are also valid primes
//          p = new BigInteger("101"); 
//          q = new BigInteger("113");
//          e = new BigInteger("3533");
        } else {
            p = new BigInteger(args[0]);
            q = new BigInteger(args[1]);
            e = new BigInteger(args[2]);
            if (args[3].contains("-f")) {
                isFile = true;
                message = args[3].substring(2);
            }
            else{
                message = args[3];
             }
        }
            

        RSA rsa = new RSA(p, q, e);
        System.out.println(rsa);

        List<BigInteger> encryption;
        List<BigInteger> signed;
        List<BigInteger> decimalMessage;
        encryption = encryptMessage(rsa, message);
        signed = signMessage(rsa, message);
        decimalMessage = messageToDecimal(rsa, message);

        List<BigInteger> decrypt = encryption.stream().map(x -> rsa.encrypt(x)).collect(Collectors.toList());
        List<BigInteger> verify = signed.stream().map(x -> rsa.encrypt(x)).collect(Collectors.toList());

    }

	private static List<BigInteger> getValidEncryptionBlocks(RSA rsa, List<String> messages) {
	    List<BigInteger> validBlocks = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(messages.get(0).getBytes());
	    if (!isModulusSmallerThanMessage(rsa, messageBytes)) {
	        for (String msg : messages) {
	            validBlocks.add(new BigInteger(msg.getBytes()));
	        }
	        return validBlocks;
	    } else {
	        return getValidEncryptionBlocks(rsa, StringUtil.splitMessages(messages));
	    }
	
	}

	public static List<BigInteger> messageToDecimal(RSA rsa, final String message) {
	    List<BigInteger> toDecimal = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(message.getBytes());
	    if (isModulusSmallerThanMessage(rsa, messageBytes)) {
	        toDecimal = getValidEncryptionBlocks(rsa, StringUtil.splitMessages(new ArrayList<String>() {
	            {
	                add(message);
	            }
	        }));
	    } else {
	        toDecimal.add((messageBytes));
	    }
	    List<BigInteger> decimal = new ArrayList<BigInteger>();
	    for (BigInteger bigInteger : toDecimal) {
	        decimal.add(bigInteger);
	    }
	    return decimal;
	}

	public static List<BigInteger> signMessage(RSA rsa, final String message) {
	    List<BigInteger> toSign = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(message.getBytes());
	    if (isModulusSmallerThanMessage(rsa, messageBytes)) {
	        toSign = getValidEncryptionBlocks(rsa, StringUtil.splitMessages(new ArrayList<String>() {
	            {
	                add(message);
	            }
	        }));
	    } else {
	        toSign.add((messageBytes));
	    }
	    List<BigInteger> signed = new ArrayList<BigInteger>();
	    for (BigInteger bigInteger : toSign) {
	        signed.add(rsa.sign(bigInteger));
	    }
	    return signed;
	}

	public static List<BigInteger> encryptMessage(RSA rsa, final String message) {
	    List<BigInteger> toEncrypt = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(message.getBytes());
	    if (isModulusSmallerThanMessage(rsa, messageBytes)) {
	        toEncrypt = getValidEncryptionBlocks(rsa, StringUtil.splitMessages(new ArrayList<String>() {
	            {
	                add(message);
	            }
	        }));
	    } else {
	        toEncrypt.add((messageBytes));
	    }
	    List<BigInteger> encrypted = new ArrayList<BigInteger>();
	    for (BigInteger bigInteger : toEncrypt) {
	        encrypted.add(rsa.encrypt(bigInteger));
	    }
	    return encrypted;
	}

	private static boolean isModulusSmallerThanMessage(RSA rsa, BigInteger messageBytes) {
		return rsa.modulus.compareTo(messageBytes) == -1;
	}

	public static List<String> splitMessages(List<String> messages) {
	    List<String> splitedMessages = new ArrayList<String>(messages.size() * 2);
	    for (String message : messages) {
	        int half = (int) Math.ceil(((double) message.length()) / ((double) 2));
	        splitedMessages.add(message.substring(0, half));
	        if (half < message.length()) {
	            splitedMessages.add(message.substring(half, message.length()));
	        }
	    }
	
	    return splitedMessages;
	
	}
}
