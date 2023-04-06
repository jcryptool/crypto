package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.ecc.ECPrivateKey;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.kdf.DerivedRootSecrets;
import org.whispersystems.libsignal.ratchet.MessageKeys;

public class JCrypToolCapturer {
    // Diffie-Hellman-Ratchet
    public ECPublicKey publicDiffieHellmanRatchetKey;
    public ECPrivateKey privateDiffieHellmanRatchetKey;
    public byte[] diffieHellmanRatchetOutput;

    // Root Chain
    public byte[] rootChainKey;
    public byte[] rootChainConstantInput;
    public DerivedRootSecrets rootKdfOutput;
    public byte[] newRootChain;

    public SendReceiveChain sendChain = new SendReceiveChain();
    public SendReceiveChain receiveChain = new SendReceiveChain();

    public static class SendReceiveChain {
        // Send/Receive chain
        public byte[] chainKey;
        public byte[] chainConstantInput;
        public byte[] kdfOutput;
        public byte[] newChainKey;
        // MessageKey
        public MessageKeys messageKey;
    }
}
