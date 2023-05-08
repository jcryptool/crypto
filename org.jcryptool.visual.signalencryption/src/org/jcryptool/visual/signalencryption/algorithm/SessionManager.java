package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.protocol.PreKeySignalMessage;
import org.whispersystems.libsignal.state.PreKeyBundle;
import org.whispersystems.libsignal.state.SessionStore;

public class SessionManager {

    private ParameterInitialization aliceParameter;
    private ParameterInitialization bobParameter;

    private final int ALICE_DEVICE_ID = 42;
    private final int BOB_DEVICE_ID = 43;

    private SignalProtocolAddress aliceRemoteAddress;
    private SignalProtocolAddress bobRemoteAddress;

    private PreSessionParameter alicePreSessionParameters;
    private PreSessionParameter bobPreSessionParameters;

    private Session aliceSession;
    private Session bobSession;

    private SessionCipher alice;
    private SessionCipher bob;

    public SessionManager newIdentities() {

        try {
            aliceParameter = new ParameterInitialization(ALICE_DEVICE_ID);
            FakeSignalServer.uploadPreKeyBundle(aliceParameter.getPreKeyBundle());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            bobParameter = new ParameterInitialization(BOB_DEVICE_ID);
            FakeSignalServer.uploadPreKeyBundle(bobParameter.getPreKeyBundle());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        aliceRemoteAddress = new SignalProtocolAddress("Alice", ALICE_DEVICE_ID);
        bobRemoteAddress = new SignalProtocolAddress("Bob", BOB_DEVICE_ID);
        //return initSession();
        return this;
    }

    public void newIdentityAlice() {
        try {
            aliceParameter = new ParameterInitialization(ALICE_DEVICE_ID);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        aliceRemoteAddress = new SignalProtocolAddress("Alice", ALICE_DEVICE_ID);
        //return initSession();
    }

    public void newIdentityBob() {
        try {
            bobParameter = new ParameterInitialization(BOB_DEVICE_ID);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        bobRemoteAddress = new SignalProtocolAddress("Bob", BOB_DEVICE_ID);
        //return initSession();
    }

    public JCrypToolCapturer alicesSession() {
        alicePreSessionParameters = aliceParameter.sessionParametersFor(bobRemoteAddress);
        aliceSession = Session.initialize(alicePreSessionParameters, bobPreSessionParameters.getPreKeyBundle());
        var capturer = new JCrypToolCapturer();
        alice = aliceSession.buildSessionCipher(capturer);
        return capturer;
    }

    public JCrypToolCapturer bobsSessionFromInitialMessage(PreKeySignalMessage message) {
        bobPreSessionParameters = bobParameter.sessionParametersFor(aliceRemoteAddress);
        bobSession = Session.initialize(
                bobPreSessionParameters,
                FakeSignalServer.retrievePreKeyBundle(message.getIdentityKey().getFingerprint())
        );
        var capturer = new JCrypToolCapturer();
        bob = bobSession.buildSessionCipher(capturer);
        return capturer;
    }


    // private Captures initSession() {
    //     alicePreSessionParameters = new PreSessionParameter(bobRemoteAddress, ALICE_DEVICE_ID, aliceParameter);
    //     bobPreSessionParameters = new PreSessionParameter(aliceRemoteAddress, BOB_DEVICE_ID, bobParameter);

    //     aliceSession = Session.initialize(alicePreSessionParameters, bobPreSessionParameters.getPreKeyBundle());
    //     bobSession = Session.initialize(bobPreSessionParameters, alicePreSessionParameters.getPreKeyBundle());

    //     var aliceCapturer = new JCrypToolCapturer();
    //     var bobCapturer = new JCrypToolCapturer();
    //     alice = aliceSession.buildSessionCipher(aliceCapturer);
    //     bob = bobSession.buildSessionCipher(bobCapturer);
    //     return new Captures(aliceCapturer, bobCapturer);

    // }

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
        return alicePreSessionParameters.getPreKeyBundle();
    }

    public PreKeyBundle getBobPreKeyBundle() {
        return bobPreSessionParameters.getPreKeyBundle();
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
