package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.SignalProtocolAddress;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.state.PreKeyBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A class which acts similar to a real signal server.
 * Allows to upload a PreKeyBundle along with one-time-pre-keys. When initiating a communication any entity
 * can come to this interface and retrieve a bundle. Delivered one-time-keys are deleted by the "server".
 */
public class FakeSignalServer {

    private FakeSignalServer() {
        // prevent instantiation of all-static class
    }

    private static final Map<SignalProtocolAddress, PartialPreKeyBundle> preKeyBundleStore = new HashMap<>();
    private static final Map<SignalProtocolAddress, SortedMap<Integer, ECPublicKey>> oneTimePreKeys = new HashMap<>();

    /**
     * Stores a partially initialized bundleTemplate along with the given oneTimePreKeys.
     * <p/>
     * Any other communication party can retrieve them now via {@link #retrievePreKeyBundle(SignalProtocolAddress)} and
     * chat with you.
     * */
    public static void uploadPreKeyBundle(
            SignalProtocolAddress address,
            PartialPreKeyBundle bundleTemplate,
            SortedMap<Integer, ECPublicKey> oneTimePreKeys
    ) {
        preKeyBundleStore.put(address, bundleTemplate);
        FakeSignalServer.oneTimePreKeys.put(address, oneTimePreKeys);
    }

    /** Gets a KeyBundle for a given address. Obviously this "server" is trustworthy, so no further checks required. */
    public static PreKeyBundle retrievePreKeyBundle(SignalProtocolAddress address) {
        int oneTimePreKeyId = -1;
        ECPublicKey oneTimePreKey = null;

        if (!oneTimePreKeys.get(address).isEmpty()) {
            oneTimePreKeyId = oneTimePreKeys.get(address).firstKey();
            oneTimePreKey = oneTimePreKeys.get(address).remove(oneTimePreKeyId);  // Removing here is important!
        }
        // the one-time-key may stay null, which is fine for the protocol. It's a bonus key.
        return preKeyBundleStore.get(address).fill(oneTimePreKeyId, oneTimePreKey);
    }
    
    /**
     * The same as {@link #retrievePreKeyBundle(SignalProtocolAddress)} but it does not delete the keys.
     * This is required for UI purposes.
     * */
    public static PreKeyBundle peekPreKeyBundle(SignalProtocolAddress address) {
        int oneTimePreKeyId = -1;
        ECPublicKey oneTimePreKey = null;

        if (!oneTimePreKeys.get(address).isEmpty()) {
            oneTimePreKeyId = oneTimePreKeys.get(address).firstKey();
            oneTimePreKey = oneTimePreKeys.get(address).get(oneTimePreKeyId);  // Removing here is important!
        }
        // the one-time-key may stay null, which is fine for the protocol. It's a bonus key.
        return preKeyBundleStore.get(address).fill(oneTimePreKeyId, oneTimePreKey);
    }

    /** Just for reference that PreKeyBundles can be revoked as well. */
    public static void revokePreKeyBundle(SignalProtocolAddress address) {
        preKeyBundleStore.remove(address);
        oneTimePreKeys.remove(address);
    }

    /** Just for reference that keys can be revoked. */
    public static void revokeOneTimePreKey(SignalProtocolAddress address, int oneTimePreKeyId) {
        oneTimePreKeys.get(address).remove(oneTimePreKeyId);
    }

    /** Just for reference that clients should upload new one-time-pre-keys from time to time. */
    public void publishOneTImePreKeys(SignalProtocolAddress address, SortedMap<Integer, ECPublicKey> freshOneTimePreKeys) {
        if (!preKeyBundleStore.containsKey(address)) {
            throw new IllegalArgumentException("Cannot publish keys if preKeyBundle does not exist.");
        }
        oneTimePreKeys.get(address).putAll(freshOneTimePreKeys);
    }

}
