package org.jcryptool.visual.signalencryption.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jcryptool.core.logging.utils.LogUtil;
import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.state.PreKeyBundle;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import org.whispersystems.libsignal.util.KeyHelper;

public class ParameterInitialization {

    private IdentityKeyPair identityKeyPair;
    private int registrationId;
    private CircularLinkedList preKeys;

    private final int MAX_ID = 100;
    private Map<Integer, SignedPreKeyRecord> cache = new HashMap<>();
    private int deviceId;

    public ParameterInitialization(int deviceId) throws InvalidKeyException {
        this.deviceId = deviceId;
        this.identityKeyPair = KeyHelper.generateIdentityKeyPair();
        this.registrationId = KeyHelper.generateRegistrationId(true);

        this.preKeys = new CircularLinkedList(KeyHelper.generatePreKeys(0, MAX_ID));
    }

    public IdentityKey getIdentityKey() {
        return identityKeyPair.getPublicKey();
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public List<PreKeyRecord> getPreKeys() {
        return preKeys.prekeys;
    }

    public ECPublicKey getSignedPublicPreKey() {
        return getSignedPreKeyRecord().getKeyPair().getPublicKey();
    }

    public PreKeyBundle getPreKeyBundle() {
        var preKeyRecord = getPreKeys().get(getPreKeyID()).getKeyPair().getPublicKey();
        return new PreKeyBundle(getRegistrationId(), deviceId, getPreKeyID(), preKeyRecord,
                getSignedPreKeyID(), getSignedPublicPreKey(),
                getSignedPreKeyRecord().getSignature(), getIdentityKey());
    }

    public SignedPreKeyRecord getSignedPreKeyRecord() {
        return cache.computeIfAbsent(getPreKeyID(), (i) -> {
            try {
                return KeyHelper.generateSignedPreKey(identityKeyPair, i);
            } catch (InvalidKeyException e) {
                // ignored
                LogUtil.logError(e);
                return null;
            }
        });
    }

    public int getSignedPreKeyID() {
        return getSignedPreKeyRecord().getId();
    }

    public int getPreKeyID() {
        return preKeys.getId();
    }

    public IdentityKeyPair getIdentityKeyPair() {
        return identityKeyPair;
    }

    public void nextPreKey() {
        preKeys.next();
    }

    public PreSessionParameter sessionParametersFor(SignalProtocolAddress address) {
        return new PreSessionParameter(address, deviceId, this);
    }

    private class CircularLinkedList {

        private LinkedList<PreKeyRecord> prekeys;
        private int index = 0;

        CircularLinkedList(List<PreKeyRecord> prekeys) {
            assert !prekeys.isEmpty();
            this.prekeys = new LinkedList<>(prekeys);
        }

        public PreKeyRecord current() {
            return prekeys.get(index);
        }

        public PreKeyRecord next() {
            index++;
            if (index >= prekeys.size()) {
                index = 0;
            }
            return current();
        }
        public int getId() {
            return index;
        }

    }

}
