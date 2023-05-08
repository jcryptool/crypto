package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.state.PreKeyBundle;


/** A partially initialized bundle which can be used by the {@link FakeSignalServer} to build complete ones. */
public class PartialPreKeyBundle {

    private final int         registrationId;
    private final int         deviceId;
    private final int         signedPreKeyId;
    private final ECPublicKey signedPreKeyPublic;
    private final byte[]      signedPreKeySignature;
    private final IdentityKey identityKey;

    public PartialPreKeyBundle(
            int registrationId, int deviceId,
            int signedPreKeyId, ECPublicKey signedPreKeyPublic, byte[] signedPreKeySignature,
            IdentityKey identityKey
    ) {
        this.registrationId        = registrationId;
        this.deviceId              = deviceId;
        this.signedPreKeyId        = signedPreKeyId;
        this.signedPreKeyPublic    = signedPreKeyPublic;
        this.signedPreKeySignature = signedPreKeySignature;
        this.identityKey           = identityKey;
    }

    /** Fill in an optional one-time-pre-key and get a proper {@link PreKeyBundle}. */
    public PreKeyBundle fill(int preKeyId, ECPublicKey preKeyPublic) {
        return new PreKeyBundle(
                registrationId, deviceId,
                preKeyId, preKeyPublic,
                signedPreKeyId, signedPreKeyPublic, signedPreKeySignature,
                identityKey
        );
    }
}
