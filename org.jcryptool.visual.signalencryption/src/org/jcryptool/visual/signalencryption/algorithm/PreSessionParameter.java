package org.jcryptool.visual.signalencryption.algorithm;

import java.util.List;

import org.whispersystems.libsignal.SessionBuilder;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.state.IdentityKeyStore;
import org.whispersystems.libsignal.state.PreKeyBundle;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.PreKeyStore;
import org.whispersystems.libsignal.state.SessionStore;
import org.whispersystems.libsignal.state.SignedPreKeyStore;
import org.whispersystems.libsignal.state.impl.*;



public class PreSessionParameter {
    
    
    private SessionStore sessionStore;
    private PreKeyStore preKeyStore;
    private SignedPreKeyStore signedPreKeyStore;
    private IdentityKeyStore  identityStore;
    
    private ParameterInitialization person;
    
    private SessionBuilder sessionBuilder;
    
    private SignalProtocolAddress address;
    private PreKeyBundle preKeyBundle;
    
    private ECPublicKey preKeyRecord;
    
    private final SignalProtocolAddress remoteAddress;

    
    public PreSessionParameter(SignalProtocolAddress remoteAddress, 
            int deviceId, ParameterInitialization person){

        this.person = person;
        this.remoteAddress = remoteAddress;
        this.sessionStore      = new InMemorySessionStore();
        this.preKeyStore       = new InMemoryPreKeyStore();
        this.signedPreKeyStore = new InMemorySignedPreKeyStore();
        this.identityStore     = new InMemoryIdentityKeyStore(person.getIdentityKeyPair(), 
                person.getRegistrationId());
        this.sessionBuilder = new SessionBuilder(sessionStore, preKeyStore, signedPreKeyStore,
                identityStore, remoteAddress);
        
        this.preKeyRecord = person.getPreKeys().get(person.getPreKeyID()).getKeyPair().getPublicKey();  
        
        this.preKeyBundle = new PreKeyBundle(person.getRegistrationId(), deviceId, 
               person.getPreKeyID() , preKeyRecord , person.getSignedPreKeyID(), 
               person.getSignedPublicPreKey(), person.getSignedPreKeyRecord().getSignature(), 
               person.getIdentityKey());
    }
    public SessionBuilder getSession() {
        return sessionBuilder;
    }

    public List<PreKeyRecord> getPersonalData() {
        return person.getPreKeys();
    }
    public PreKeyBundle getPreKeyBundle() {
        return preKeyBundle;
    }
    
    public SessionStore getSessionStore() {
        return sessionStore;
    }
    
    public PreKeyStore getPreKeyStore() {
        return preKeyStore;
    }
    
    public SignedPreKeyStore getSignedPreKeyStore() {
        return signedPreKeyStore;
    }
    
    public IdentityKeyStore getIdentityKeyStore() {
        return identityStore;
    }
    public SignalProtocolAddress getAddress() {
        return address;
    }
    public ParameterInitialization getParameter() {
        return person;
    }
    public PreKeyRecord getPreKeyRecord() {
        return person.getPreKeys().get(person.getPreKeyID());
    }
    public SignalProtocolAddress getRemoteAddress() {
        return remoteAddress;
    }
}
