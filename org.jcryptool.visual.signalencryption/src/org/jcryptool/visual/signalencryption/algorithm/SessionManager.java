package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.state.PreKeyBundle;
import org.whispersystems.libsignal.state.SessionStore;


public class SessionManager {
    
    private ParameterInitialization aliceParameter;
    private ParameterInitialization bobParameter;
    
    private final int ALICE_DEVICE_ID = 42;
    private final int BOB_DEVICE_ID = 43;
    
    private SignalProtocolAddress aliceRemoteAddress;
    private SignalProtocolAddress bobRemoteAddress;
    
    private PreSessionParameter aliceBuiltSession;
    private PreSessionParameter bobBuiltSession;
    
    private SessionInitialization aliceSession;
    private SessionInitialization bobSession;
    
    private SessionCipher alice;
    private SessionCipher bob;
    
    
    public Captures createSessionBoth() {
        
        try {
            aliceParameter = new ParameterInitialization();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        
        try {
            bobParameter = new ParameterInitialization();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        
        aliceRemoteAddress = new SignalProtocolAddress("Alice", ALICE_DEVICE_ID);
        bobRemoteAddress = new SignalProtocolAddress("Bob", BOB_DEVICE_ID);
        return initSession();
    }
    
    public Captures createSessionAlice() {
        try {
            aliceParameter = new ParameterInitialization();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        
        aliceRemoteAddress = new SignalProtocolAddress("Alice", ALICE_DEVICE_ID);
        return initSession();
    }
    
    public Captures createSessionBob() {
        try {
            bobParameter = new ParameterInitialization();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        bobRemoteAddress = new SignalProtocolAddress("Bob", BOB_DEVICE_ID);
        return initSession();
    }
    
    private Captures initSession() {
    	aliceBuiltSession = new PreSessionParameter(bobRemoteAddress, ALICE_DEVICE_ID, aliceParameter);
        bobBuiltSession = new PreSessionParameter(aliceRemoteAddress, BOB_DEVICE_ID, bobParameter);
        
        aliceSession = new SessionInitialization(aliceBuiltSession, bobBuiltSession.getPreKeyBundle());
        bobSession = new SessionInitialization(bobBuiltSession, aliceBuiltSession.getPreKeyBundle());

        var aliceCapturer = new JCrypToolCapturer();
        var bobCapturer = new JCrypToolCapturer();
        alice = aliceSession.buildSessionCipher(aliceCapturer);
        bob = bobSession.buildSessionCipher(bobCapturer);
        return new Captures(aliceCapturer, bobCapturer);

    }
    
    public SessionCipher getAliceSessionCipher() {
        return alice;
    }
    public SessionCipher getBobSessionCipher() {
        return bob;
    }
    public SessionStore getAliceSessionStore() {
        return aliceSession.getSessionStore();
    }
    public SessionStore getBobSessionStore() {
        return bobSession.getSessionStore();
    }
    
    public SignalProtocolAddress getBobAddress() {
        return aliceSession.getRemoteAddress();
    }
    public SignalProtocolAddress getAliceAddress() {
        return bobSession.getRemoteAddress();
    }
    public PreKeyBundle getAlicePreKeyBundle() {
        return aliceBuiltSession.getPreKeyBundle();
    }
    public PreKeyBundle getBobPreKeyBundle() {
        return bobBuiltSession.getPreKeyBundle();
    }

    /** Simple Dataclass holding algorithm values for both Alice and bob */
    public static class Captures {
        private JCrypToolCapturer aliceCapture;
        private JCrypToolCapturer bobCapture;

        public Captures(JCrypToolCapturer aliceCapture, JCrypToolCapturer bobCapture) {
            this.aliceCapture = aliceCapture;
            this.bobCapture = bobCapture;
        }

        public JCrypToolCapturer getAliceCapture() {
            return aliceCapture;
        }

        public JCrypToolCapturer getBobCapture() {
            return bobCapture;
        }
    }



}
