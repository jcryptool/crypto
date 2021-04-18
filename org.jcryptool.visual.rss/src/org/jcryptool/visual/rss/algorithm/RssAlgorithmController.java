package org.jcryptool.visual.rss.algorithm;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jcryptool.visual.rss.ui.RssBodyComposite.ActiveRssBodyComposite;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.Identifier;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.RedactableSignature;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.RedactableSignatureException;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.SignatureOutput;

/**
 * The controller of the algorithm, which uses the methods of the WPProvider from Wolfgang Popp:
 * https://github.com/woefe/xmlrss
 * 
 * @author Leon Shell, Lukas Krodinger
 */
public class RssAlgorithmController {
    private State currentState;
    private KeyLength keyLength;
    private AlgorithmType keyType;
    private KeyPair keyPair;
    private List<String> messageParts;
    private List<Boolean> redactedParts;
    private List<Boolean> redactableParts;
    private RedactableSignature signature;
    private SignatureOutput signOut;
    private SignatureOutput redacted;
    
    public RssAlgorithmController() {
        currentState = State.START;
    }
    
    public State getCurrentState() {
        return currentState;
    }
    
    public Information getInformation() {
        return new Information(keyType, keyLength, keyPair);
    }
    
    public List<String> getMessageParts() {
        if (messageParts == null) {
            return new LinkedList<String>();
        }
        return Collections.unmodifiableList(messageParts);
    }
    
    public List<Boolean> getRedactableParts() {
        if (redactableParts == null) {
            return new LinkedList<Boolean>();
        }
        return Collections.unmodifiableList(redactableParts);
    }
    
    public List<Boolean> getRedactedParts() {
        if (redactedParts == null) {
            return null;
        }
        return Collections.unmodifiableList(redactedParts);
    }
    
    /**
     * Gets whether there are only redactable parts allowed.
     * @return True, when all message parts must be redactable for this algorithm, false otherwise.
     */
    public boolean isOnlyRedactablePartsAllowed() {
        String algorithmAsString = signature.getAlgorithm();
        AlgorithmType algorithm = AlgorithmType.fromString(algorithmAsString);
        return algorithm.isOnlyRedactablePartsAllowed();
    }

    public synchronized void setKeyAgain() {
        if (currentState == State.START) {
            throw new IllegalStateException();
        }
        redactedParts = null;
        currentState = State.START;
    }
    
    public synchronized void genKeyAndSignature(AlgorithmType algorithmType, KeyLength kl) {
        if (currentState != State.START) {
            throw new IllegalStateException();
        }
        
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance(algorithmType.getKeyPairGenerationType());
            keyGen.initialize(kl.getKl());
            KeyPair keyPair = keyGen.generateKeyPair();
            setKey(keyPair);
            keyLength = kl;
            keyType = algorithmType;
                
            setSignature(RedactableSignature.getInstance(algorithmType.getSignatureType()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("There is no implementation for the given key type.");
        }
        currentState = State.KEY_SET;
    }
    
    private void setKey(KeyPair keyPair) {
        if (keyPair == null) {
            throw new NullPointerException();
        }
        this.keyPair = keyPair;
    }
    
    private void setSignature(RedactableSignature signature) {
        if (signature == null) {
            throw new NullPointerException();
        }
        this.signature = signature;
    }
    
    public synchronized void newMessageAgain() {
        if (!(currentState == State.MESSAGE_SIGNED
              || currentState == State.MESSAGE_VERIFIED
              || currentState == State.PARTS_REDACTED
              || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        redactedParts = null;
        currentState = State.KEY_SET;
    }
    
    public synchronized void newMessage(List<String> messageParts) {
        if (currentState != State.KEY_SET) {
            throw new IllegalStateException();
        }
        if (messageParts == null) {
            throw new NullPointerException();
        }
        if (messageParts.isEmpty()) {
            throw new IllegalArgumentException("Can not have message with no parts.");
        }
        this.messageParts = messageParts;
        currentState = State.MESSAGE_SET;
    }
    
    public synchronized void signMessage(List<Boolean> redactableParts) {
        if (currentState != State.MESSAGE_SET) {
            throw new IllegalStateException();
        }
        if (redactableParts == null) {
            throw new NullPointerException();
        }
        if (redactableParts.size() != messageParts.size()) {
            throw new IllegalArgumentException("Redactable parts list must match size of message parts list.");
        }
        
        // Check if algorithm is allowed to have non redactable parts
        if (isOnlyRedactablePartsAllowed() && redactableParts.contains(false)) {
        	throw new IllegalStateException("The algorithm is not allowed to contain not redactable message parts.");
        }
        
        this.redactableParts = redactableParts;
        //TODO make JOB:
        try {
            signature.initSign(keyPair);
            for (int i = 0; i < messageParts.size(); i++) {
                signature.addPart(messageParts.get(i).getBytes(), redactableParts.get(i));
            }
            signOut = signature.sign();
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Given message can not be signed.", e);
        }
        currentState = State.MESSAGE_SIGNED;
    }
    
    public synchronized void redactAgain() {
        redactAgain(true);
    }

    public synchronized void redactAgain(boolean reset) {
        if (!(currentState == State.PARTS_REDACTED || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        if (reset) {
            redactedParts = null;
        }
        currentState = State.MESSAGE_VERIFIED;
    }
    
    public synchronized void redactMessage(List<Integer> indices) {
        if (currentState != State.MESSAGE_VERIFIED && currentState != State.PARTS_REDACTED) {
            throw new IllegalStateException();
        }
        if (indices == null) {
            throw new NullPointerException();
        }
        if (indices.stream().anyMatch(i -> i == null 
                                           || i < 0 
                                           || i >= redactableParts.size() 
                                           || !redactableParts.get(i))) {
            throw new IllegalArgumentException();
        }
        //TODO make JOB
        try {
            signature.initRedact(keyPair.getPublic());
            for (Integer index : indices) {
                signature.addIdentifier(new Identifier(messageParts.get(index).getBytes(), index));
            }
            redacted = signature.redact(signOut);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalArgumentException("Message can not be redacted at given indices.", e);
        }
        if (redactedParts == null) {
            redactedParts = new ArrayList<Boolean>(Collections.nCopies(this.messageParts.size(), false));
        }
        for (int i = 0; i < indices.size(); i++) {
            redactedParts.set(indices.get(i), true);
        }
        currentState = State.PARTS_REDACTED;
    }
    
    public synchronized boolean verifyOriginalMessage() {
        if (currentState != State.MESSAGE_SIGNED) {
            throw new IllegalStateException();
        }
        boolean doesVerify = unsafeVerifyOriginalMessage();
        currentState = State.MESSAGE_VERIFIED;
        return doesVerify;
    }

    public synchronized boolean confirmVerifyOriginalMessage() {
        if (!(currentState == State.MESSAGE_SIGNED
              || currentState == State.MESSAGE_VERIFIED
              || currentState == State.PARTS_REDACTED
              || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        return unsafeVerifyOriginalMessage();
    }
    
    private boolean unsafeVerifyOriginalMessage() {
        try {
            signature.initVerify(keyPair.getPublic());
            return signature.verify(signOut);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Signed message can not be verified.", e);
        }
    }

    public synchronized boolean verifyRedactedMessage() {
        if (currentState != State.PARTS_REDACTED) {
            throw new IllegalStateException();
        }

        boolean doesVerify = unsafeVerifyRedactedMessage();
        currentState = State.REDACTED_VERIFIED;
        return doesVerify;
    }

    public synchronized boolean confirmVerifyRedactedMessage() {
        if (!(currentState == State.PARTS_REDACTED || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        return unsafeVerifyRedactedMessage();
    }

    private boolean unsafeVerifyRedactedMessage() {
        try {
            signature.initVerify(keyPair.getPublic());
            return signature.verify(redacted);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Redacted message can not be verified.", e);
        }
    }
    
    public enum State {
        START(ActiveRssBodyComposite.SET_KEY),
        KEY_SET(ActiveRssBodyComposite.SET_MESSAGE),
        MESSAGE_SET(ActiveRssBodyComposite.SIGN_MESSAGE),
        MESSAGE_SIGNED(ActiveRssBodyComposite.VERIFY_MESSAGE),
        MESSAGE_VERIFIED(ActiveRssBodyComposite.REDACT),
        PARTS_REDACTED(ActiveRssBodyComposite.VERIFY_REDACTED),
        REDACTED_VERIFIED(ActiveRssBodyComposite.REDACT);
        
        private final ActiveRssBodyComposite composite;
        
        State(ActiveRssBodyComposite composite) {
            this.composite = composite;
        }
        
        public ActiveRssBodyComposite getComposite() {
            return composite;
        }
    }
    
    /**
     * The available algorithm types in the WPProvider together with a text representation and a short name.
     * Note to not use GLRSS or GSRSS with PSRSS, as this will not work together.
     * Also do not use BPA, as this is not implemented completely; use GLRSSwithRSAandBPA/GSRSSwithRSAandBPA instead.
     * 
     * @author Lukas Krodinger
     */
    public enum AlgorithmType {
        GLRSS_WITH_RSA_AND_BPA("GLRSS", "GLRSSwithRSAandBPA", "GLRSSwithRSAandBPA", false),
    	GSRSS_WITH_RSA_AND_BPA("GSRSS", "GSRSSwithRSAandBPA", "GSRSSwithRSAandBPA", false),
    	RSS_WITH_PSA("RSS", "RSSwithPSA", "PSRSS", true);
    	
    	/*
    	 * Not working, as BPPrivateKey of BPA not implemented:
    	 * https://github.com/woefe/xmlrss/blob/2e178daad02d013352b29d05c4dacda2110960e5/src/main/java/de/unipassau/wolfgangpopp/xmlrss/wpprovider/grss/BPKeyPairGenerator.java
    	 * TEST("GLRSSwithRSAandBPA", "GLRSS with BPA", "BPA", false); // Not Implemented, probably invalid anyway
    	 * 
    	 * Not working as not allowed:
    	 * TEST2("GLRSSwithRSAandBPA", "GLRSSwithRSAandBPA with GSRSSwithRSAandBPA", "GSRSSwithRSAandBPA", false), //Invalid
    	 * TEST3("GSRSSwithRSAandBPA", "GSRSSwithRSAandBPA with GLRSSwithRSAandBPA", "GLRSSwithRSAandBPA", false), //Invalid
      	 * TEST4("GLRSSwithRSAandBPA", "GLRSSwithRSAandBPA with PSRSS", "PSRSS", false), //Invalid
    	 * TEST5("GSRSSwithRSAandBPA", "GSRSSwithRSAandBPA with PSRSS", "PSRSS", false); //Invalid
    	 */ 
    	
        private final String keyPairGenerationType;
        private final String shortName;
        private final String signatureType;
        private final boolean onlyRedactablePartsAllowed;
        
        AlgorithmType(String shortName,String signatureType, String keyPairGenerationType, boolean onlyRedactablePartsAllowed) {
            this.shortName = shortName;
            this.keyPairGenerationType = keyPairGenerationType;
            this.signatureType = signatureType;
            this.onlyRedactablePartsAllowed = onlyRedactablePartsAllowed;
        }
        
        public String getKeyPairGenerationType() {
            return keyPairGenerationType;
        }
        
        public String getSignatureType() {
        	return signatureType;
        }
                     
        @Override
        public String toString() {
        	return shortName;
        }
        
        public boolean isOnlyRedactablePartsAllowed() {
        	return onlyRedactablePartsAllowed;
        }
        
        public static AlgorithmType fromString(String text) {
            for (AlgorithmType keyType : AlgorithmType.values()) {
                if (keyType.shortName.equalsIgnoreCase(text) || keyType.signatureType.equalsIgnoreCase(text)) {
                    return keyType;
                }
            }
            return null;
        }
    }
    
    /**
     * The available key lengths for the generated keys.
     * @author Lukas Krodinger
     */
    public enum KeyLength {
        KL_512(512),
        KL_1024(1024),
        KL_2048(2048);
        
        private final int length;
        
        KeyLength(int kl) {
            this.length = kl;
        }
        
        public int getKl() {
            return length;
        }
    }
    
    public class Information {
        private AlgorithmType keyType;
        private KeyLength keyLength;
        private KeyPair keyPair;
        public Information(AlgorithmType keyType, KeyLength keyLength, KeyPair keyPair) {
            this.keyType = keyType;
            this.keyLength = keyLength;
            this.keyPair = keyPair;
        }
        public AlgorithmType getKeyType() {
            return keyType;
        }
        public KeyLength getKeyLength() {
            return keyLength;
        }
        public KeyPair getKeyPair() {
            return keyPair;
        }
    }
}
