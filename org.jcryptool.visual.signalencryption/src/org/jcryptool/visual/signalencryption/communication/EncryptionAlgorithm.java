package org.jcryptool.visual.signalencryption.communication;

import org.jcryptool.visual.signalencryption.algorithm.JCrypToolCapturer;
import org.jcryptool.visual.signalencryption.algorithm.SessionManager;
import org.whispersystems.libsignal.SessionCipher;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.protocol.PreKeySignalMessage;
import org.whispersystems.libsignal.state.PreKeyBundle;

/** Manages sessions, identites and keys. */
public class EncryptionAlgorithm {

    public SessionManager session;

    private SessionCipher bobSessionCipher;
    private SessionCipher aliceSessionCipher;
    private JCrypToolCapturer initialAliceCapture;

    private SignalProtocolAddress aliceAddress;
    private SignalProtocolAddress bobAddress;

    public EncryptionAlgorithm() {
        this.session = new SessionManager().newIdentities();
        this.initialAliceCapture = session.alicesSession();
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
        session.newIdentities();
        bobSessionCipher = session.getBobSessionCipher();
        aliceSessionCipher = session.getAliceSessionCipher();

        aliceAddress = session.getAliceAddress();
        bobAddress = session.getBobAddress();
    }

    public void generateAlice() {
        session.newIdentityAlice();

        aliceSessionCipher = session.getAliceSessionCipher();
        aliceAddress = session.getAliceAddress();
    }

    public void generateBob() {
        session.newIdentityBob();

        bobSessionCipher = session.getBobSessionCipher();
        bobAddress = session.getBobAddress();
    }

    public JCrypToolCapturer getInitialAliceCapture() {
        return initialAliceCapture;
    }

    public JCrypToolCapturer getBobsCaptureFromInitialMessage(PreKeySignalMessage message) {
        return session.bobsSessionFromInitialMessage(message);
    }

}
