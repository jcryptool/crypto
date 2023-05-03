package org.jcryptool.visual.signalencryption.ui;

import org.jcryptool.visual.signalencryption.communication.EncryptionAlgorithm;
import org.jcryptool.visual.signalencryption.communication.SignalCommunication;
import org.jcryptool.visual.signalencryption.util.ToHex;

public class AlgorithmState {

    private static AlgorithmState instance;

    private SignalCommunication communication;
    private EncryptionAlgorithm signalEncryptionAlgorithm;

    private AlgorithmState() {
        signalEncryptionAlgorithm = new EncryptionAlgorithm();
        communication = new SignalCommunication(signalEncryptionAlgorithm);
    }

    public static AlgorithmState get() {
        if (instance == null) {
            instance = new AlgorithmState();
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    public EncryptionAlgorithm getSignalEncryptionAlgorithm() {
        return signalEncryptionAlgorithm;
    }

    public boolean allowMessageEntering() {
        return !communication.current().isAlreadyEncrypted();
    }

    public String getEncryptedMessage() {
        return ToHex.toHex(communication.current().getCiphertextMessage().orElse(new byte[] {}));
    }

    public String getPlainTextMessage() {
        return communication.current().getMessage();
    }

    public SignalCommunication getCommunication() {
        return communication;
    }

    public void resetCommunication() {
        communication = new SignalCommunication(signalEncryptionAlgorithm);
    }

}
