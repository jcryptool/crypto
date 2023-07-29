package org.jcryptool.visual.signalencryption.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jcryptool.visual.signalencryption.exceptions.SignalAlgorithmException;
import org.whispersystems.libsignal.IdentityKey;
import org.whispersystems.libsignal.IdentityKeyPair;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.state.PreKeyRecord;
import org.whispersystems.libsignal.state.SignedPreKeyRecord;
import org.whispersystems.libsignal.util.KeyHelper;

/** Stores basic keys and parameters of a communication entity. */
public class AlgorithmBaseParameters {

    private static final int MAX_ID = 100;

    private final IdentityKeyPair identityKeyPair;
    private final int registrationId;
    private final CircularLinkedList preKeys;
    private final int deviceId;

    private final Map<Integer, SignedPreKeyRecord> cache = new HashMap<>();


    public AlgorithmBaseParameters(int deviceId) throws InvalidKeyException {
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

    public SignedPreKeyRecord getSignedPreKeyRecord() throws SignalAlgorithmException {
        return cache.computeIfAbsent(getPreKeyID(), (i) -> {
            try {
                return KeyHelper.generateSignedPreKey(identityKeyPair, i);
            } catch (InvalidKeyException e) {
                throw new SignalAlgorithmException();
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

    public int getDeviceId() {
        return deviceId;
    }

    private static class CircularLinkedList {


        private final LinkedList<PreKeyRecord> prekeys;
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
