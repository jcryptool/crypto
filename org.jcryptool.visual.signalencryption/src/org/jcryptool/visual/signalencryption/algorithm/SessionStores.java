package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.*;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.state.*;
import org.whispersystems.libsignal.state.impl.InMemoryIdentityKeyStore;
import org.whispersystems.libsignal.state.impl.InMemoryPreKeyStore;
import org.whispersystems.libsignal.state.impl.InMemorySessionStore;
import org.whispersystems.libsignal.state.impl.InMemorySignedPreKeyStore;

import java.util.TreeMap;

/**
 * Provides all four persistence interfaces to create sessions in libsignal.
 * Note that it uses InMemoryStores for simplicity reasons.
 */
public class SessionStores {

    private final SessionStore sessionStore;
    private final PreKeyStore preKeyStore;
    private final SignedPreKeyStore signedPreKeyStore;
    private final IdentityKeyStore identityKeyStore;

    /** Initializeds  */
    public SessionStores(AlgorithmBaseParameters parameters) {
        sessionStore = new InMemorySessionStore();
        preKeyStore = new InMemoryPreKeyStore();
        signedPreKeyStore = new InMemorySignedPreKeyStore();
        identityKeyStore = new InMemoryIdentityKeyStore(parameters.getIdentityKeyPair(), parameters.getRegistrationId());

        // Store the other keys as well
        getPreKeyStore().storePreKey(parameters.getPreKeyID(), parameters.getPreKeys().get(parameters.getPreKeyID()));
        getSignedPreKeyStore().storeSignedPreKey(parameters.getSignedPreKeyID(), parameters.getSignedPreKeyRecord());
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
        return identityKeyStore;
    }

    public SessionCipher createSession(
            PreKeyBundle theirPrekeyBundle,
            SignalProtocolAddress theirAddress,
            JCrypToolCapturer capturer
    ) throws UntrustedIdentityException, InvalidKeyException {
        SessionBuilder sessionBuilder = new SessionBuilder(
                getSessionStore(), getPreKeyStore(), getSignedPreKeyStore(), getIdentityKeyStore(), theirAddress
        );
        sessionBuilder.process(theirPrekeyBundle, capturer);
        return new SessionCipher(
                getSessionStore(), getPreKeyStore(), getSignedPreKeyStore(), getIdentityKeyStore(), theirAddress
        );
    }

    public void uploadPreKeyBundle(SignalProtocolAddress homeAddress, AlgorithmBaseParameters parameters) {
        var signedPreKeyRecord = parameters.getSignedPreKeyRecord();
        var partialPreKeyBundle = new PartialPreKeyBundle(
                parameters.getRegistrationId(),
                parameters.getDeviceId(),
                signedPreKeyRecord.getId(),
                signedPreKeyRecord.getKeyPair().getPublicKey(),
                signedPreKeyRecord.getSignature(),
                parameters.getIdentityKey()
        );

        int i = 0;
        var oneTimePreKeys = new TreeMap<Integer, ECPublicKey>();
        for (var oneTimePreKeyRecord : parameters.getPreKeys()) {
            getPreKeyStore().storePreKey(i, oneTimePreKeyRecord);
            oneTimePreKeys.put(i, oneTimePreKeyRecord.getKeyPair().getPublicKey());
            ++i;
        }
        FakeSignalServer.uploadPreKeyBundle(homeAddress, partialPreKeyBundle, oneTimePreKeys);
    }
}
