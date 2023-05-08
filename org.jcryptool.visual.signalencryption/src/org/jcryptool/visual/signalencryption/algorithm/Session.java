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

public class Session {

    private PreSessionParameter parameters;
    private SessionBuilder session;

    private PreKeyBundle remotePreKeyBundle;
    private final SignalProtocolAddress remoteAddress;

    private SignedPreKeyStore signedPreKeyStore;
    private PreKeyStore preKeyStore;

    private Session(PreSessionParameter ownParameters, PreKeyBundle remotePreKeyBundle) {
        this.parameters = ownParameters;
        this.remoteAddress = ownParameters.getRemoteAddress();
        this.remotePreKeyBundle = remotePreKeyBundle;
    }

    public static Session initialize(PreSessionParameter parameters, PreKeyBundle remotePreKeyBundle) {
        return new Session(parameters, remotePreKeyBundle);
    }

    public SessionCipher buildSessionCipher(JCrypToolCapturer capturer) {

        session = parameters.getSession();

        signedPreKeyStore = parameters.getSignedPreKeyStore();
        preKeyStore = parameters.getPreKeyStore();

        for (int i = 0; i < 100; ++i) {
            signedPreKeyStore.storeSignedPreKey(
                    parameters.getParameter().getSignedPreKeyID(),
                    parameters.getParameter().getSignedPreKeyRecord()
            );
            preKeyStore.storePreKey(parameters.getParameter().getPreKeyID(), parameters.getPreKeyRecord());
            parameters.getParameter().nextPreKey();
        }

        try {
            session.process(remotePreKeyBundle, capturer);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UntrustedIdentityException e) {
            e.printStackTrace();
        }
        SessionCipher sessionCipher = new SessionCipher(parameters.getSessionStore(), parameters.getPreKeyStore(),
                parameters.getSignedPreKeyStore(), parameters.getIdentityKeyStore(), remoteAddress);

        return sessionCipher;
    }

    public SessionStore getSessionStore() {
        return parameters.getSessionStore();
    }

    public SignalProtocolAddress getRemoteAddress() {
        return remoteAddress;
    }
}
