package org.jcryptool.visual.rsa_elgamal.textbook.impl.algo;

import org.jcryptool.visual.rsa_elgamal.textbook.impl.Utils;

public class RSAImpl_Div {

	@Override
	public List<BigInteger> encryptFile(String filePath) {
	    BufferedReader br = null;
	    FileInputStream fis = null;
	    String line = "";
	    List<BigInteger> encription = new ArrayList<BigInteger>();
	    try {
	        fis = new FileInputStream(new File(filePath));
	        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	
	        while ((line = br.readLine()) != null) {
	            if ("".equals(line)) {
	                continue;
	            }
	            encription.addAll(this.encryptMessage(line));
	        }
	
	    } catch (IOException ex) {
	        Logger.getLogger(RSAImpl.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        try {
	            if (fis != null) {
	                fis.close();
	            }
	            if (br != null) {
	                br.close();
	            }
	
	        } catch (IOException ex) {
	            Logger.getLogger(RSAImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return encription;
	
	
	}

	public List<BigInteger> messageToDecimal(final String message) {
	    List<BigInteger> toDecimal = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(message.getBytes());
	    if (isModulusSmallerThanMessage(messageBytes)) {
	        toDecimal = getValidEncryptionBlocks(Utils.splitMessages(new ArrayList<String>() {
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

	public List<BigInteger> fileToDecimal(final String filePath) {
	    BufferedReader br = null;
	    FileInputStream fis = null;
	    String line = "";
	    List<BigInteger> decimalLines = new ArrayList<BigInteger>();
	    try {
	        fis = new FileInputStream(new File(filePath));
	        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	
	        while ((line = br.readLine()) != null) {
	            if ("".equals(line)) {
	                continue;
	            }
	            decimalLines.addAll(this.messageToDecimal(line));
	        }
	
	    } catch (IOException ex) {
	        Logger.getLogger(RSAImpl.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        try {
	            if (fis != null) {
	                fis.close();
	            }
	            if (br != null) {
	                br.close();
	            }
	
	        } catch (IOException ex) {
	            Logger.getLogger(RSAImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return decimalLines;
	}

	public List<BigInteger> messageToDecimal(final String message) {
	    List<BigInteger> toDecimal = new ArrayList<BigInteger>();
	    BigInteger messageBytes = new BigInteger(message.getBytes());
	    if (isModulusSmallerThanMessage(messageBytes)) {
	        toDecimal = getValidEncryptionBlocks(Utils.splitMessages(new ArrayList<String>() {
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

	public List<BigInteger> fileToDecimal(final String filePath) {
	    BufferedReader br = null;
	    FileInputStream fis = null;
	    String line = "";
	    List<BigInteger> decimalLines = new ArrayList<BigInteger>();
	    try {
	        fis = new FileInputStream(new File(filePath));
	        br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
	
	        while ((line = br.readLine()) != null) {
	            if ("".equals(line)) {
	                continue;
	            }
	            decimalLines.addAll(this.messageToDecimal(line));
	        }
	
	    } catch (IOException ex) {
	        Logger.getLogger(RSAImpl.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
	        try {
	            if (fis != null) {
	                fis.close();
	            }
	            if (br != null) {
	                br.close();
	            }
	
	        } catch (IOException ex) {
	            Logger.getLogger(RSAImpl.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	    return decimalLines;
	}

}
