package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.SessionBuilder;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.UntrustedIdentityException;
import org.whispersystems.libsignal.state.PreKeyBundle;
import org.whispersystems.libsignal.state.PreKeyStore;
import org.whispersystems.libsignal.state.SessionStore;
import org.whispersystems.libsignal.state.SignedPreKeyStore;

public class SessionInitialization {

    
    private PreSessionParameter signalSession;
    private SessionBuilder session;
    
    private PreKeyBundle remotePreKeyBundle;
    private final SignalProtocolAddress remoteAddress;
    
    private SignedPreKeyStore signedPreKeyStore;
    private PreKeyStore preKeyStore;
    
    
    public SessionInitialization(PreSessionParameter signalSession, PreKeyBundle remotePreKeyBundle) {
        this.remotePreKeyBundle = remotePreKeyBundle;
        this.signalSession = signalSession;
        this.remoteAddress = signalSession.getRemoteAddress();
    }
    
    public SessionCipher buildSessionCipher(JCrypToolCapturer capturer) {
        
        session = signalSession.getSession();
        
        signedPreKeyStore = signalSession.getSignedPreKeyStore();
        preKeyStore = signalSession.getPreKeyStore();
        
        signedPreKeyStore.storeSignedPreKey(
        		signalSession.getParameter().getSignedPreKeyID(),
        		signalSession.getParameter().getSignedPreKeyRecord()
        		);
        preKeyStore.storePreKey(signalSession.getParameter().getPreKeyID(), signalSession.getPreKeyRecord());
        
        try {
            session.process(remotePreKeyBundle, capturer);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UntrustedIdentityException e) {
            e.printStackTrace();
        }
        SessionCipher sessionCipher = new SessionCipher(signalSession.getSessionStore(), 
                signalSession.getPreKeyStore(), signalSession.getSignedPreKeyStore(), 
                signalSession.getIdentityKeyStore(), remoteAddress);
        
        return sessionCipher;
        }


    public SessionStore getSessionStore() {
        return signalSession.getSessionStore();
    }
    
    public SignalProtocolAddress getRemoteAddress() {
        return remoteAddress;
    }
}
