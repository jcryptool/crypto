/**
 * Copyright (C) 2014-2016 Open Whisper Systems
 *
 * Licensed according to the LICENSE file in this repository.
 */
package org.whispersystems.libsignal.ratchet;

import org.jcryptool.visual.signalencryption.algorithm.JCrypToolCapturer;
import org.whispersystems.libsignal.InvalidKeyException;
import org.whispersystems.libsignal.ecc.Curve;
import org.whispersystems.libsignal.ecc.ECKeyPair;
import org.whispersystems.libsignal.ecc.ECPublicKey;
import org.whispersystems.libsignal.kdf.DerivedRootSecrets;
import org.whispersystems.libsignal.kdf.HKDF;
import org.whispersystems.libsignal.util.ByteUtil;
import org.whispersystems.libsignal.util.Pair;

public class RootKey {

  private final HKDF   kdf;
  private final byte[] key;

  public RootKey(HKDF kdf, byte[] key) {
    this.kdf = kdf;
    this.key = key;
  }

  public byte[] getKeyBytes() {
    return key;
  }

  public Pair<RootKey, ChainKey> createChain(ECPublicKey theirRatchetKey, ECKeyPair ourRatchetKey, JCrypToolCapturer capturer, JCrypToolCapturer.SendReceiveChain sendReceiveCapturer)
      throws InvalidKeyException
  {
    byte[]             constantInput      = "WhisperRatchet".getBytes();
    byte[]             sharedSecret       = Curve.calculateAgreement(theirRatchetKey, ourRatchetKey.getPrivateKey());
    byte[]             derivedSecretBytes = kdf.deriveSecrets(sharedSecret, key, constantInput, DerivedRootSecrets.SIZE);
    DerivedRootSecrets derivedSecrets     = new DerivedRootSecrets(derivedSecretBytes);

    capturer.publicDiffieHellmanRatchetKey = theirRatchetKey;
    capturer.privateDiffieHellmanRatchetKey = ourRatchetKey.getPrivateKey();
    capturer.diffieHellmanRatchetOutput = sharedSecret;
    capturer.rootChainKey = key;
    capturer.rootChainConstantInput = constantInput;
    capturer.rootKdfOutput = derivedSecrets;

    RootKey  newRootKey  = new RootKey(kdf, derivedSecrets.getRootKey());
    ChainKey newChainKey = new ChainKey(kdf, derivedSecrets.getChainKey(), 0);

    // Note here as it is slightly confusing.
    // There are variables called newRootKey and newChainKey. However, in the observer context,
    // the newRootKey is the output of the first root-chain ratcheting step, while
    // newChainKey is the initial value for the Send/Receive chain
    capturer.newRootChain = newRootKey.getKeyBytes();
    sendReceiveCapturer.chainKey = derivedSecrets.getChainKey();

    return new Pair<>(newRootKey, newChainKey);
  }
}
