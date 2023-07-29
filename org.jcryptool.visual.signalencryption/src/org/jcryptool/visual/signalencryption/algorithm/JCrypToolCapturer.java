package org.jcryptool.visual.signalencryption.algorithm;

import org.whispersystems.libsignal.ecc.ECPrivateKey;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.kdf.DerivedRootSecrets;
import org.whispersystems.libsignal.ratchet.MessageKeys;

/** A data class which allows to store any relevant Double-Ratchet calculation values inside it. */
public class JCrypToolCapturer {
    /** An empty instance for representative usage only. */
    public static final JCrypToolCapturer DEFERED_CAPTURE = new JCrypToolCapturer();

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
