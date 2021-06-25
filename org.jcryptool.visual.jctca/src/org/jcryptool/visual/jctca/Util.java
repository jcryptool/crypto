//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2013, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.visual.jctca;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.crypto.keystore.backend.KeyStoreManager;
import org.jcryptool.crypto.keystore.keys.IKeyStoreAlias;
import org.jcryptool.crypto.keystore.keys.KeyType;
import org.jcryptool.visual.jctca.CertificateClasses.CRLEntry;
import org.jcryptool.visual.jctca.CertificateClasses.CSR;
import org.jcryptool.visual.jctca.CertificateClasses.CertificateCSRR;

public class Util {

	public static X509Certificate certificateForKeyPair(CSR csr, BigInteger serialNumber, X509Certificate caCert,
			Date expiryDate, Date startDate, PrivateKey caKey) {

		KeyStoreManager mng = KeyStoreManager.getInstance();
		try {
			PublicKey pub = mng.getCertificate(csr.getPubAlias()).getPublicKey();
			PrivateKey priv = mng.getPrivateKey(csr.getPrivAlias(), KeyStoreManager.KEY_PASSWORD);
			KeyPair keypair = new KeyPair(pub, priv);

			return Util.certificateForKeyPair(csr.getFirst() + " " + csr.getLast(), csr.getCountry(), csr.getStreet(),
					csr.getZip(), csr.getTown(), "", "", csr.getMail(), keypair, serialNumber, caCert, expiryDate,
					startDate, caKey);
		} catch (UnrecoverableEntryException e) {
			LogUtil.logError(e);
		} catch (NoSuchAlgorithmException e) {
			LogUtil.logError(e);
		}

		return null;

	}

	public static X509Certificate certificateForKeyPair(String principal, String country, String street, String zip,
			String city, String unit, String organisation, String mail, KeyPair keyPair, BigInteger serialNumber,
			X509Certificate caCert, Date expiryDate, Date startDate, PrivateKey caKey) {
		try {

			AlgorithmIdentifier sigID = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA512withRSA");
			AlgorithmIdentifier digID = new DefaultDigestAlgorithmIdentifierFinder().find(sigID);

			AsymmetricKeyParameter privateKeyAsymKeyParam = PrivateKeyFactory
					.createKey(keyPair.getPrivate().getEncoded());

			ContentSigner bcrsasigner = new BcRSAContentSignerBuilder(sigID, digID).build(privateKeyAsymKeyParam);

			X500Name issuer = new X500Name(caCert.getIssuerX500Principal().getName(X500Principal.RFC1779));

			SubjectPublicKeyInfo pki = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());

			X500Name subjectName = new X500Name(
					"CN=" + principal + ", " + "ST=" + street + ", " + "L=" + zip + " " + city + ", " + "C=" + country
							+ ", " + "OU=" + unit + ", " + "O=" + organisation + ", " + "E=" + mail);

			X509v3CertificateBuilder builder = new X509v3CertificateBuilder(issuer, serialNumber, startDate, expiryDate,
					subjectName, pki);

			JcaX509ExtensionUtils extUtils = new JcaX509ExtensionUtils();

			builder.addExtension(org.bouncycastle.asn1.x509.Extension.authorityKeyIdentifier, false,
					extUtils.createAuthorityKeyIdentifier(caCert));
			builder.addExtension(org.bouncycastle.asn1.x509.Extension.subjectKeyIdentifier, false,
					extUtils.createSubjectKeyIdentifier(keyPair.getPublic()));

			X509CertificateHolder certifiacteHolder = builder.build(bcrsasigner);
			JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
			X509Certificate cert = converter.getCertificate(certifiacteHolder);

			return cert;

		} catch (CertificateEncodingException cee) {
			LogUtil.logError(Activator.PLUGIN_ID, cee);
		} catch (IllegalStateException ise) {
			LogUtil.logError(Activator.PLUGIN_ID, ise);
		} catch (NoSuchAlgorithmException nsae) {
			LogUtil.logError(Activator.PLUGIN_ID, nsae);
		} catch (CertificateParsingException cpe) {
			LogUtil.logError(Activator.PLUGIN_ID, cpe);
		} catch (IOException ioe) {
			LogUtil.logError(Activator.PLUGIN_ID, ioe);
		} catch (OperatorCreationException oce) {
			LogUtil.logError(Activator.PLUGIN_ID, oce);
		} catch (CertificateException ce) {
			LogUtil.logError(Activator.PLUGIN_ID, ce);
		}

		return null;
	}

	/**
	 * Converts an asymmetric Keypair to a (sun) KeyPair.
	 * 
	 * @param keypair The asymmetric keypair to be converted.
	 * @return a sun keypair or null, if something went wrong
	 */
	public static KeyPair asymmetricKeyPairToNormalKeyPair(AsymmetricCipherKeyPair keypair) {
		RSAKeyParameters publicKey = (RSAKeyParameters) keypair.getPublic();
		RSAPrivateCrtKeyParameters privateKey = (RSAPrivateCrtKeyParameters) keypair.getPrivate();

		// JCE format needed for the certificate - because
		// getEncoded() is necessary...
		PublicKey pubKey;
		try {
			pubKey = KeyFactory.getInstance("RSA").generatePublic(//$NON-NLS-1$
					new RSAPublicKeySpec(publicKey.getModulus(), publicKey.getExponent()));
			PrivateKey privKey = KeyFactory.getInstance("RSA").generatePrivate(//$NON-NLS-1$
					new RSAPrivateCrtKeySpec(publicKey.getModulus(), publicKey.getExponent(), privateKey.getExponent(),
							privateKey.getP(), privateKey.getQ(), privateKey.getDP(), privateKey.getDQ(),
							privateKey.getQInv()));

			return new KeyPair(pubKey, privKey);
		} catch (InvalidKeySpecException e) {
			LogUtil.logError(e);
		} catch (NoSuchAlgorithmException e) {
			LogUtil.logError(e);
		}
		
		return null;
	}

	public static void createCARootNodes(Tree tree) {
		TreeItem tree_item_csr = new TreeItem(tree, SWT.NONE);
		tree_item_csr.setText(Messages.Util_CSR_Tree_Head);
		TreeItem tree_item_crl = new TreeItem(tree, SWT.NONE);
		tree_item_crl.setText(Messages.Util_RR_Tree_Head);

		tree.getItems()[0].setExpanded(true);
		tree.getItems()[1].setExpanded(true);
	}

	public static void create2ndUserRootNodes(Tree tree) {
		TreeItem tree_item_csr = new TreeItem(tree, SWT.NONE);
		tree_item_csr.setText(Messages.Util_signed_texts);
		TreeItem tree_item_crl = new TreeItem(tree, SWT.NONE);
		tree_item_crl.setText(Messages.Util_signed_files);

		tree.getItems()[0].setExpanded(true);
		tree.getItems()[1].setExpanded(true);
	}

	/**
	 * Find all RSA public keys in a given keystore ksm and return them in an array
	 * of KeyStoreAlias. omits JCT-CA Root Certificates
	 * 
	 * @param ksm - KeyStoreManager from where to get the keys
	 * @return ArrayList of all KeyStoreAlias containing either RSA or DSA,
	 *         excluding JCT-CA Root Certificates public key pairs
	 */
	public static ArrayList<KeyStoreAlias> getAllRSAPublicKeys(KeyStoreManager ksm) {
		ArrayList<KeyStoreAlias> RSAAndDSAPublicKeys = new ArrayList<KeyStoreAlias>();
		for (IKeyStoreAlias ksAlias : ksm.getAllPublicKeys()) {
			if (ksAlias.getOperation().contains("RSA")//$NON-NLS-1$
					&& (ksAlias.getKeyStoreEntryType() == KeyType.KEYPAIR_PUBLIC_KEY)
					&& !(ksAlias.getContactName().contains("JCT-PKI Root Certificates"))) {//$NON-NLS-1$
				if (ksAlias instanceof KeyStoreAlias) {
					RSAAndDSAPublicKeys.add((KeyStoreAlias) ksAlias);
				}
			}
		}
		return RSAAndDSAPublicKeys;
	}

	public static void showMessageBox(String title, String text, int type) {
		MessageBox box = new MessageBox(Display.getCurrent().getActiveShell(), type);
		box.setText(title);
		box.setMessage(text);
		box.open();
	}

	public static boolean isSignedByJCTCA(KeyStoreAlias ksAlias) {
		KeyStoreManager ksm = KeyStoreManager.getInstance();
		X509Certificate pubKey = null;
		try {
			pubKey = (X509Certificate) ksm.getCertificate(ksAlias);
		} catch (UnrecoverableEntryException e) {
			LogUtil.logError(e);
		} catch (NoSuchAlgorithmException e) {
			LogUtil.logError(e);
		}
		// create X500Name from the X509 certificate Subjects distinguished name
		X500Name x500name = new X500Name(pubKey.getIssuerDN().toString());
		RDN rdn = x500name.getRDNs(BCStyle.OU)[0];
		if (rdn.getFirst().getValue().toString().equals("JCT-CA Visual")) {//$NON-NLS-1$
			return true;
		} else {
			return false;
		}
	}

	public static boolean isCertificateRevoked(BigInteger serialNumber) {
		ArrayList<CRLEntry> crl = CertificateCSRR.getInstance().getRevoked();
		for (CRLEntry crle : crl) {
			if (serialNumber.compareTo(crle.GetSerial()) == 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean isDateBeforeRevocation(BigInteger serialNumber, Date signDate) {
		ArrayList<CRLEntry> crl = CertificateCSRR.getInstance().getRevoked();
		for (CRLEntry crle : crl) {
			if (serialNumber.compareTo(crle.GetSerial()) == 0) {
				Date revokeDate = crle.getRevokeTime();
				if (signDate.before(revokeDate)) {
					return true;
				}
			}
		}
		return false;
	}

	public static KeyStoreAlias getAliasForHash(String hash) {
		KeyStoreManager mng = KeyStoreManager.getInstance();
		for (IKeyStoreAlias al : mng.getAllPublicKeys()) {
			if (al.getHashValue().compareTo(hash) == 0) {
				if (al instanceof KeyStoreAlias) {
					return (KeyStoreAlias) al;
				}
			}
		}
		return null;
	}

	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String formatHash(String hash) {
		char[] help = hash.toCharArray();
		String formatted = "";
		int i = 0;
		for (char c : help) {
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
				formatted += c;
				i++;
				if (i % 2 == 0) {
					formatted += "-";
				}
			}
		}
		if (formatted.charAt(formatted.length() - 1) == '-') {
			formatted = formatted.substring(0, formatted.length() - 1);
		}
		if (formatted.charAt(0) == '-') {
			formatted = formatted.substring(1);
		}
		return formatted;
	}

}
