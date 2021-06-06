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
 * The RSSAlgorithmController is the communication layer between the WPProvider (https://github.com/woefe/xmlrss)
 * and the UI in the org.jcryptool.cisual.rss.ui package. The class saves the current state
 * together with the relevant information of the state. This might be information about the key or the signature.
 * The class also delegates method calls to the WPProvider together with the needed input.
 * 
 * @author Leon Shell, Lukas Krodinger
 */
public class RssAlgorithmController {
	
	/**
	 * The current state of the RssAlgorithmController.
	 */
    private State currentState;
    
    /*
     * Information about the current key.
     */
    private KeyLength keyLength;
    private AlgorithmType keyType;
    private KeyPair keyPair;
    
    /**
     * The current RedactableSignature to use for signing, redacting and validating.
     */
    private RedactableSignature signature;
    
    /**
     * The messages before signing.
     */
    private List<MessagePart> originalMessages;
    
    /**
     * The signature after signing, before redacting for the first time.
     */
    private SignatureOutput originalSignature;
    
    /**
     * The current signature might be after signing or after (multiple) redactions.
     */
    private SignatureOutput currentSignature;
    
    /**
     * This class is for saving and loading key information or signatures.
     */
    private final Persistence persistence;
    
    /**
     * Creates a new algorithm controller. Therefore sets the state of it to START and initializes values.
     */
    public RssAlgorithmController() {
        currentState = State.START;
        originalMessages = new ArrayList<MessagePart>();
        persistence = new XMLPersistence();
    }
    
    /**
     * Gets the current state.
     * @return The current state.
     */
    public State getCurrentState() {
        return currentState;
    }
    
    /**
     * Gets all information about the key. This information consists of the key type, 
     * the key length and the key pair itself.
     * @return The current key information.
     */
    public KeyInformation getInformation() {
        return new KeyInformation(keyType, keyLength, keyPair);
    }
    
    /**
     * Gets the current message parts. In case the state is MESSAGE_SET and therewith the message parts were
     * just set but not signed yet, the previous set parts are returned. Otherwise the message parts of the 
     * currentSignature are returned.
     * @return The current message parts.
     */
    public List<MessagePart> getMessageParts() {
    	if(currentState == State.START || currentState == State.KEY_SET) {
    		throw new IllegalStateException();
    	}   	
    	
    	if(currentState == State.MESSAGE_SET) {
    	   	return originalMessages;
    	}
 
    	return currentSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
    }
    
    /**
     * Gets the message parts before redacting. Therefore the message parts of the original signature are returned.
     * @return The original message parts.
     */
	public List<MessagePart> getOriginalMessageParts() {
		if(currentState == State.START || currentState == State.KEY_SET || currentState == State.MESSAGE_SET) {
    		throw new IllegalStateException();
    	}   	
		
		return originalSignature.getMessageIdentifiers().stream().map(identifier -> new MessagePart(identifier, originalSignature.isRedactable(identifier))).collect(Collectors.toList());
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

    /**
     * Resets the algorithm controller to the state START. The next step is to initialize the key (again).
     */
    public synchronized void resetToStart() {
        if (currentState == State.START) {
            throw new IllegalStateException();
        }
        
        originalMessages.clear();
        originalSignature = null;
        currentSignature = null;
        
        currentState = State.START;
    }
    
    /**
     * Generates a new key. Gives the information of the algorithmType and the keyLength to a KeyPairGenerator
     * to generate a new keyPair. This keyInformation is then set as the current one.
     * @param algorithmType The type of the algorithm.
     * @param keyLength The length of the key.
     */
    public synchronized void generateKey(AlgorithmType algorithmType, KeyLength keyLength) {
        if (currentState != State.START) {
            throw new IllegalStateException();
        }
        
        try {
          	// Generate a new key using a KeyPairGenerator instance
        	KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithmType.getKeyPairGenerationType());
            keyGen.initialize(keyLength.getKl());
            KeyPair keyPair = keyGen.generateKeyPair();
            
            // Set the resulting keyInformation as the current one
            KeyInformation keyInformation = new KeyInformation(algorithmType, keyLength, keyPair);
            setKeyInformation(keyInformation);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("There is no implementation for the given key type.");
        }
        
        currentState = State.KEY_SET;
    }
    
    /**
     * Sets the given keyInformation object to the current one. Therefore updates the information of the controller 
     * and initializes the signature algorithm, which belongs to the key.
     * @param information The keyInformation to set.
     * @throws NoSuchAlgorithmException In case the algorithm for the key is not supported.
     */
    private void setKeyInformation(KeyInformation information) throws NoSuchAlgorithmException {
    	assert information != null;
    	
    	// Set the signature algorithm to use
    	this.signature = RedactableSignature.getInstance(information.algorithmType.getSignatureType());
    	
    	// Set all information about the key and the key pair itself
    	this.keyLength = information.keyLength;
    	this.keyPair = information.keyPair;
    	this.keyType = information.algorithmType;
    	
    	currentState = State.KEY_SET;
    }
    
    /**
     * Resets the algorithm controller to the state KEY_SET. The next step is to create the message parts (again).
     */
    public synchronized void resetToKeySet() {
        if (!(currentState == State.MESSAGE_SIGNED
              || currentState == State.MESSAGE_VERIFIED
              || currentState == State.PARTS_REDACTED
              || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        
        originalMessages.clear();
        originalSignature = null;
        currentSignature = null;
        
        currentState = State.KEY_SET;
    }
    
    /**
     * Sets new message parts. Therefore converts the given list of Strings into a list of 
     * MessageParts and stores those.
     * 
     * @param messageParts The messageParts of the new message.
     */
    public synchronized void newMessage(List<String> messageParts) {
        if (currentState != State.KEY_SET) {
            throw new IllegalStateException();
        }
        if (messageParts == null || messageParts.isEmpty()) {
        	throw new IllegalArgumentException("Can not work with message with no parts.");
        }

        MessagePart newMessagePart;
        for(int i = 0; i < messageParts.size(); i++) {
        	newMessagePart = new MessagePart(messageParts.get(i), i);
        	originalMessages.add(newMessagePart);
        }
        
        currentState = State.MESSAGE_SET;
    }
    
    /**
     * Signs the given messageParts. Then stores the signature as originalSignature and as currentSignature.
     * @param messageParts The messageParts to sign.
     */
    public synchronized void signMessage(List<MessagePart> messageParts) {
        if (currentState != State.MESSAGE_SET) {
            throw new IllegalStateException();
        }
        if (messageParts == null || messageParts.isEmpty()) {
        	throw new IllegalArgumentException("Can not sign message with no parts.");
        }
        
        // Check if algorithm is allowed to have non redactable parts
        if (isOnlyRedactablePartsAllowed() && messageParts.stream().anyMatch(part -> part.isRedactable() == false)) {
        	throw new IllegalStateException("The algorithm is not allowed to contain not redactable message parts.");
        }
        
        // Save redactable informations
        originalMessages = messageParts;

        try {
        	
        	// Create signature for message
            signature.initSign(keyPair);
            MessagePart messagePart;
            for (int i = 0; i < originalMessages.size(); i++) {
            	messagePart = originalMessages.get(i);
                signature.addPart(messagePart.getMessageBytes(), messagePart.isRedactable());
            }
            originalSignature = signature.sign();
            currentSignature = originalSignature;
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Given message can not be signed.", e);
        }
                
        currentState = State.MESSAGE_SIGNED;
    }
    
    /**
     * Resets the state to the state MESSAGE_VERIFIED. The next step is then to redact the message.
     * @param reset Whether to reset the currentSignature to the originalSignature. Should probably be always true.
     */
    public synchronized void resetToMessageVerified(boolean reset) {
        if (!(currentState == State.PARTS_REDACTED || currentState == State.REDACTED_VERIFIED)) {
            throw new IllegalStateException();
        }
        
        // Reset signature or not
        if (reset) {
        	currentSignature = originalSignature;
        }
        
        currentState = State.MESSAGE_VERIFIED;
    }
    
    /**
     * Redacts the given messagePartsToRedact from the currentSignature. The originalSignature is not
     * touched and can still be restored.
     * @param messagePartsToRedact The message parts to redact from the currentSignature.
     */
    public synchronized void redactMessage(List<MessagePart> messagePartsToRedact) {
        if (currentState != State.MESSAGE_VERIFIED && currentState != State.PARTS_REDACTED) {
            throw new IllegalStateException();
        }
        if (messagePartsToRedact == null) {
            throw new IllegalArgumentException("messagePartsToRedact must not be null.");
        }

        try {
            signature.initRedact(keyPair.getPublic());
            for (MessagePart messagePart : messagePartsToRedact) {
            	Identifier identifier = messagePart.toIdentifier();
                signature.addIdentifier(identifier);
            }
                   
            currentSignature = signature.redact(currentSignature);
          
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalArgumentException("Message can not be redacted at given indices.", e);
        }
        
        currentState = State.PARTS_REDACTED;
    }
    
    /**
     * Verify the originalSignature with the current public key.
     * @return True in case the signature does verify, false otherwise.
     */
    public synchronized boolean verifyOriginalSignature() {
        if (currentState != State.MESSAGE_SIGNED) {
            throw new IllegalStateException();
        }
        
        boolean doesVerify = false;
        try {
            signature.initVerify(keyPair.getPublic());
            doesVerify = signature.verify(originalSignature);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Signed message can not be verified.", e);
        }
        
        currentState = State.MESSAGE_VERIFIED;
        return doesVerify;
    }
    
    /**
     * Verify the currentSignature with the current public key.
     * @return True in case the signature does verify, false otherwise.
     */
    public synchronized boolean verifyCurrentSignature() {
        if (currentState != State.PARTS_REDACTED) {
            throw new IllegalStateException();
        }

        boolean doesVerify;
        try {
            signature.initVerify(keyPair.getPublic());
            doesVerify = signature.verify(currentSignature);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException("Invalid key for signature type.", e);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Redacted message can not be verified.", e);
        }
        
        currentState = State.REDACTED_VERIFIED;
        return doesVerify;
    }
    
    /*
     * **************************
     * ENUMS
     * **************************
     */
    
    /**
     * All states of the algorithmController.
     * @author Leon Shell, Lukas Krodinger
     */
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
     * @author Leon Shell, Lukas Krodinger
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
     * @author Leon Shell, Lukas Krodinger
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
    
    /*
     * **************************
     * Save/Load methods
     * **************************
     */

	/**
	 * Sets the given SignatureOutput. 
	 * Therewith also the messageParts, the redactableParts and the messageIdentifiers are affected.
	 * 
	 * @param signOut The SignatureOutput to set.
	 */
	private void setSignOut(SignatureOutput signOut) {
		assert signOut != null;
		
		this.originalSignature = signOut;
		this.currentSignature = signOut;
		
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
			setKeyInformation(information);
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
		if(currentSignature == null) {
			throw new IllegalStateException("SignOut must be initialized to store it.");
		}
		
		if(currentState != State.PARTS_REDACTED && currentState != State.REDACTED_VERIFIED) {
			throw new IllegalStateException("The redacted signature can not be saved before created.");
		}
		
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		persistence.saveSignatureOutput(currentSignature, path);	
	}
	
	/**
	 * Loads the signature output from the given file path. 
	 * 
	 * @param path The path to load the key from.
	 * @return The loaded message parts as Strings together with the information, which of them are redactable. Returns null in case the loading was not canceled.
	 * @throws FileNotFoundException In case the given path is invalid.
	 * @throws InvalidSignatureException In case the signature is not supported by the visualisation or the chosen key.
	 */
	public boolean loadSignature(String path) throws FileNotFoundException, InvalidSignatureException {
		if(currentState == State.START) {
			throw new IllegalStateException("The signature can not be loaded before the key is set.");
		}
		
		if(path == null || path.equals("")) {
			throw new IllegalArgumentException("Path must not be empty.");
		}
		
		SignatureOutput loadedSignOut = persistence.loadSignatureOutput(path);
			
		if(!loadedSignOut.getAlgorithmName().equals(keyType.shortName)) {
			throw new InvalidSignatureException("The loaded signature does not match the current key.");
		}
		
		if(loadedSignOut != null) {
			setSignOut(loadedSignOut);
			return true;
		}
		
		return false;
	}


}
