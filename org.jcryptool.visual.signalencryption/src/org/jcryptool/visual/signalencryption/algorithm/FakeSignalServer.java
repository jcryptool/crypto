package org.jcryptool.visual.signalencryption.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.whispersystems.libsignal.state.PreKeyBundle;

public class FakeSignalServer {

    private FakeSignalServer() {
        // prevent instantiation of all-static class
    }

    private static Map<String, PreKeyBundle> prekeyBundleStore = new HashMap<>();

    public static void uploadPreKeyBundle(PreKeyBundle bundle) {
        prekeyBundleStore.put(bundle.getIdentityKey().getFingerprint(), bundle);
    }

    public static PreKeyBundle retrievePreKeyBundle(String identityFingerprint) {
        return prekeyBundleStore.get(identityFingerprint);
    }

    public static void revokePreKeyBundle(String identityFingerprint) {
        prekeyBundleStore.remove(identityFingerprint);
    }

}
