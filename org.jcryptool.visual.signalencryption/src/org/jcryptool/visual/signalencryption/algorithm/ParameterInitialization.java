package org.jcryptool.visual.signalencryption.algorithm;

import java.util.List;

import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import org.whispersystems.libsignal.util.KeyHelper;

public class ParameterInitialization {

    private IdentityKeyPair    identityKeyPair;
    private int                registrationId;
    private List<PreKeyRecord> preKeys;
    private SignedPreKeyRecord signedPreKey;
    
    private final int MAX_ID = 10;
    private final int ID = 0;
    
    
    public ParameterInitialization() throws InvalidKeyException {
        this.identityKeyPair = KeyHelper.generateIdentityKeyPair();
        this.registrationId  = KeyHelper.generateRegistrationId(true);
        this.preKeys         = KeyHelper.generatePreKeys(ID, MAX_ID);
        this.signedPreKey    = KeyHelper.generateSignedPreKey(identityKeyPair, registrationId);
    }
    
    public IdentityKey getIdentityKey() {
        return identityKeyPair.getPublicKey();
    }
    
    public int getRegistrationId() {
        return registrationId;
    }
    
    public List<PreKeyRecord> getPreKeys() {
        return preKeys;
    }
    
    public ECPublicKey getSignedPublicPreKey() {
        return signedPreKey.getKeyPair().getPublicKey();
    }
    public SignedPreKeyRecord getSignedPreKeyRecord() {
        return signedPreKey;
    }
    
    public int getSignedPreKeyID() {
        return signedPreKey.getId();
    }
    
    public int getPreKeyID() {
        return ID;
    }
    public IdentityKeyPair getIdentityKeyPair() {
        return identityKeyPair;
    }


}
