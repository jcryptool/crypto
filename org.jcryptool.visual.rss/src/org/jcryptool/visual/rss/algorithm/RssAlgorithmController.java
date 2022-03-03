package org.jcryptool.visual.rss.algorithm;

import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.persistence.InvalidSignatureException;
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
    private Scheme scheme;
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
        return new KeyInformation(scheme, keyLength, keyPair);
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
        return scheme.isOnlyRedactablePartsAllowed();
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
     * @param scheme The type of the algorithm.
     * @param keyGenType The key pair generator algorithm.
     * @param keyLength The length of the key.
     */
    public synchronized void generateKey(Scheme scheme, KeyPairGeneratorType keyGenType, KeyLength keyLength) {
        if (currentState != State.START) {
            throw new IllegalStateException();
        }
        
        try {
          	// Generate a new key using a KeyPairGenerator instance
        	KeyPairGenerator keyGen = KeyPairGenerator.getInstance(keyGenType.toString());
            keyGen.initialize(keyLength.getKl());
            KeyPair keyPair = keyGen.generateKeyPair();
            
            // Set the resulting keyInformation as the current one
            KeyInformation keyInformation = new KeyInformation(scheme, keyLength, keyPair);
            setKeyInformation(keyInformation);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("There is no implementation for the given key type.");
        }
        
        currentState = State.KEY_SET;
    }
    
    public static Scheme getScheme(AlgorithmType algorithmType, HashMethod hashMethod, Accumulator accumulator, UnderlayingSignatureScheme underlayingSignatureScheme) {
    	Scheme scheme = null;
    	
    	switch(algorithmType) {
		case GLRSS:
			switch(hashMethod) {
			case SHA_256:
					scheme = Scheme.GLRSS_WITH_BPA_AND_SHA256_WITH_RSA;
				break;
			case SHA_512:
					scheme = Scheme.GLRSS_WITH_BPA_AND_SHA512_WITH_RSA;
				break;
			default:
				break;
			}
			break;
			
		case GSRSS:
			switch(hashMethod) {
			case SHA_256:
					scheme = Scheme.GSRSS_WITH_BPA_AND_SHA256_WITH_RSA;
				break;
			case SHA_512:
					scheme = Scheme.GSRSS_WITH_BPA_AND_SHA512_WITH_RSA;
				break;
			default:
				break;
			}
			break;
			
    	case GC:
    		switch(hashMethod) {
			case SHA_256:
					scheme = Scheme.GC_WITH_RSA_AND_SHA256;
				break;
			case SHA_512:
					scheme = Scheme.GC_WITH_RSA_AND_SHA512;
				break;
			default:
				break;
			}
    		break;
    		
		case MERSA:
			switch(hashMethod) {
			case SHA_256:
					scheme = Scheme.MERSA_WITH_RSA_AND_SHA256;
				break;
			case SHA_512:
					scheme = Scheme.MERSA_WITH_RSA_AND_SHA512;
				break;
			default:
				break;
			}
			break;
			
		default:
			break;
    	}
    	
    	return scheme;
    }
    
    public static KeyPairGeneratorType getKeyGenType(AlgorithmType algorithmType, MaxMessageParts maxMessageParts) {
    	KeyPairGeneratorType keyGenType = null;
    	
    	KeyPairGeneratorType[] supportedKeyPairGenerators = algorithmType.getSupportedKeyPairGenerators();
    	if(supportedKeyPairGenerators.length == 1) {
    		keyGenType = supportedKeyPairGenerators[0];
    	}
    	
    	if(algorithmType == AlgorithmType.MERSA) {
    		switch(maxMessageParts){
			case M_1024:
				keyGenType = KeyPairGeneratorType.MERSA1024;
				break;
			case M_16:
				keyGenType = KeyPairGeneratorType.MERSA16;
				break;
			case M_8:
				keyGenType = KeyPairGeneratorType.MERSA8;
				break;
			default:
				break;
    		}
    	}
    	
    	return keyGenType;
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
    	this.signature = RedactableSignature.getInstance(information.scheme.getSignatureType());
    	
    	// Set all information about the key and the key pair itself
    	this.keyLength = information.keyLength;
    	this.keyPair = information.keyPair;
    	this.scheme = information.scheme;
    	
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
     * @throws RedactableSignatureException In case the signature could not be signed for some reason.
     * @throws InvalidKeyException In case the key is invalid (for example if the lenght of the hash value is longer than the key size)
     */
    public synchronized void signMessage(List<MessagePart> messageParts) throws RedactableSignatureException, InvalidKeyException {
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
       	
    	// Create signature for message
        signature.initSign(keyPair);
        MessagePart messagePart;
        for (int i = 0; i < originalMessages.size(); i++) {
        	messagePart = originalMessages.get(i);
            signature.addPart(messagePart.getMessageBytes(), messagePart.isRedactable());
        }
        originalSignature = signature.sign();
        currentSignature = originalSignature;
                
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
     * @throws InvalidKeyException In case the key is invalid (for example if the lenght of the hash value is longer than the key size)
     */
    public synchronized void redactMessage(List<MessagePart> messagePartsToRedact) throws InvalidKeyException {
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
          
        } catch (RedactableSignatureException e) {
            throw new IllegalArgumentException("Message can not be redacted at given indices.", e);
        }
        
        currentState = State.PARTS_REDACTED;
    }
    
    /**
     * Verify the originalSignature with the current public key.
     * @return True in case the signature does verify, false otherwise.
     * @throws InvalidKeyException In case the key is invalid (for example if the lenght of the hash value is longer than the key size)
     */
    public synchronized boolean verifyOriginalSignature() throws InvalidKeyException {
        if (currentState != State.MESSAGE_SIGNED) {
            throw new IllegalStateException();
        }
        
        boolean doesVerify = false;
        try {
            signature.initVerify(keyPair.getPublic());
            doesVerify = signature.verify(originalSignature);
        } catch (RedactableSignatureException e) {
            throw new IllegalStateException("Signed message can not be verified.", e);
        }
        
        currentState = State.MESSAGE_VERIFIED;
        return doesVerify;
    }
    
    /**
     * Verify the currentSignature with the current public key.
     * @return True in case the signature does verify, false otherwise.
     * @throws InvalidKeyException In case the key is invalid (for example if the lenght of the hash value is longer than the key size)

     */
    public synchronized boolean verifyCurrentSignature() throws InvalidKeyException {
        if (currentState != State.PARTS_REDACTED) {
            throw new IllegalStateException();
        }

        boolean doesVerify;
        try {
            signature.initVerify(keyPair.getPublic());
            doesVerify = signature.verify(currentSignature);
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
     * TODO: Remove KeyPairGenerator from scheme
     * 
     * @author Leon Shell, Lukas Krodinger
     */
    public enum Scheme {
    	GSRSS_WITH_BPA_AND_SHA256_WITH_RSA(AlgorithmType.GSRSS, "GSRSSwithBPAandSHA256withRSA"),
    	GSRSS_WITH_BPA_AND_SHA512_WITH_RSA(AlgorithmType.GSRSS, "GSRSSwithBPAandSHA512withRSA"),
    	//GSRSS_WITH_BPA_AND_SHA3256_WITH_RSA(AlgorithmType.GSRSS, "GSRSSwithBPAandSHA3256withRSA"),
    	//GSRSS_WITH_BPA_AND_SHA3512_WITH_RSA(AlgorithmType.GSRSS, "GSRSSwithBPAandSHA3512withRSA"),
    	
    	GLRSS_WITH_BPA_AND_SHA256_WITH_RSA(AlgorithmType.GLRSS, "GLRSSwithBPAandSHA256withRSA"),
    	GLRSS_WITH_BPA_AND_SHA512_WITH_RSA(AlgorithmType.GLRSS, "GLRSSwithBPAandSHA512withRSA"),
    	//GLRSS_WITH_BPA_AND_SHA256_WITH_RSA(AlgorithmType.GLRSS, "GLRSSwithBPAandSHA3256withRSA"),
    	//GLRSS_WITH_BPA_AND_SHA512_WITH_RSA(AlgorithmType.GLRSS, "GLRSSwithBPAandSHA3512withRSA"),
    	
    	GC_WITH_RSA_AND_SHA256(AlgorithmType.GC, "GCwithRSAandSHA256"),
    	GC_WITH_RSA_AND_SHA512(AlgorithmType.GC, "GCwithRSAandSHA512"),
    	//GC_WITH_RSA_AND_SHA3256(AlgorithmType.GC, "GCwithRSAandSHA3256"),
    	//GC_WITH_RSA_AND_SHA3512(AlgorithmType.GC, "GCwithRSAandSHA3512"),

    	MERSA_WITH_RSA_AND_SHA256(AlgorithmType.MERSA, "MERSAwithRSAandSHA256"),
    	MERSA_WITH_RSA_AND_SHA512(AlgorithmType.MERSA, "MERSAwithRSAandSHA512");
    	//MERSA_WITH_RSA_AND_SHA3256(AlgorithmType.MERSA, "MERSAwithRSAandSHA3256"),
    	//MERSA_WITH_RSA_AND_SHA3512(AlgorithmType.MERSA, "MERSAwithRSAandSHA3512"),  	
    	    	
        private final AlgorithmType algorithmType;
        private final String signatureType;
        
        Scheme(AlgorithmType algorithmType, String signatureType) {
            this.algorithmType = algorithmType;
            this.signatureType = signatureType;
        }
        
        public AlgorithmType getAlgorithmType() {
            return algorithmType;
        }
        
        public String getSignatureType() {
        	return signatureType;
        }
        
        public boolean isOnlyRedactablePartsAllowed() {
        	return algorithmType.isOnlyRedactablePartsAllowed();
        }          
        
    }
    
    public enum AlgorithmType {
    	GLRSS(Descriptions.GLRSS, "GLRSS", false, KeyPairGeneratorType.GLRSS),
    	GSRSS(Descriptions.GSRSS, "GSRSS", false, KeyPairGeneratorType.GSRSS),
    	GC(Descriptions.GC, "Generic Construction", true, KeyPairGeneratorType.RSA),
    	MERSA(Descriptions.Mersa, "SBZ02-MersaProd", false, KeyPairGeneratorType.MERSA8, KeyPairGeneratorType.MERSA16, KeyPairGeneratorType.MERSA1024);
    	
    	private final String displayName;
    	private final String algorithmName;
    	private final boolean onlyRedactablePartsAllowed;
    	private final KeyPairGeneratorType[] supportedKeyPairGenerators;
    	
    	AlgorithmType(String displayName, String algorithmName, boolean onlyRedactablePartsAllowed, KeyPairGeneratorType... supportedKeyPairGenerators){
    		this.displayName = displayName;
    		this.algorithmName = algorithmName;
    		this.onlyRedactablePartsAllowed = onlyRedactablePartsAllowed;
    		this.supportedKeyPairGenerators = supportedKeyPairGenerators;
    	}
    	
    	public String getAlgorithmName() {
    		return algorithmName;
    	}
    	
    	public KeyPairGeneratorType[] getSupportedKeyPairGenerators(){
    		return supportedKeyPairGenerators;
    	}
    	
        public boolean isOnlyRedactablePartsAllowed() {
        	return onlyRedactablePartsAllowed;
        }   
    	
        @Override
        public String toString() {
        	return displayName;
        }
        
        public static AlgorithmType fromString(String text) {
            for (AlgorithmType algorithmType : AlgorithmType.values()) {
                if (algorithmType.displayName.equalsIgnoreCase(text)) {
                    return algorithmType;
                }
            }
            return null;
        }
    }
    
    public enum KeyPairGeneratorType {
    	GLRSS,
    	GSRSS,
    	RSA,
    	MERSA8,
    	MERSA16,
    	MERSA1024;
    }
        
    /**
     * The available key lengths for the generated keys.
     * @author Leon Shell, Lukas Krodinger
     */
    public enum KeyLength {
        KL_512(512),
        KL_1024(1024),
        KL_2048(2048);
        //KL_4096(4096);
        
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
     * The available hash methods.
     * @author Lukas Krodinger
     */
    public enum HashMethod {
        //SHA3_256,
        //SHA3_512,
        SHA_256,
    	SHA_512;
    }
    
    /**
     * The available max message parts.
     * @author Lukas Krodinger
     */
    public enum MaxMessageParts {
    	 M_8(8),
         M_16(16),
         M_1024(1024);
         
         private final int maxMessageParts;
         
         MaxMessageParts(int maxMessageParts) {
             this.maxMessageParts = maxMessageParts;
         }
         
         public int getKl() {
             return maxMessageParts;
         }
         
         @Override
         public String toString(){
         	return String.valueOf(maxMessageParts);
         }

 		public static MaxMessageParts getItem(String maxMessageParts) {
 			return valueOf("M_"+maxMessageParts);
 		}
    }
    
    /**
     * The available accumulator variants.
     * @author Lukas Krodinger
     */
    public enum Accumulator {
        BPA(Descriptions.BPA);
    	
    	private final String displayName;
    	
    	Accumulator(String displayName){
    		this.displayName = displayName;
    	}
    	
        @Override
        public String toString() {
        	return displayName;
        }
        
        public static Accumulator fromString(String text) {
            for (Accumulator accumulator : Accumulator.values()) {
                if (accumulator.displayName.equalsIgnoreCase(text)) {
                    return accumulator;
                }
            }
            return null;
        }
    }
    
    /**
     * The available accumulator variants.
     * @author Lukas Krodinger
     */
    public enum UnderlayingSignatureScheme {
        RSA;
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
	public void saveOriginalSignature(String path) throws FileNotFoundException {
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
	public void saveCurrentSignature(String path) throws FileNotFoundException {
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
			
		if(!loadedSignOut.getAlgorithmName().equals(scheme.getAlgorithmType().getAlgorithmName())) {
			throw new InvalidSignatureException("The loaded signature does not match the current key.");
		}
		
		if(loadedSignOut != null) {
			setSignOut(loadedSignOut);
			return true;
		}
		
		return false;
	}


}
