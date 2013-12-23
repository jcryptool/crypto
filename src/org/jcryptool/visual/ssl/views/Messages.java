package org.jcryptool.visual.ssl.views;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "org.jcryptool.visual.ssl.views.messages"; //$NON-NLS-1$
	
	public static String ServerHelloCompositeBtnInfo;
	public static String ServerHelloCompositeBtnGenerate;
	public static String ServerHelloCompositeGrpServerHello;
	public static String ServerHelloCompositeLblVersion;
	public static String ServerHelloCompositeLblRandom;
	public static String ServerHelloCompositeLblCipherSuite;
	public static String ServerHelloCompositeLblSessionID;
	public static String ServerHelloCompositeBtnNextStep;
	public static String ServerHelloInformationText;
		
	public static String ClientCertificateCompositeGrpClientCertificate;
	public static String ClientCertificateCompositeLblCertifcate;
	public static String ClientCertificateCompositeLblClientKeyExchange;
	public static String ClientCertificateCompositeLblCertificateVerify;
	public static String ClientCertificateCompositeBtnInfo;
	public static String ClientCertificateCompositeBtnShow;
	public static String ClientCertificateCompositeBtnNextStep;
	public static String ClientCertificateInformationText;
	
	public static String ServerCertificateCompositeServerCertificate;
	public static String ServerCertificateCompositeLblCertificateRequest;
	public static String ServerCertificateCompositeLblCertificate;
	public static String ServerCertificateCompositeLblServerKeyExchange;
	public static String ServerCertificateCompositeLblServerHelloDone;
	public static String ServerCertificateCompositeRdbYes;
	public static String ServerCertificateCompositeRdbNo;
	public static String ServerCertificateCompositeBtnShow;
	public static String ServerCertificateCompositeBtnInfo;
	public static String ServerCertificateCompositeBtnNextStep;
	public static String ServerCertificateInformationText;
	
	public static String ClientHelloCompositeLblClientHello;
	public static String ClientHelloCompositeLblVersion;
	public static String ClientHelloCompositeLblRandom;
	public static String ClientHelloCompositeLblCipherSuit;
	public static String ClientHelloCompositeLblSessionId;
	public static String ClientHelloCompositeLblSessionIdValue;
	public static String ClientHelloCompositeBtnGenerate;
	public static String ClientHelloCompositeBtnInformation;
	public static String ClientHelloCompositeBtnNextStep;
	public static String ClientHelloComposite_group_text;
	public static String ClientHelloInformationText;
	public static String ServerHelloCompositeError;
	public static String ServerHelloCompositeErrorSessionID;
	public static String ServerHelloCompositeErrorRandom;
	public static String ServerHelloCompositeErrorRandomShort;

	public static String ServerChangeCipherSpecCompositeLblServerChangeCipher;
	public static String ServerChangeCipherSpecCompositeLblServerChangeCipherSpec;
	public static String ServerChangeCipherSpecCompositeLblFinished;
	public static String ServerChangeCipherSpecCompositeBtnInformation;
	public static String ServerChangeCipherSpecCompositeBtnNextStep;
	public static String ServerChangeCipherSpecInformationText;

	public static String ClientChangeCipherSpecCompositeLblClientChangeCipher;
	public static String ClientChangeCipherSpecCompositeLblClientChangeCipherSpec;
	public static String ClientChangeCipherSpecCompositeLblFinished;
	public static String ClientChangeCipherSpecCompositeBtnInformation;
	public static String ClientChangeCipherSpecCompositeBtnNextStep;
	public static String ClientChangeCipherSpecInformationText;
	
	public static String ServerFinishedCompositeBtnInformation;
	public static String ServerFinishedCompositeLblFinished;
	public static String ServerFinishedCompositeGrpServerFinished;

	public static String ClientFinishedCompositeBtnInformation;
	public static String ClientFinishedCompositeLblFinished;
	public static String ClientFinishedCompositeGrpServerFinished;
	public static String ClientFinishedInformationText;
	public static String ServerFinishedInformationText;
	
	public static String SslViewPlugin;
	public static String SslViewLblClient;
	public static String SslViewLblServer;
	public static String SslViewLblInfo;
	public static String SslViewBtnPreviousStep;
	public static String SslViewBtnNextStep;
	public static String SslViewBtnReset;
	public static String SslViewStxInformation;
	
	public static String Tls0;
	public static String Tls1;
	public static String Tls2;
	
	public static String AttacksCaution;
	public static String AttacksProceed;
	public static String AttacksSHA1;
	public static String AttacksMD5;
	public static String AttacksBEAST;
	public static String AttacksRC4;
	public static String AttacksDES;
	public static String AttacksNoCipher;
	public static String AttacksLucky13;
	public static String AttacksRSA;
	
	public static String TLS0_RSA_WITH_NULL_MD5;
	public static String TLS0_RSA_WITH_NULL_SHA;
	public static String TLS0_RSA_WITH_RC4_128_MD5;
	public static String TLS0_RSA_WITH_RC4_128_SHA;
	public static String TLS0_RSA_WITH_DES_CBC_SHA;
	public static String TLS0_RSA_WITH_3DES_EDE_CBC_SHA;
	public static String TLS0_DH_DSS_WITH_DES_CBC_SHA;
	public static String TLS0_DH_DSS_WITH_3DES_EDE_CBC_SHA;
	public static String TLS0_DH_RSA_WITH_DES_CBC_SHA;
	public static String TLS0_DH_RSA_WITH_3DES_EDE_CBC_SHA;
	public static String TLS0_DHE_DSS_WITH_DES_CBC_SHA;
	public static String TLS0_DHE_DSS_WITH_3DES_EDE_CBC_SHA;
	public static String TLS0_DHE_RSA_WITH_DES_CBC_SHA;
	public static String TLS0_DHE_RSA_WITH_3DES_EDE_CBC_SHA;
	
	public static String TLS1_RSA_WITH_AES_128_CBC_SHA; 
	public static String TLS1_DH_DSS_WITH_AES_128_CBC_SHA;
	public static String TLS1_DH_RSA_WITH_AES_128_CBC_SHA;
	public static String TLS1_DHE_DSS_WITH_AES_128_CBC_SHA;
	public static String TLS1_DHE_RSA_WITH_AES_128_CBC_SHA;
	public static String TLS1_RSA_WITH_AES_256_CBC_SHA;
	public static String TLS1_DH_DSS_WITH_AES_256_CBC_SHA;
	public static String TLS1_DH_RSA_WITH_AES_256_CBC_SHA;
	public static String TLS1_DHE_DSS_WITH_AES_256_CBC_SHA;
	public static String TLS1_DHE_RSA_WITH_AES_256_CBC_SHA;
	
	public static String TLS2_RSA_WITH_NULL_MD5;
	public static String TLS2_RSA_WITH_NULL_SHA;
	public static String TLS2_RSA_WITH_NULL_SHA256; 
	public static String TLS2_RSA_WITH_RC4_128_MD5;
	public static String TLS2_RSA_WITH_RC4_128_SHA;
	public static String TLS2_RSA_WITH_3DES_EDE_CBC_SHA;
	public static String TLS2_RSA_WITH_AES_128_CBC_SHA;
	public static String TLS2_RSA_WITH_AES_256_CBC_SHA;
	public static String TLS2_RSA_WITH_AES_128_CBC_SHA256;
	public static String TLS2_RSA_WITH_AES_256_CBC_SHA256;

	public static String TLS2_DH_DSS_WITH_3DES_EDE_CBC_SHA;
	public static String TLS2_DH_RSA_WITH_3DES_EDE_CBC_SHA;
	public static String TLS2_DHE_DSS_WITH_3DES_EDE_CBC_SHA;
	public static String TLS2_DHE_RSA_WITH_3DES_EDE_CBC_SHA;
	public static String TLS2_DH_DSS_WITH_AES_128_CBC_SHA;
	public static String TLS2_DH_RSA_WITH_AES_128_CBC_SHA;
	public static String TLS2_DHE_DSS_WITH_AES_128_CBC_SHA;
	public static String TLS2_DHE_RSA_WITH_AES_128_CBC_SHA;
	public static String TLS2_DH_DSS_WITH_AES_256_CBC_SHA;
	public static String TLS2_DH_RSA_WITH_AES_256_CBC_SHA;
	public static String TLS2_DHE_DSS_WITH_AES_256_CBC_SHA;
	public static String TLS2_DHE_RSA_WITH_AES_256_CBC_SHA;
	public static String TLS2_DH_DSS_WITH_AES_128_CBC_SHA256;
	public static String TLS2_DH_RSA_WITH_AES_128_CBC_SHA256; 
	public static String TLS2_DHE_DSS_WITH_AES_128_CBC_SHA256;
	public static String TLS2_DHE_RSA_WITH_AES_128_CBC_SHA256;
	public static String TLS2_DH_DSS_WITH_AES_256_CBC_SHA256;
	public static String TLS2_DH_RSA_WITH_AES_256_CBC_SHA256;
	public static String TLS2_DHE_DSS_WITH_AES_256_CBC_SHA256;
	public static String TLS2_DHE_RSA_WITH_AES_256_CBC_SHA256;
	public static String TLS2_RSA_WITH_AES_128_GCM_SHA256;
	public static String TLS2_RSA_WITH_AES_256_GCM_SHA384;
	public static String TLS2_DHE_RSA_WITH_AES_128_GCM_SHA256;
	public static String TLS2_DHE_RSA_WITH_AES_256_GCM_SHA384;
	public static String TLS2_DH_RSA_WITH_AES_128_GCM_SHA256;
	public static String TLS2_DH_RSA_WITH_AES_256_GCM_SHA384;
	public static String TLS2_DHE_DSS_WITH_AES_128_GCM_SHA256;
	public static String TLS2_DHE_DSS_WITH_AES_256_GCM_SHA384;
	public static String TLS2_DH_DSS_WITH_AES_128_GCM_SHA256;
	public static String TLS2_DH_DSS_WITH_AES_256_GCM_SHA384;
	public static String CertificateShow_btnISee_text;
	public static String CertificateShow_grpCertificate_text;
	public static String CertificateShow_text_text;
	public static String stxInformationCipherSuitesExchanged;
	public static String stxInformationSelectedCiphers;
	public static String stxInformationRandomValue;
	public static String stxInformationTLS1;
	public static String stxInformationTLS2;
	public static String stxInformationCipherSuiteRefused1;
	public static String stxInformationCipherSuiteRefused2;
	
	static 
	{
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	
	private Messages() 
	{
		
	}
}
