package org.jcryptool.visual.rss.algorithm;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jcryptool.visual.rss.persistence.InvalidSignatureException;
import org.jcryptool.visual.rss.persistence.MessageAndRedactable;
import org.jcryptool.visual.rss.persistence.Persistence;
import org.jcryptool.visual.rss.persistence.XMLPersistence;
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
    private List<Boolean> redactableParts;
    private boolean alreadyRedacted;
    private RedactableSignature signature;
    private SignatureOutput originalSignature;
    private List<MessagePart> originalMessages;
    private SignatureOutput redactedSignature;
    private final Persistence persistence;
    
    public RssAlgorithmController() {
        currentState = State.START;
        originalMessages = new ArrayList<MessagePart>();
        persistence = new XMLPersistence();
    }
    
    public State getCurrentState() {
        return currentState;
    }
    
    public KeyInformation getInformation() {
        return new KeyInformation(keyType, keyLength, keyPair);
    }
    
    public List<MessagePart> getMessageParts() {
    	if(currentState == State.START || currentState == State.KEY_SET) {
    		throw new IllegalStateException();
    	}
    	
    	// Does not work because of State.MESSAGE_VERIFIED happens after REDACTED_VERIFIED
    	/*if(currentState == State.MESSAGE_SIGNED || currentState == State.MESSAGE_VERIFIED) {
    		return originalSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
    	}
        
    	if(currentState == State.PARTS_REDACTED || currentState == State.REDACTED_VERIFIED) {
    		return redactedSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
    	}*/
    	
    	
    	if(currentState == State.MESSAGE_SET) {
    	   	return originalMessages;
    	}
 
      	if(redactedSignature == null) {
    		return originalSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
    	} else {
    		return redactedSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
    	}
    }
    
	public List<MessagePart> getOriginalMessageParts() {
		return originalSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
	}
    
    public List<Boolean> getRedactableParts() {
    	if(currentState == State.MESSAGE_SIGNED || currentState == State.MESSAGE_VERIFIED) {
    		return originalSignature.getMessageIdentifiers().stream().map(identifier -> originalSignature.isRedactable(identifier)).collect(Collectors.toList());
    	}
        
    	if(currentState == State.PARTS_REDACTED || currentState == State.REDACTED_VERIFIED) {
    		return redactedSignature.getMessageIdentifiers().stream().map(identifier -> originalSignature.isRedactable(identifier)).collect(Collectors.toList());
    	}
    	
        if (redactableParts == null) {
            return new LinkedList<Boolean>();
        }
        return Collections.unmodifiableList(redactableParts);
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
        alreadyRedacted = false;
        originalMessages.clear();
        originalSignature = null;
        redactedSignature = null;
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
    
    private void setInformation(KeyInformation information) {
    	assert information != null;
    	
    	//if(information.getKeyLength() == null || information.getKeyLength())
    	
    	
    	this.keyLength = information.keyLength;
    	this.keyPair = information.keyPair;
    	this.keyType = information.algorithmType;
    	currentState = State.KEY_SET;
    }
    
    public void setKey(KeyPair keyPair) {
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
        alreadyRedacted = false;
        originalMessages.clear();
        originalSignature = null;
        redactedSignature = null;
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
        
        MessagePart newMessagePart;
        for(int i = 0; i < messageParts.size(); i++) {
        	newMessagePart = new MessagePart(messageParts.get(i), i);
        	originalMessages.add(newMessagePart);
        }
        //this.originalMessages = messageParts.stream().map(messagePart -> new MessagePart(messagePart)).collect(Collectors.toList());
        
        currentState = State.MESSAGE_SET;
    }
    
    public synchronized void signMessage(List<MessagePart> messageParts) {
        if (currentState != State.MESSAGE_SET) {
            throw new IllegalStateException();
        }
        if (messageParts == null) {
            throw new NullPointerException();
        }
        
        // Check if algorithm is allowed to have non redactable parts
        if (isOnlyRedactablePartsAllowed() && messageParts.stream().anyMatch(part -> part.isRedactable() == false)) {
        	throw new IllegalStateException("The algorithm is not allowed to contain not redactable message parts.");
        }
        
        // Save redactable informations
        originalMessages = messageParts;

        //TODO make JOB:
        try {
            signature.initSign(keyPair);
            MessagePart messagePart;
            for (int i = 0; i < originalMessages.size(); i++) {
            	messagePart = originalMessages.get(i);
                signature.addPart(messagePart.getMessageBytes(), messagePart.isRedactable());
            }
            originalSignature = signature.sign();
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Given message can not be signed.", e);
        }
                
        currentState = State.MESSAGE_SIGNED;
    }
    
    public synchronized void redactAgain(boolean reset) {
        if (!(currentState == State.PARTS_REDACTED || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        if (reset) {
        	alreadyRedacted = false;
            redactedSignature = null;
        }
        currentState = State.MESSAGE_VERIFIED;
    }
    
    public synchronized void redactMessage(List<MessagePart> messagePartsToRedact) {
        if (currentState != State.MESSAGE_VERIFIED && currentState != State.PARTS_REDACTED) {
            throw new IllegalStateException();
        }
        if (messagePartsToRedact == null) {
            throw new NullPointerException();
        }

        try {
            signature.initRedact(keyPair.getPublic());
            for (MessagePart messagePart : messagePartsToRedact) {
            	Identifier identifier = messagePart.toIdentifier();
                signature.addIdentifier(identifier);
            }
            
            // If redactedParts == null, it is the first redact after creating/loading the signed message
            // Otherwise redact again on the already redacted message
            if(alreadyRedacted) {
            	redactedSignature = signature.redact(redactedSignature);
            } else {
            	redactedSignature = signature.redact(originalSignature);
            	alreadyRedacted = true;
            }
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalArgumentException("Message can not be redacted at given indices.", e);
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
            return signature.verify(originalSignature);
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
            return signature.verify(redactedSignature);
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
    	//RSS_WITH_PSA("RSS", "RSSwithPSA", "PSRSS", true),
    	GC("Generic Construction", "GCwithRSAandSHA256", "RSA", true),
    	MERSA("SBZ02-MersaProd", "MersaWithRSAandSHA256", "Mersa256KeyPairGenerator", true);
    	
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
        
        @Override
        public String toString(){
        	return String.valueOf(length);
        }

		public static KeyLength getItem(String keyLength) {
			return valueOf("KL_"+keyLength);
		}
    }

	/**
	 * Sets the given SignatureOutput. 
	 * Therewith also the messageParts, the redactableParts and the messageIdentifiers are affected.
	 * 
	 * @param signOut The SignatureOutput to set.
	 */
	private void setSignOut(SignatureOutput signOut) {
		assert signOut != null;
		
		this.originalSignature = signOut;
		
		// Set messageParts and redactableParts
		messageParts = new ArrayList<String>();
		redactableParts = new ArrayList<Boolean>();
		Collection<Identifier> messageIdentifiers = signOut.getMessageIdentifiers();
		
		for(Identifier identifier: messageIdentifiers) {
			messageParts.add(new String(identifier.getBytes()));
			redactableParts.add(signOut.isRedactable(identifier));
		}
		
		currentState = State.MESSAGE_SIGNED;
		
	}
	
	/**
	 * Saves the key information to the given file path.
	 * 
	 * @param path The file path to store the key information to.
	 * @throws FileNotFoundException Thrown when the given file path is invalid.
	 */
	public void saveKey(String path) throws FileNotFoundException {
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		if(currentState == State.START) {
			throw new IllegalStateException("The key can not be saved before created.");
		}
		
		persistence.saveInformation(getInformation(), path);		
	}

	/**
	 * Loads the key information from the given file path.
	 * 
	 * @param path The path to load the key from.
	 * @return The loaded keyInformation.
	 * @throws FileNotFoundException In case the given path is invalid.
	 * @throws NoSuchAlgorithmException In case the algorithm is not supported.
	 * @throws InvalidKeyException In case the key is not supported by the visualisation.
	 */
	public KeyInformation loadKey(String path) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeyException {
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		KeyInformation information = persistence.loadInformation(path);
		
		if(information != null) {
			setInformation(information);	
			setSignature(RedactableSignature.getInstance(keyType.getSignatureType()));
		}
		
		return information;
	}

	/**
	 * Saves the signatureOutput to the given file path.
	 * 
	 * @param path The file path to store the signatureOutput to.
	 * @throws FileNotFoundException Thrown when the given file path is invalid.
	 */
	public void saveSignature(String path) throws FileNotFoundException {
		if(originalSignature == null) {
			throw new IllegalStateException("SignOut must be initialized to store it.");
		}
		
		if(currentState == State.START || currentState == State.KEY_SET || currentState == State.MESSAGE_SET) {
			throw new IllegalStateException("The signature can not be saved before created.");
		}
		
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		persistence.saveSignatureOutput(originalSignature, path);	
	}
	
	/**
	 * Saves the redacted signatureOutput to the given file path.
	 * 
	 * @param path The file path to store the message information to.
	 * @throws FileNotFoundException Thrown when the given file path is invalid.
	 */
	public void saveRedactedSignature(String path) throws FileNotFoundException {
		if(redactedSignature == null) {
			throw new IllegalStateException("SignOut must be initialized to store it.");
		}
		
		if(currentState != State.PARTS_REDACTED && currentState != State.REDACTED_VERIFIED) {
			throw new IllegalStateException("The redacted signature can not be saved before created.");
		}
		
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		persistence.saveSignatureOutput(redactedSignature, path);	
	}
	
	/**
	 * Loads the signature output from the given file path. 
	 * 
	 * @param path The path to load the key from.
	 * @return The loaded message parts as Strings together with the information, which of them are redactable. Returns null in case the loading was not canceled.
	 * @throws FileNotFoundException In case the given path is invalid.
	 * @throws InvalidSignatureException In case the signature is not supported by the visualisation or the chosen key.
	 */
	public MessageAndRedactable loadSignature(String path) throws FileNotFoundException, InvalidSignatureException {
		if(currentState == State.START) {
			throw new IllegalStateException("The signature can not be loaded before the key is set.");
		}
		
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		SignatureOutput loadedSignOut = persistence.loadSignatureOutput(path);
		
		// TODO: Add check, if loadedSignOut matches chosen algorithm variant.
		
		if(loadedSignOut != null) {
			setSignOut(loadedSignOut);
			return new MessageAndRedactable(messageParts, redactableParts);
		}
		
		return null;
	}


}
