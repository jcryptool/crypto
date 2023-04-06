package org.jcryptool.visual.signalencryption.ui;


import org.jcryptool.visual.signalencryption.algorithm.SessionManager;
import org.jcryptool.visual.signalencryption.algorithm.SessionManager.Captures;
import org.jcryptool.visual.signalencryption.ui.AlgorithmState.STATE;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.state.PreKeyBundle;


public class EncryptionAlgorithm {

    public SessionManager session;
    
    private SessionCipher bobSessionCipher;
    private SessionCipher aliceSessionCipher;
	private Captures currentInitializationCaptures;

    private SignalProtocolAddress aliceAddress;
    private SignalProtocolAddress bobAddress;
    
    public EncryptionAlgorithm() {
        this.session = new SessionManager();
        this.currentInitializationCaptures = session.createSessionBoth();
        this.bobSessionCipher = session.getBobSessionCipher();
        this.aliceSessionCipher = session.getAliceSessionCipher();
        
        this.aliceAddress = session.getAliceAddress();
        this.bobAddress = session.getBobAddress();
    }

    public PreKeyBundle getAlicePreKeyBundle() {
        return session.getAlicePreKeyBundle();
    }
    public PreKeyBundle getBobPreKeyBundle() {
        return session.getBobPreKeyBundle();
    }
    public SessionManager getSession() {
        return session;
    }
    public SessionCipher getBobSessionCipher() {
        return bobSessionCipher;
    }
    public SessionCipher getAliceSessionCipher() {
        return aliceSessionCipher;
    }
    
    public SignalProtocolAddress getAliceAddress() {
        return aliceAddress;
    }
    
    public SignalProtocolAddress getBobAddress() {
        return bobAddress;
    }
    
    public void generateBoth() {
        session.createSessionBoth();
        bobSessionCipher = session.getBobSessionCipher();
        aliceSessionCipher = session.getAliceSessionCipher();
        
        aliceAddress = session.getAliceAddress();
        bobAddress = session.getBobAddress();
    }
    
    public void generateAlice() {
        session.createSessionAlice();
        
        aliceSessionCipher = session.getAliceSessionCipher();
        aliceAddress = session.getAliceAddress();
    }
    
    public void generateBob() {
        session.createSessionBob();
        
        bobSessionCipher = session.getBobSessionCipher();
        bobAddress = session.getBobAddress();
    }
    
	public Captures getInitializationCaptures() {
		return currentInitializationCaptures;
	}

}
