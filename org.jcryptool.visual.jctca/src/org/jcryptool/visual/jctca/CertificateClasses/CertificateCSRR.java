// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2013, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.jctca.CertificateClasses;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.jcryptool.core.logging.utils.LogUtil;
import org.jcryptool.crypto.keystore.backend.KeyStoreAlias;
import org.jcryptool.crypto.keystore.backend.KeyStoreManager;
import org.jcryptool.crypto.keystore.keys.IKeyStoreAlias;
import org.jcryptool.crypto.keystore.keys.KeyType;
import org.jcryptool.visual.jctca.Activator;
import org.jcryptool.visual.jctca.Util;

/**
 * Class containing everything regarding CSRs that have been sent to the CA,
 * Revocation Requests, the CRL, CA-Certificates+Keys and the CRL.
 * 
 * Accessible from everywhere via .getInstance()
 * 
 * @author mmacala
 * 
 */

public class CertificateCSRR {

	/**
	 * the object instance of this class
	 */
	private static CertificateCSRR instance = null;

	/**
	 * the CSRs that have been approved by the RA
	 */
	private ArrayList<CSR> approved_csrs;

	/**
	 * revocation requests sent from users
	 */
	private ArrayList<RR> revRequests;

	/**
	 * the keys from the CA to sign certificates
	 */
	private ArrayList<PrivateKey> caKeys;

	/**
	 * the certificates from the CA
	 */
	private ArrayList<X509Certificate> certs;

	/**
	 * the CRL containing all revoked Certificates
	 */
	private ArrayList<CRLEntry> crl;

	/**
	 * contains all generated signatures
	 */
	private ArrayList<Signature> sigList;

	/**
	 * private constructor, use getInstance()
	 */
	private CertificateCSRR() {
		approved_csrs = new ArrayList<CSR>();
		revRequests = new ArrayList<RR>();
		caKeys = new ArrayList<PrivateKey>();
		certs = new ArrayList<X509Certificate>();
		crl = new ArrayList<CRLEntry>();
		sigList = new ArrayList<Signature>();
		checkCertificatesAndCRL();
	}

	/**
	 * Get the Instance of this class
	 * 
	 * @return Instance of CertificateCSRR
	 */
	public static CertificateCSRR getInstance() {
		if (instance == null) {
			instance = new CertificateCSRR();
		}
		return instance;
	}

	/**
	 * Checks if there are root certificates in the keystore. if not, creates and
	 * adds them to the keystore. Also loads the revoked requests from the keystore
	 * and adds them to the CRL.
	 */
	public void checkCertificatesAndCRL() {
		boolean certsExist = false;
		KeyStoreManager mng = KeyStoreManager.getInstance();
		// iterate through all public key aliases
		for (IKeyStoreAlias pubAlias : mng.getAllPublicKeys()) {
			if (pubAlias.getContactName().contains("JCT-PKI Root Certificates")) {//$NON-NLS-1$
				// root certificates have been found, do not create new ones
				certsExist = true;
				java.security.cert.Certificate c = null;
				try {
					c = mng.getCertificate(pubAlias);
				} catch (UnrecoverableEntryException e) {
					LogUtil.logError(e);
				} catch (NoSuchAlgorithmException e) {
					LogUtil.logError(e);
				}
				if (c instanceof X509Certificate) {

					try {
						KeyStoreAlias pk = mng.getPrivateForPublic(pubAlias);

						PrivateKey k = mng.getPrivateKey(pk, KeyStoreManager.KEY_PASSWORD);

						caKeys.add(k);

					} catch (UnrecoverableEntryException uee) {
						LogUtil.logError(Activator.PLUGIN_ID, uee);
					} catch (NoSuchAlgorithmException nsae) {
						LogUtil.logError(Activator.PLUGIN_ID, nsae);
					}

					certs.add((X509Certificate) c);
				}
			} else if (pubAlias.getContactName().contains("JCT-PKI Certificate Revocation List")) {//$NON-NLS-1$
				// revoked certificates have been found. add them to the CRL-ArrayList
				java.security.cert.Certificate c = null;
				try {
					c = mng.getCertificate(pubAlias);
				} catch (UnrecoverableEntryException e) {
					LogUtil.logError(e);
				} catch (NoSuchAlgorithmException e) {
					LogUtil.logError(e);
				}
				if (c instanceof X509Certificate) {
					long time = Long.parseLong(pubAlias.getOperation());
					X509Certificate cert = (X509Certificate) c;
					crl.add(new CRLEntry(cert.getSerialNumber(), new Date(time)));
				}
			}
		}
		if (!certsExist) {
			// no certificates exist, create new ones

			// Schlüsselerzeugung hier nachgebaut:
			// https://www.programcreek.com/java-api-examples/?api=org.bouncycastle.operator.bc.BcRSAContentSignerBuilder

			// GENERATE THE PUBLIC/PRIVATE RSA KEY PAIR
			RSAKeyPairGenerator gen = new RSAKeyPairGenerator();
			SecureRandom sr = new SecureRandom();
			gen.init(new RSAKeyGenerationParameters(BigInteger.valueOf(3), sr, 2048, 80));

			AsymmetricCipherKeyPair keypair = null;
			for (int i = 0; i < 2; i++) {

				try {
					// generates 5 new self signed certificates and adds them to the keystore
					keypair = gen.generateKeyPair();
					KeyPair kp = Util.asymmetricKeyPairToNormalKeyPair(keypair);

//              	ASN1ObjectIdentifier obj = new ASN1ObjectIdentifier("1.2.840.113549.1.1");
					AlgorithmIdentifier sigID = new DefaultSignatureAlgorithmIdentifierFinder()
							.find("SHA256WithRSAEncryption");
					AlgorithmIdentifier digID = new DefaultDigestAlgorithmIdentifierFinder().find(sigID);

					AsymmetricKeyParameter privateKeyAsymKeyParam = PrivateKeyFactory
							.createKey(kp.getPrivate().getEncoded());

					ContentSigner bcrsasigner = new BcRSAContentSignerBuilder(sigID, digID)
							.build(privateKeyAsymKeyParam);

					// yesterday
					Date validityBeginDate = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
					// in 10 years
					Date validityEndDate = new Date(System.currentTimeMillis() + 10 * 365 * 24 * 60 * 60 * 1000);
					X500Name issuer = new X500Name("CN=JCrypTool, O=JCrypTool, OU=JCT-CA Visual");
					BigInteger serial = BigInteger.valueOf(System.currentTimeMillis());
					// Self signed Certificates are created. The issuer and the subject are
					// therefore the same.
					X500Name subject = new X500Name("CN=JCrypTool, O=JCrypTool, OU=JCT-CA Visual");
					SubjectPublicKeyInfo pki = SubjectPublicKeyInfo.getInstance(kp.getPublic().getEncoded());

					X509v3CertificateBuilder builder = new X509v3CertificateBuilder(issuer, serial, validityBeginDate,
							validityEndDate, subject, pki);

					X509CertificateHolder certifiacteHolder = builder.build(bcrsasigner);
					JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
					X509Certificate cert = converter.getCertificate(certifiacteHolder);
					caKeys.add(kp.getPrivate());
					certs.add(cert);
					KeyStoreAlias pubAlias = new KeyStoreAlias("JCT-PKI Root Certificates", KeyType.KEYPAIR_PUBLIC_KEY, //$NON-NLS-1$
							"RSA", 2048, kp.getPrivate().hashCode() + "", kp.getPublic().getClass().toString());//$NON-NLS-1$ //$NON-NLS-2$
					KeyStoreAlias privAlias = new KeyStoreAlias("JCT-PKI Root Certificates", //$NON-NLS-1$
							KeyType.KEYPAIR_PRIVATE_KEY, "RSA", 2048, kp.getPrivate().hashCode() + "", //$NON-NLS-1$ //$NON-NLS-2$
							kp.getPrivate().getClass().toString());
					mng.addKeyPair(kp.getPrivate(), cert, KeyStoreManager.KEY_PASSWORD, privAlias, pubAlias);

				} catch (IOException ioe) {
					LogUtil.logError(Activator.PLUGIN_ID, ioe);
				} catch (OperatorCreationException oce) {
					LogUtil.logError(Activator.PLUGIN_ID, oce);
				} catch (CertificateException ce) {
					LogUtil.logError(Activator.PLUGIN_ID, ce);
				}
			}
		}
	}

	/**
	 * adds a CSR to the list, will then be shown in the CA view as an approved CSR
	 * 
	 * @param c - the CSR to be added
	 */
	public void addCSR(CSR c) {
		approved_csrs.add(c);
	}

	/**
	 * arraylist containing the approved
	 * 
	 * @return arraylist with the CSRs
	 */
	public ArrayList<CSR> getApproved() {
		return approved_csrs;
	}

	/**
	 * private ca-key for the index
	 * 
	 * @param i - the index
	 * @return private key
	 */
	public PrivateKey getCAKey(int i) {
		return caKeys.get(i);
	}

	/**
	 * public ca-key for the index
	 * 
	 * @param i - the index
	 * @return public key
	 */
	public X509Certificate getCACert(int i) {
		return certs.get(i);
	}

	/**
	 * list containing the revocation requests
	 * 
	 * @return the revocation requests
	 */
	public ArrayList<RR> getRevocations() {
		return revRequests;
	}

	/**
	 * list containing all private ca-keys
	 * 
	 * @return the private ca-keys
	 */
	public ArrayList<PrivateKey> getCAKeys() {
		return caKeys;
	}

	/**
	 * removes a csr from the approved CSR-List
	 * 
	 * @param i - the index
	 */
	public void removeCSR(int i) {
		this.approved_csrs.remove(i);
	}

	/**
	 * removes a csr from the approved CSR-List
	 * 
	 * @param c - the csr object from the list, that should be removed
	 */
	public void removeCSR(CSR c) {
		this.approved_csrs.remove(c);
	}

	/**
	 * removes a rr from the RR-List
	 * 
	 * @param r - the revocation request object from the list, that should be
	 *          removed
	 */
	public void removeRR(RR r) {
		this.revRequests.remove(r);
	}

	/**
	 * gets the current CRL
	 * 
	 * @return the CRL
	 */
	public ArrayList<CRLEntry> getCRL() {
		return crl;
	}

	/**
	 * adds the given rr to the rr-List
	 * 
	 * @param rr - the rr to be added
	 */
	public void addRR(RR rr) {
		this.revRequests.add(rr);
	}

	/**
	 * adds the given crl-entry to the crl-list
	 * 
	 * @param crle - the crl-entry to be added
	 */
	public void addCRLEntry(CRLEntry crle) {
		this.crl.add(crle);
	}

	/**
	 * gets the list containing the revoked certificates
	 * 
	 * @return the revoked certificates
	 */
	public ArrayList<CRLEntry> getRevoked() {
		return crl;
	}

	/**
	 * gets the list containing the generated signatures
	 */
	public ArrayList<Signature> getSignatures() {
		return sigList;
	}

	/**
	 * adds a signature to the list of signatures
	 * 
	 * @param sig - the signature to be added
	 */
	public void addSignature(Signature sig) {
		this.sigList.add(sig);
	}

	/**
	 * removes a signature from the list of signatures
	 * 
	 * @param sig - the signature to be deleted form the list
	 */
	public void removeSignature(Signature sig) {
		this.sigList.remove(sig);
	}
}
