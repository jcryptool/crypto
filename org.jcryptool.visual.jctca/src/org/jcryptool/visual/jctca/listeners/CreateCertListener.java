// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2013, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.jctca.listeners;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Calendar;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.crypto.keystore.backend.KeyStoreManager;
import org.jcryptool.crypto.keystore.certificates.CertificateFactory;
import org.jcryptool.crypto.keystore.keys.KeyType;
import org.jcryptool.visual.jctca.Util;
import org.jcryptool.visual.jctca.CertificateClasses.RegistrarCSR;

/**
 * listens on the components in the Create Certificate view from the user
 * 
 * @author mmacala
 * 
 */
public class CreateCertListener implements SelectionListener {
    private KeyStoreManager mng = KeyStoreManager.getInstance();
    private Text txt_first_name, txt_last_name, txt_street, txt_zip, txt_town, txt_country, txt_mail;
    private Combo cmb_keys;
    private String path;
    private Button btn_radio_gen_key;

    /**
     * Constructor with all the widgets it needs to have accessible
     * 
     * @param first_name
     * @param last_name
     * @param street
     * @param zip
     * @param town
     * @param country
     * @param mail
     * @param keys
     */
    public CreateCertListener(Text first_name, Text last_name, Text street, Text zip, Text town, Text country,
            Text mail, Combo keys, Button btn_radio_gen_key) {
        this.txt_first_name = first_name;
        this.txt_last_name = last_name;
        this.txt_street = street;
        this.txt_zip = zip;
        this.txt_town = town;
        this.txt_country = country;
        this.txt_mail = mail;
        this.cmb_keys = keys;
        this.btn_radio_gen_key = btn_radio_gen_key;
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

    @Override
    public void widgetSelected(SelectionEvent arg0) {
        Button src = (Button) arg0.getSource();
        Integer data = (Integer) src.getData();
        int pressed = data.intValue();// 0 - btn_proof, 1 - btn_genKey, 2 -
                                      // btn_send_csr

        switch (pressed) {
        case 0:
            FileDialog f = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
            f.setFilterExtensions(new String[] { "*.jpg", "*.gif", "*.bmp", "*.gif" });//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            path = f.open();
            if (path != null) {
                src.setText(path);
            }
            break;
        case 1:
            if (checkFields()) {
                generateNewRSAKeyPair();
            } else {
                Util.showMessageBox(Messages.CreateCertListener_msgbox_title_not_all_fields_set,
                        Messages.CreateCertListener_msgbox_text_not_all_fields_set, SWT.ICON_INFORMATION);
            }
            break;
        case 2:
            if (checkFields()) {
                if (btn_radio_gen_key.getSelection()) {
                    generateNewRSAKeyPair();
                }
                sendCSR();
            } else {
                Util.showMessageBox(Messages.CreateCertListener_msgbox_title_not_all_fields_set,
                        Messages.CreateCertListener_msgbox_text_not_all_fields_set, SWT.ICON_INFORMATION);
            }
        }
    }

    /**
     * sends the csr to the RA
     */
    private void sendCSR() {
        KeyStoreAlias pubAlias = null;
        KeyStoreAlias privAlias = null;

        pubAlias = (KeyStoreAlias) cmb_keys.getData(cmb_keys.getItem(cmb_keys.getSelectionIndex()));
        privAlias = mng.getPrivateForPublic(pubAlias);
        RegistrarCSR.getInstance().addCSR(txt_first_name.getText(), txt_last_name.getText(), txt_street.getText(),
                txt_zip.getText(), txt_town.getText(), txt_country.getText(), txt_mail.getText(), path, pubAlias,
                privAlias, Calendar.getInstance().getTime());
        Util.showMessageBox(Messages.CreateCertListener_msgbox_title_csr_to_ca,
                Messages.CreateCertListener_msgbox_text_csr_to_ca, SWT.ICON_INFORMATION);
    }

    /**
     * generates a new Key Pair for the user with the information provided in the other fields
     */
    private void generateNewRSAKeyPair() {
        RSAKeyPairGenerator gen = new RSAKeyPairGenerator();
        SecureRandom sr = new SecureRandom();
        gen.init(new RSAKeyGenerationParameters(BigInteger.valueOf(3), sr, 2048, 80));
        AsymmetricCipherKeyPair keypair = gen.generateKeyPair();
        RSAKeyParameters publicKey = (RSAKeyParameters) keypair.getPublic();
        RSAPrivateCrtKeyParameters privateKey = (RSAPrivateCrtKeyParameters) keypair.getPrivate();
        try {
            // JCE format needed for the certificate - because
            // getEncoded() is necessary...
            PublicKey pubKey = KeyFactory.getInstance("RSA")//$NON-NLS-1$
                    .generatePublic(new RSAPublicKeySpec(publicKey.getModulus(), publicKey.getExponent()));
            // and this one for the KeyStore
            PrivateKey privKey = KeyFactory.getInstance("RSA")//$NON-NLS-1$
                    .generatePrivate(
                            new RSAPrivateCrtKeySpec(publicKey.getModulus(), publicKey.getExponent(), privateKey
                                    .getExponent(), privateKey.getP(), privateKey.getQ(), privateKey.getDP(),
                                    privateKey.getDQ(), privateKey.getQInv()));
            String name = txt_first_name.getText() + " " //$NON-NLS-1$
                    + txt_last_name.getText();
            KeyStoreAlias privAlias = new KeyStoreAlias(name, KeyType.KEYPAIR_PRIVATE_KEY, "RSA", 2048, //$NON-NLS-1$
                    (name.concat(privKey.toString())).hashCode() + " ",//$NON-NLS-1$
                    privKey.getClass().getName());
            KeyStoreAlias pubAlias = new KeyStoreAlias(name, KeyType.KEYPAIR_PUBLIC_KEY, "RSA", 2048,//$NON-NLS-1$
                    (name.concat(privKey.toString())).hashCode() + " ",//$NON-NLS-1$
                    pubKey.getClass().getName());
            mng.addKeyPair(privKey, CertificateFactory.createJCrypToolCertificate(pubKey),
                    KeyStoreManager.KEY_PASSWORD, privAlias, pubAlias);
            String entry = pubAlias.getContactName() + " (Hash: " + Util.formatHash(pubAlias.getHashValue()) + ")";//$NON-NLS-1$ //$NON-NLS-2$
            cmb_keys.add(entry);
            cmb_keys.getParent().layout();
            cmb_keys.select(cmb_keys.getItemCount() - 1);
            cmb_keys.setData(entry, pubAlias);
        } catch (InvalidKeySpecException e) {
            LogUtil.logError(e);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.logError(e);
        }
    }

    /**
     * checks if all the fields neccessary for a CSR are set
     * 
     * @return true if all information is provided
     */
    public boolean checkFields() {
        String first = txt_first_name.getText();
        String last = txt_last_name.getText();
        String street = txt_street.getText();
        String zip = txt_zip.getText();
        String town = txt_town.getText();
        String country = txt_country.getText();
        String mail = txt_mail.getText();
        return first.length() != 0 && last.length() != 0 && street.length() != 0 && zip.length() != 0
                && town.length() != 0 && country.length() != 0 && mail.length() != 0;
    }

}
