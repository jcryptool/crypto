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

    public synchronized void setKeyAgain() {
        if (currentState == State.START) {
            throw new IllegalStateException();
        }
        redactedParts = null;
        currentState = State.START;
    }
    
    public synchronized void genKeyAndSignature(AlgorithmType kt, KeyLength kl) {
        if (currentState != State.START) {
            throw new IllegalStateException();
        }
        
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance(kt.getKt());
            keyGen.initialize(kl.getKl());
            setKey(keyGen.generateKeyPair());
            keyLength = kl;
            keyType = kt;
                
            setSignature(RedactableSignature.getInstance(kt.getKt()));
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
    
    public enum AlgorithmType {
        GLRSS_WITH_RSA_AND_BPA("GLRSSwithRSAandBPA"),
    	GSRSS_WITH_RSA_AND_BPA("GSRSSwithRSAandBPA");
        
        private final String keyTypeText;
        
        AlgorithmType(String kt) {
            this.keyTypeText = kt;
        }
        
        public String getKt() {
            return keyTypeText;
        }
        
        public static AlgorithmType fromString(String text) {
            for (AlgorithmType keyType : AlgorithmType.values()) {
                if (keyType.keyTypeText.equalsIgnoreCase(text)) {
                    return keyType;
                }
            }
            return null;
        }
    }
    
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
        
//        public static KeyLength fromLength(int length) {
//            for (KeyLength keyLength : KeyLength.values()) {
//                if (keyLength.getKl() == length) {
//                    return keyLength;
//                }
//            }
//            return null;
//        }
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
