package org.jcryptool.visual.rabin;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.jcryptool.core.operations.editors.AbstractEditorService;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.visual.rabin.ui.RabinFirstTabComposite;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;

/**
 * @author rico
 *
 */
public class GUIHandler {
	
	private GUIHandler guiHandler;
	private ScrolledComposite scMain;
	private Composite compMain;
	private int limitExp = 1024;
	private int limitExpPQ = 1024;
	private BigInteger limitUpPQ = BigInteger.TWO.pow(limitExpPQ);
	private BigInteger limitUp = BigInteger.TWO.pow(limitExp);
	private int limitExpAttacks = 200;
	private BigInteger limitUpAttacks = BigInteger.TWO.pow(limitExp);
	private Rabin rabinFirst;
	private Rabin rabinSecond;
	private int bytesPerBlock;
	private int blocklength;
	private int radix = 16;
	private String separator = "||"; //$NON-NLS-1$
	private boolean stopComputation = false;
	private static boolean darkmode = true;
	
	
	// colors for p and q in Cryptosystem and Algorithm tab
	private Color colorBackgroundCorrect = ColorService.LIGHT_AREA_GREEN;
	private Color colorBackgroundWrong = ColorService.LIGHT_AREA_RED;
	private Color colorBackgroundNeutral = ColorService.WHITE;
	
	// colors for warning textfields	
	private Color colorBackgroundWarning = ColorService.LIGHTGRAY;
	private Color colorForegroundWarning = ColorService.RED;
	
	// colors for info textfields
	private static Color colorBGinfo = ColorService.LIGHTGRAY;
	private static Color colorFGinfo = ColorService.BLACK;
	
	// colors for selecting controls
	private Color colorSelectControlBG = ColorService.LIGHT_AREA_BLUE;
	private Color colorDeselectControlBG = ColorService.GRAY;
	private Color colorSelectControlFG = ColorService.BLACK;
	private Color colorDeselectControlFG = ColorService.WHITE;
	
	// colors for buttons
	private Color colorButtonsBG = ColorService.LIGHT_AREA_BLUE;
	private Color colorButtonsFG = ColorService.BLACK;
	
	// colors for composites
	private Color colorCompositeBG = ColorService.GRAY;
	private Color colorCompositeFG = ColorService.WHITE;
	
	// colors for groups
	private Color colorGroupBG = ColorService.GRAY;
	private Color colorGroupFG = ColorService.WHITE;
	
	// colors dark mode
	
	private Color colorDarkModeBG = ColorService.GRAY;
	private Color colorDarkModeFG = ColorService.WHITE;
	private Color colorDarkModeWarningFG = ColorService.LIGHT_AREA_RED;//new Color(255, 91, 91);
	
	
	// colors for second tab selection of styledText
	private Color colorSelectionStyledTextBG = ColorService.LIGHT_AREA_BLUE;
	private Color colorSelectionStyledTextFG = ColorService.BLACK;
	
	
	// colors for reset final plaintext in 2nd tab
	private Color colorResetFinalPlaintextBG = ColorService.GRAY;
	
	
	public void setColorBGinfo(Color color) {
		this.colorBGinfo = color;
	}
	
	public void setColorFGinfo(Color color) {
		this.colorFGinfo = color;
	}
	
	
	public boolean getDarkmode() {
		return darkmode;
	}
	
	
	public void setDarkmode(boolean val) {
		this.darkmode = val;
	}
	
	
	public Color getColorResetFinalPlaintextBG() {
		return colorResetFinalPlaintextBG;
	}
	
	
	public Color getColorSelectionStyledTextBG() {
		return colorSelectionStyledTextBG;
	}
	
	
	
	public Color getColorSelectionStyledTextFG() {
		return colorSelectionStyledTextFG;
	}
	
	
	
	public Color getColorDarkModeWarningFG() {
		return colorDarkModeWarningFG;
	}
	
	
	
	public Color getColorDarkModeBG() {
		return colorDarkModeBG;
	}
	
	
	public Color getColorDarkModeFG() {
		return colorDarkModeFG;
	}
	
	
	
	/** 
	 * @param scMain
	 * @param compMain
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public GUIHandler(ScrolledComposite scMain, Composite compMain, Rabin rabinFirst, Rabin rabinSecond) {
		this.scMain = scMain;
		this.compMain = compMain;
		this.rabinFirst = rabinFirst;
		this.rabinSecond = rabinSecond;
		this.guiHandler = this;
	}
	
	
	public GUIHandler() {
		
	}
	

	
	
	/*public GUIHandler(GUIHandler guiHandler) {
		this.blocklength = guiHandler.blocklength;
		this.bytesPerBlock = guiHandler.bytesPerBlock;
		this.colorBackgroundCorrect = guiHandler.colorBackgroundCorrect;
		this.colorBackgroundNeutral = guiHandler.colorBackgroundNeutral;
		this.colorBackgroundWarning = guiHandler.colorBackgroundWarning;
		this.colorBackgroundWrong = guiHandler.colorBackgroundWrong;
		this.colorForegroundWarning = guiHandler.colorForegroundWarning;
		this.compMain = guiHandler.compMain;
		this.limitExp = guiHandler.limitExp;
		this.limitUp = guiHandler.limitUp;
		this.rabinFirst = guiHandler.rabinFirst;
		this.rabinSecond = guiHandler.rabinSecond;
		this.radix = guiHandler.radix;
		this.scMain = guiHandler.scMain;
		this.separator = guiHandler.separator;
	}*/
	
	
	
	public BigInteger getLimitUpAttacks() {
		return limitUpAttacks;
	}
	
	
	public int getLimitExpAttacks() {
		return limitExpAttacks;
	}
	
	
	
	public boolean getStopComputation() {
		return stopComputation;
	}
	
	
	/**
	 * @return the colorFGinfo
	 */
	public Color getColorFGinfo() {
		return colorFGinfo;
	}






	/**
	 * @return the colorButtons
	 */
	public Color getColorButtonsBG() {
		return colorButtonsBG;
	}


		
	public Color getColorButtonsFG() {
		return colorButtonsFG;
	}



	/**
	 * @return the colorCompositeBG
	 */
	public Color getColorCompositeBG() {
		return colorCompositeBG;
	}






	/**
	 * @return the colorCompositeFG
	 */
	public Color getColorCompositeFG() {
		return colorCompositeFG;
	}






	/**
	 * @return the colorGroupBG
	 */
	public Color getColorGroupBG() {
		return colorGroupBG;
	}






	/**
	 * @return the colorGroupFG
	 */
	public Color getColorGroupFG() {
		return colorGroupFG;
	}






	public void setStopComputation(boolean stopComputation) {
		this.stopComputation = stopComputation;
	}
	
	
	public Color getColorSelectControlBG() {
		return colorSelectControlBG;
	}
	
	
	public Color getColorDeselectControlBG() {
		return colorDeselectControlBG;
	}
	
	
	public Color getColorSelectControlFG() {
		return colorSelectControlFG;
	}
	
	public Color getColorDeselectControlFG() {
		return colorDeselectControlFG;
	}
	
	
	public String getSeparator() {
		return separator;
	}
	
	
	
	public Color getColorBGinfo() {
		return this.colorBGinfo;
	}
	
	
	
	
	/**
	 * @return the colorBackgroundCorrect
	 */
	public Color getColorBackgroundCorrect() {
		return colorBackgroundCorrect;
	}









	/**
	 * @return the colorBackgroundWrong
	 */
	public Color getColorBackgroundWrong() {
		return colorBackgroundWrong;
	}









	/**
	 * @return the colorBackgroundNeutral
	 */
	public Color getColorBackgroundNeutral() {
		return colorBackgroundNeutral;
	}









	/**
	 * @return the colorBackgroundWarning
	 */
	public Color getColorBackgroundWarning() {
		return colorBackgroundWarning;
	}









	/**
	 * @return the colorForegroundWarning
	 */
	public Color getColorForegroundWarning() {
		return colorForegroundWarning;
	}









	/**
	 * @return limitUp
	 */
	public BigInteger getLimitUp() {
		return this.limitUp;
	}
	
	
	
	/**
	 * @return limitExp
	 */
	public int getLimitExp() {
		return this.limitExp;
	}
	

	/**
	 * @return the rabinFirst
	 */
	public Rabin getRabinFirst() {
		return rabinFirst;
	}






	/**
	 * @param rabinFirst the rabinFirst to set
	 */
	public void setRabinFirst(Rabin rabinFirst) {
		this.rabinFirst = rabinFirst;
	}






	/**
	 * @return the rabinSecond
	 */
	public Rabin getRabinSecond() {
		return rabinSecond;
	}






	/**
	 * @param rabinSecond the rabinSecond to set
	 */
	public void setRabinSecond(Rabin rabinSecond) {
		this.rabinSecond = rabinSecond;
	}


	
	
	
	



	/**
	 * @return the bytesPerBlock
	 */
	public int getBytesPerBlock() {
		return bytesPerBlock;
	}






	/**
	 * @param bytesPerBlock the bytesPerBlock to set
	 */
	public void setBytesPerBlock(int bytesPerBlock) {
		this.bytesPerBlock = bytesPerBlock;
	}






	/**
	 * @return the blocklength
	 */
	public int getBlocklength() {
		return blocklength;
	}






	/**
	 * @param blocklength the blocklength to set
	 */
	public void setBlocklength(int blocklength) {
		this.blocklength = blocklength;
	}

	
	





	/**
	 * @return the radix
	 */
	public int getRadix() {
		return radix;
	}






	/**
	 * @param radix the radix to set
	 */
	public void setRadix(int radix) {
		this.radix = radix;
	}
	
	
	public void setCursorControl(Control c, int cursor) {
		c.setCursor(new Cursor(Display.getDefault(), cursor));
	}


	
	public void setControlMargin(Group c, int marginWidth, int marginHeight) {
		GridLayout gl = (GridLayout) c.getLayout();
		gl.marginHeight = marginHeight;
		gl.marginWidth = marginWidth;
	}
	
	
	public void setControlMargin(Composite c, int marginWidth, int marginHeight) {
		GridLayout gl = (GridLayout) c.getLayout();
		gl.marginHeight = marginHeight;
		gl.marginWidth = marginWidth;
	}



	/**
	 * sets the size of the passed control
	 * @param c
	 * @param minWidth
	 * @param minHeight
	 */
	public void setSizeControl(Control c, int minWidth, int minHeight) {
		GridData gd = (GridData) c.getLayoutData();
		gd.minimumWidth = minWidth;
		gd.minimumHeight = minHeight;
		gd.widthHint = c.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		gd.heightHint = c.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
	}
	
	
	
	public static void setSizeControlStatic(Control c, int minWidth, int minHeight) {
		GridData gd = (GridData) c.getLayoutData();
		gd.minimumWidth = minWidth;
		gd.minimumHeight = minHeight;
		gd.widthHint = c.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		gd.heightHint = c.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
	}
	
	
	
	
	
	/**
	 * sets the size of the passed control but only computes the width
	 * @param c
	 * @param minWidth
	 * @param minHeight
	 */
	public void setSizeControlWarning(Control c, int minWidth, int minHeight) {
		GridData gd = (GridData) c.getLayoutData();
		gd.minimumWidth = minWidth;
		gd.minimumHeight = minHeight;
		gd.widthHint = c.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
	}
	
	
	
	
	/**
	 * hides passed control
	 * @param c
	 */
	public void hideControl(Control c) {
		GridData gd = (GridData) c.getLayoutData();
		gd.exclude = true;
		c.setVisible(false);
		c.getParent().requestLayout();
		
		if(scMain == null)
			return;
		
		
		int width = scMain.getClientArea().width;
		
		/*if(width < 1000) {
			scMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			return;
		}*/
		
		scMain.setMinSize(compMain.computeSize(width, SWT.DEFAULT));
		
		//scMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	
	
	/**
	 * hides passed control, not used
	 * @param c
	 * @param parent
	 */
	public void hideControl(Control c, Control parent) {
		GridData gd = (GridData) c.getLayoutData();
		gd.exclude = true;
		c.setVisible(false);
		parent.requestLayout();
		
		if(scMain == null)
			return;
		
		//scMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	
	
	
	/**
	 * show passed control
	 * @param c
	 */
	public void showControl(Control c) {
		GridData gd = (GridData) c.getLayoutData();
		gd.exclude = false;
		c.setVisible(true);
		c.getParent().requestLayout();
		
		if(scMain == null)
			return;
		
		int width = scMain.getClientArea().width;
		
		/*if(width < 1000) {
			scMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
			return;
		}*/
		
		scMain.setMinSize(compMain.computeSize(width, SWT.DEFAULT));
		//scMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * shows passed control, not used
	 * @param c
	 * @param parent
	 */
	public void showControl(Control c, Control parent) {
		GridData gd = (GridData) c.getLayoutData();
		gd.exclude = false;
		c.setVisible(true);
		parent.requestLayout();
		
		if(scMain == null)
			return;
		
		scMain.setMinSize(compMain.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	
	
	
	public String getMessageByControl(String str) {
		String message = null;
		
		switch(str) {
			case "btnGenKeysMan_selection":
				message = "To generate a private key (p,q) and a public key N manually do the following:\n\n"
						+ "1) either enter p and q in the "
						+ "corresponding fields on the left side or make use of the drop-down lists for p and q."
						+ "\nMake sure to satisfy the conditions p = q \u2261 3 mod 4 and p \u2260 q. "
						+ "Furthermore, p and q both must be \u2264 " + "2^" + this.getLimitExp() + ".\n"
						+ "For a start there are already given default values for p and q.\n\n"
						+ "2) click on \"Start\" in the middle to generate the keys.";
					break;
			
			case "btnGenKeys_selection":
				message = "To generate a private key (p,q) and a public key N using limits do the following:\n\n"
						+ "1) select \"Generate p and q having the same range\" or \"Generate p and q having different ranges\" on the left side.\n\n"
						+ "2) enter a lower and an upper limit for p and q in the corresponding fields. "
						+ "The numbers for p and q will be between the lower and upper limit. When selecting "
						+ "\"Generate p and q having the same range\" you are supposed to enter one range, which "
						+ "is used for both p and q. On the other hand the other option allows you to have "
						+ "ranges for p and q independently from each other.\n"
						+ "Only an upper limit \u2264 " + "2^" + this.getLimitExp() + " is allowed.\n"
						+ "For a start there are already given default values for the ranges.\n\n"
						+ "3) click on \"Start\" in the middle to generate the keys.";
				break;
			
			case "txtInfoSelection_textbook":
				message = "In this mode you are able to encrypt a chosen plaintext"
						+ " or decrypt a chosen chiphertext in textbook mode. For example, "
						+ "you have N = 713 = 23 \u2219 31 (default value) and want to encrypt "
						+ "the plaintext \"hello\", then the plaintext will be split in "
						+ "\"h || e || l || l || o\" and every letter is encrypted individually since N can only encrypt at most 9 bits "
						+ "and one character is 8 bits long. To encrypt more characters at once you have to choose a greater N."
						+ "On the other hand decrypting a ciphertext will result in 4 possible plaintexts, of which only one "
						+ "is the correct one.";
				break;
				
			case "txtInfoSelection_steps":
				message = "In this mode you are able to encrypt a chosen plaintext "
						+ "or decrypt a chosen ciphertext. But compared to the \"textbook\" version "
						+ "you are shown every step of the encryption and decryption process "
						+ "in detail. Furthermore you have more options for configurations.\n"
						+ "As such, this mode is for better understanding the cryptosystem.";
				break;
				
			case "txtInfoSelector":
				message = "click on \"Load text...\" to either load a plaintext you want to encrypt "
						+ "or a ciphertext you want to decrypt. If you want to load a plaintext "
						+ "only characters in the UTF-8 format are allowed. If you want to load "
						+ "a ciphertext only hexadecimal numbers (0-f) are allowed.\n"
						+ "Furthermore make sure the length of the ciphertext is a multiple of the blocklength of N.";
				break;
				
			/*case "txtInfoEncryptionDecryption_encrypt":
				message = "click on \"Encrypt\" to encrypt a chosen plaintext. The ciphertext will be "
						+ "shown in the box on the left side.\n\n"
						+ "Once you have encrypted a plaintext you can click on "
						+ "\"Decrypt and switch to decryption mode\" to decrypt your ciphertext again and "
						+ "switch to decryption mode.\n"
						+ "You can also click on \"Write to JCT editor\" to write your ciphertext to a build-in editor "
						+ "in JCT.";*/
				
			case "txtInfoEncryptionDecryption_encrypt":
				message = "In encryption mode you have three options you can choose from:\n\n"
				  		+ "1) click on \"Encrypt\" to encrypt a chosen plaintext. The ciphertext will be shown "
				  		+ "in the field on the left side.\n\n"
				  		+ "2) once you have encrypted a plaintext you can click on "
				  		+ "\"Decrypt and switch to decryption mode\" to decrypt the ciphertext again and "
				  		+ "switch to decryption mode.\n\n"
				  		+ "3) you can click on \"Write to JCT editor\" to write your non-empty ciphertext to a build-in "
				  		+ "editor in JCT.";
				break;
				
			case "txtInfoEncryptionDecryption_decrypt":
				message = "In decryption mode you have five options you can choose from:\n\n"
						+ "1) click on \"Decrypt\" to decrypt a chosen ciphertext.\n"
						+ "The ciphertext is split into blocks with a specific blocklength. "
						+ "For example, having N = 713 = 23 \u2219 31 (default value), the blocklength "
						+ "is 2. So its the bitlength of N divided by 8 plus 1, since one block is 8 bits long.\n"
						+ "The ciphertext seperated into blocks is shown in the according field on the left side.\n\n"
						+ "2) Once you have decrypted a ciphertext you can either use the drop-down list \"Block\" to choose a "
						+ "specifc ciphertextblock or the buttons \"Previous block\" and \"Next block\" to go through the "
						+ "ciphertextblocks one by one in a cyclic manner.\n"
						+ "The currently selected ciphertextblock is decrypted and its four possible plaintexts are "
						+ "shown in the according fields on the left side.\n"
						+ "Most of the times three of the four plaintexts consist of random characters, so "
						+ "they do not make any sense at all.\n\n"
						+ "3) you can click on each plaintext to mark it as \"selected\" and add it to the "
						+ "list of chosen plaintexts, which are shown in the field \"Chosen plaintexts (preview)\".\n"
						+ "The first click will add the plaintext to the list and a second click will remove "
						+ "it again.\n"
						+ "With this you are able to select different plaintexts of your choice and connect them to"
						+ "\"one\" plaintext.\n\n"
						+ "4) you can click on \"Write to JCT editor\" to write the content of the field "
						+ "\"Chosen plaintexts (preview)\" to a build-in editor in JCT.\n\n"
						+ "5) you can click on \"Reset chosen plaintexts\" to reset the field "
						+ "\"Chosen plaintexts (preview)\" and your whole selection of plaintexts.";
				break;
				
				
			case "txtInfoEnc_Text":
				message = "To encrypt a plaintext in \"Text\" mode do the following:\n\n"
						+ "1) select \"Bytes per block\" to choose how many bytes you want to encrypt at once. "
						+ "For example, having N = 713 = 23 \u2219 31 (default value) you are able "
						+ "to encrypt at most 1 Byte (or character/letter) at once since N has a bitlength of 10 and 1 Byte "
						+ "is 8 bits long.\n"
						+ "If you want to encrypt more bytes at once you need to generate a greater N.\n"
						+ "Compared to the \"textbook\" mode you are here able to choose how many bytes "
						+ "you want to encrypt at once. The \"textbook\" mode always uses the maximum number "
						+ "of bytes.\n\n"
						+ "2) enter a plaintext in the field \"Plaintext\". Only characters in the UTF-8 format are allowed.\n\n"
						+ "3) click on \"Encrypt\" to encrypt the plaintext";
				break;
				
			case "txtInfoEnc_Decimal":
				message = "To encrypt a plaintext in \"Decimal numbers\" mode do the following:\n\n"
						+ "1) enter a plaintext in the field \"Plaintext separated into blocks "
						+ "(\"||\" as separator)\". You are only allowed to enter decimal numbers "
						+ "(0-9). Furthermore make sure you enter the numbers in the format "
						+ "\"decimal number\" or \"decimal number 1 || decimal number 2 || ...\".\n"
						+ "Every decimal number has to be less than N.\n\n"
						+ "2) click on \"Encrypt\" to encrypt the plaintext.";
				break;
				
			case "txtInfoDecimalAndHex_decryption_hex":
				message = "You are in \"Hex\" mode. This mode allows you to decrypt ciphertexts entered as "
						+ "hexstrings. Keep in mind that its only the decryption without previous encryption. "
						+ "That means, In contrast to the \"Encryption and decryption\" mode this mode computes the four possible plaintexts without notifying the "
						+ "user which of them is the correct one since the decryption does not know it.\n\n"
						+ "To decypt a ciphertext do the following:\n\n"
						+ "1) enter a ciphertext in the field \"Ciphertext in base 16 format\" as hexstring, which "
						+ "means only hexadecimal numbers (0-f) are allowed. "
						+ "Make sure that the length of the ciphertext is a multiple of the blocklength of N.\n\n"
						+ "2) click on \"Apply\" to get the ciphertext separated into blocks";
				break;
				
			case "txtInfoSquareRoots_decryption_hex_and_decimal":
				message = "3) Either use the drop-down list \"Block(c[i])\" to choose a specific ciphertextblock ("
						+ "c[i] is only meant as a short version for the specific ciphertextblock. For example c[1] would "
						+ "be the first ciphertextblock, c[2] the second and so on) or the buttons "
						+ "\"Previous block\" and \"Next block\" to go through the ciphertextblocks in a cyclic manner.\n\n"
						+ "4) Once you have chosen a ciphertextblock click on \"Compute square roots mod p and q\" "
						+ "to compute the square roots of your ciphertextblock mod p and q.";
				break;
				
			case "txtInfoLC_decryption_hex_and_decimal":
				message = "5) click on \"Compute y_p and y_q\" to compute y_p and y_q using the Euclidean algorithm\n\n"
						+ "6) click on \"Compute v and w\" to compute the intermediate values v and w.";
				break;
				
			case "txtInfoPlaintexts_decryption_hex_and_decimal":
				message = "7) click on \"Compute all plaintexts\" to compute all plaintexts.\n"
						+ "The whole computation is based on the Chinese remainder theorem (CRT). "
						+ "For further information on it take a look at the CRT plugin in JCT.";
				break;
				
			case "txtInfoDecimalAndHex_decryption_decimal":
				message = "You are in \"Decimal numbers\" mode. This mode allows you to decrypt ciphertexts entered in "
						+ "the format \"decimal number\" or \"decimal number 1 || decimal number 2 || ...\". Keep in mind that its only the decryption without previous encryption. "
						+ "That means, In contrast to the \"Encryption and decryption\" mode this mode computes the four possible plaintexts without notifying the "
						+ "user which of them is the correct one since the decryption does not know it.\n\n"
						+ "To decypt a ciphertext do the following:\n\n"
						+ "1) enter a ciphertext in the field \"Ciphertext in base 10 separated into block (|| as separator)\".\n"
						+ "Pay attention to the mentioned format.\n\n"
						+ "2) click on \"Apply\" to get the ciphertext separated into blocks in base 16 format.";
				break;
				
			case "txtInfoSquareRoots_encryption_text":
				message = "4) Either use the drop-down list \"Block(c[i])\" to choose a specific ciphertextblock ("
						+ "c[i] is only meant as a short version for the specific ciphertextblock. For example c[1] would "
						+ "be the first ciphertextblock, c[2] the second and so on) or the buttons "
						+ "\"Previous block\" and \"Next block\" to go through the ciphertextblocks in a cyclic manner.\n\n"
						+ "5) Once you have chosen a ciphertextblock click on \"Compute square roots mod p and q\" "
						+ "to compute the square roots of your ciphertextblock mod p and q.";
				break;
				
			case "txtInfoLC_encryption_text":
				message = "6) click on \"Compute y_p and y_q\" to compute y_p and y_q using the Euclidean algorithm\n\n"
						+ "7) click on \"Compute v and w\" to compute the intermediate values v and w.";
				break;
				
			case "txtInfoPlaintexts_encryption_text":
				message = "8) click on \"Compute all plaintexts\" to compute all plaintexts.\n"
						+ "The whole computation is based on the Chinese remainder theorem (CRT). "
						+ "For further information on it take a look at the CRT plugin in JCT.";
				break;
				
			
			case "txtInfoFactor_fermat":
				message = "The Fermat factorization basically works like this (simplified):\n\n"
						+ "We want to factorize N into its factors p and q. We use the fact that N can be "
						+ "expressed as N = y\u00b2 \u2212 x\u00b2 which you can change to "
						+ "y\u00b2 \u2212 N = x\u00b2. Then we use y as an \"anchor point\" by starting at a specific value "
						+ "and increasing y by one in every iteration until the value \u221a(y\u00b2 \u2212 N) is an integer.\n"
						+ "Every step is shown in the table below. In the end you can also see how the factors p and q are computed.\n\n"
						+ "For a more detailed description take a look on the internet or click on the help button in the description "
						+ "of the plugin.\n\n"
						+ "To factorize a composite number N do the following:\n\n"
						+ "1) enter N in the corresponding field, use the drop-down list or click on the button"
						+ " \"Use public key generated in Cryptosystem tab\" to use N which was generated in the "
						+ "Cryptosystem tab.\n"
						+ "Only an upper limit of 2^" + getLimitExpAttacks() + " for N is allowed.\n\n"
						+ "2) Click on \"Factorize\" to start factorizing the number. The whole algorithm is executed in the background.\n"
						+ "In case the processing time takes too long for getting a result you can click on "
						+ "\"Stop computation\" to stop and quit the computation.\n"
						+ "Only one number can be factorized at one time. So if you want to factorize a new number you have to either wait "
						+ "until the current factorization is completed or stop the current factorization.";
				break;
				
				
			case "txtInfoFactor_pollard":
				message = "Pollard's rho factorization basically works like this (simplified):\n\n"
						+ "To factorize N we try to find numbers say x and y such that x \u2262 y mod N "
						+ "but x \u2261 y mod p. With that we have that N does not divide (x-y) but p divides both (x-y) "
						+ "and N. If we compute gcd((x-y), N) now we get one of the factors p or q of N. If we then divide N "
						+ "by the found factor we get the other factor and as such the whole factorization.\n"
						+ "To find so called numbers x and y we use a random function, here g(x) = x\u00b2 + c.\n"
						+ "The algorithm is shown in the table below.\n\n"
						+ "For a more detailed description take a look on the internet or click on the help button in the "
						+ "description of the plugin.\n\n"
						+ "To factorize N do the following:\n\n"
						+ "1) enter N in the corresponding field, use the drop-down list or click on the button"
						+ " \"Use public key generated in Cryptosystem tab\" to use N which was generated in the "
						+ "Cryptosystem tab.\n"
						+ "Only an upper limit of 2^" + getLimitExpAttacks() + " for N is allowed.\n\n"
						+ "2) Click on \"Factorize\" to start factorizing the number. The whole algorithm is executed in the background.\n"
						+ "In case the processing time takes too long for getting a result you can click on "
						+ "\"Stop computation\" to stop and quit the computation.\n"
						+ "Only one number can be factorized at one time. So if you want to factorize a new number you have to either wait "
						+ "until the current factorization is completed or stop the current factorization.";
				break;
				
		}
		
		return message;
	}
	
	
	
	
	
	
	
	/**
	 * action which is executed when clicking the btnGenKeysMan button
	 * @param cP
	 * @param cQ
	 * @param txtN
	 */
	public void btnGenKeysManAction(Control cP, Control cQ, Text txtN) {
		
		Text txtP = null;
		Text txtQ = null;
		Combo cmbP = null;
		Combo cmbQ = null;
		String pAsStr = null;
		String qAsStr = null;
		
		if(cP instanceof Text && cQ instanceof Text) {
			txtP = (Text) cP;
			txtQ = (Text) cQ;
			
			pAsStr = txtP.getText();
			qAsStr = txtQ.getText();
		}
		else if(cP instanceof Combo && cQ instanceof Combo) {
			cmbP = (Combo) cP;
			cmbQ = (Combo) cQ;
			
			pAsStr = cmbP.getText();
			qAsStr = cmbQ.getText();
		}
		
		BigInteger p = new BigInteger(pAsStr);
		BigInteger q = new BigInteger(qAsStr);
		BigInteger n = p.multiply(q);
		this.getRabinFirst().setP(p);
		this.getRabinFirst().setQ(q);
		this.getRabinFirst().setN(n);
		txtN.setText(n.toString());
	}
	
	
		
	
	
	
	/**
	 * update textfields for Crypptosytem and Algorithm tab, not used
	 * @param cP
	 * @param cQ
	 * @param btnGenKeysMan
	 * @param btnStartGenKeys
	 * @param txtWarning
	 */
	public void updateTextfields(Control cP, Control cQ, Button btnGenKeysMan, Button btnStartGenKeys, Text txtWarning) {
		
		BigInteger ptmp = null;
		BigInteger qtmp = null;
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		
		Text txtP = null;
		Text txtQ = null;
		Combo cmbP = null;
		Combo cmbQ = null;
		
		if(btnGenKeysMan.getSelection())
			btnStartGenKeys.setEnabled(false);
		
		String strNumberRestriction = "Attention: only numbers > 0 are allowed"; //$NON-NLS-1$
		if(cP instanceof Text && cQ instanceof Text) {
			txtP = (Text) cP;
			txtQ = (Text) cQ;
			
			
			String pAsStr = txtP.getText();
			String qAsStr = txtQ.getText();
			
			if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
				
				txtP.setBackground(ColorService.WHITE);
				txtQ.setBackground(ColorService.WHITE);
				hideControl(txtWarning);
				return;
			}
			
			if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				
				
				if(!qAsStr.matches(pattern)) {
					txtQ.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.WHITE);
					txtWarning.setText(strNumberRestriction);
					showControl(txtWarning);
					return;
				}
				
				txtP.setBackground(ColorService.RED);
				qtmp = new BigInteger(qAsStr);
				
				if(qtmp.compareTo(limitUp) > 0) {
					txtQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n1) only an upper limit of 2^" + limitExp + " is allowed\n" //$NON-NLS-1$ //$NON-NLS-2$
								+ "2) p is missing"); //$NON-NLS-1$
					showControl(txtWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(qtmp)) {
					txtQ.setBackground(ColorService.GREEN);
					txtWarning.setText("Attention: p is missing"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else {
					txtQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
							+ "1) p is missing\n" //$NON-NLS-1$
							+ "2) q is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
			
				

				if(!pAsStr.matches(pattern)) {
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.WHITE);
					txtWarning.setText(strNumberRestriction);
					showControl(txtWarning);
					return;
				}
				
				ptmp = new BigInteger(pAsStr);
				txtQ.setBackground(ColorService.RED);
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					txtP.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
						+ "1) only an upper limit of 2^" + limitExp + " is allowed\n" //$NON-NLS-1$ //$NON-NLS-2$
								+ "2) q is missing"); //$NON-NLS-1$
					showControl(txtWarning);
					return;
				}
				
				
				if(rabinFirst.isSuitablePrime(ptmp)) {
					txtP.setBackground(ColorService.GREEN);
					txtWarning.setText("Attention: q is missing"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else {
					txtP.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
							+ "1) q is missing\n" //$NON-NLS-1$
							+ "2) p is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
			
				
				boolean checkPattern = pAsStr.matches(pattern) && qAsStr.matches(pattern);
				
				if(!checkPattern) {
					if(!pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						txtWarning.setText(strNumberRestriction);
						showControl(txtWarning);
					}
					if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.WHITE);
						txtWarning.setText(strNumberRestriction);
						showControl(txtWarning);
					}
					if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.WHITE);
						txtQ.setBackground(ColorService.RED);
						txtWarning.setText(strNumberRestriction);
						showControl(txtWarning);
					}
					return;
					
				}
				
				ptmp = new BigInteger(pAsStr);
				qtmp = new BigInteger(qAsStr);
				
				boolean checkUpperLimit = ptmp.compareTo(limitUp) <= 0 && qtmp.compareTo(limitUp) <= 0;
				
				if(!checkUpperLimit) {
					if(!(ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						txtWarning.setText("Attention: " //$NON-NLS-1$
						+ "only an upper limit of 2^" + limitExp + " is allowed"); //$NON-NLS-1$ //$NON-NLS-2$
						showControl(txtWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.GREEN);
						txtWarning.setText("Attention: " //$NON-NLS-1$
						+ "only an upper limit of 2^" + limitExp + " is allowed"); //$NON-NLS-1$ //$NON-NLS-2$
						showControl(txtWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.GREEN);
						txtQ.setBackground(ColorService.RED);
						txtWarning.setText("Attention: " //$NON-NLS-1$
						+ "only an upper limit of 2^" + limitExp + " is allowed"); //$NON-NLS-1$ //$NON-NLS-2$
						showControl(txtWarning);
					}
					return;
				}
				
				if(rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					if(!ptmp.equals(qtmp)) {
						if(rabinFirst.isCompositeSuitable(ptmp, qtmp)) {
							txtP.setBackground(ColorService.GREEN);
							txtQ.setBackground(ColorService.GREEN);
							hideControl(txtWarning);
							if(btnGenKeysMan.getSelection()) {
								btnStartGenKeys.setEnabled(true);
							}
						}
						else {
							txtP.setBackground(ColorService.RED);
							txtQ.setBackground(ColorService.RED);
							txtWarning.setText("Attention: " //$NON-NLS-1$
									+ "N = p \u2219 q < 256"); //$NON-NLS-1$
							showControl(txtWarning);
						}
					}
					else {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						txtWarning.setText("Attention: the condition p \u2260 q must be satisfied"); //$NON-NLS-1$
						showControl(txtWarning);
					}
				}
				else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(ColorService.GREEN);
					txtQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention: q is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.GREEN);
					txtWarning.setText("Attention: p is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention: p and q are not suitable primes"); //$NON-NLS-1$
					showControl(txtWarning);
				}
			}
			
		}
		else if(cP instanceof Combo && cQ instanceof Combo) {
			cmbP = (Combo) cP;
			cmbQ = (Combo) cQ;
			
			
			String pAsStr = cmbP.getText();
			String qAsStr = cmbQ.getText();
			
			if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
				
				cmbP.setBackground(ColorService.WHITE);
				cmbQ.setBackground(ColorService.WHITE);
				hideControl(txtWarning);
				return;
			}
			
			if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				
				if(!qAsStr.matches(pattern)) {
					cmbP.setBackground(ColorService.WHITE);
					cmbQ.setBackground(ColorService.RED);
					txtWarning.setText(strNumberRestriction);
					showControl(txtWarning);
					return;
				}
				
				cmbP.setBackground(ColorService.RED);
				qtmp = new BigInteger(qAsStr);
				
				if(qtmp.compareTo(limitUp) > 0) {
					cmbQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
						+ "1) only an upper limit of 2^" + limitExp + " is allowed\n" //$NON-NLS-1$ //$NON-NLS-2$
								+ "2) p is missing"); //$NON-NLS-1$
					showControl(txtWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbQ.setBackground(ColorService.GREEN);
					txtWarning.setText("Attention: p is missing"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else {
					cmbQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
							+ "1) p is missing\n" //$NON-NLS-1$
							+ "2) q is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
				if(!pAsStr.matches(pattern)) {
					cmbP.setBackground(ColorService.RED);
					cmbQ.setBackground(ColorService.WHITE);
					txtWarning.setText(strNumberRestriction);
					showControl(txtWarning);
					return;
				}
				
				cmbQ.setBackground(ColorService.RED);
				ptmp = new BigInteger(pAsStr);
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					cmbP.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
						+ "1) only an upper limit of 2^" + limitExp + " is allowed\n" //$NON-NLS-1$ //$NON-NLS-2$
								+ "2) q is missing"); //$NON-NLS-1$
					showControl(txtWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(ptmp)) {
					cmbP.setBackground(ColorService.GREEN);
					txtWarning.setText("Attention: q is missing"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else {
					cmbP.setBackground(ColorService.RED);
					txtWarning.setText("Attention:\n" //$NON-NLS-1$
							+ "1) q is missing\n" //$NON-NLS-1$
							+ "2) p is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				boolean checkPattern = pAsStr.matches(pattern) && qAsStr.matches(pattern);
				
				if(!checkPattern) {
					if(!pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.RED);
						txtWarning.setText(strNumberRestriction);
						showControl(txtWarning);
					}
					if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.WHITE);
						txtWarning.setText(strNumberRestriction);
						showControl(txtWarning);
					}
					if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						cmbP.setBackground(ColorService.WHITE);
						cmbQ.setBackground(ColorService.RED);
						txtWarning.setText(strNumberRestriction);
						showControl(txtWarning);
					}
					return;
					
				}
				
				ptmp = new BigInteger(pAsStr);
				qtmp = new BigInteger(qAsStr);
				
				boolean checkUpperLimit = ptmp.compareTo(limitUp) <= 0 && qtmp.compareTo(limitUp) <= 0;
				
				if(!checkUpperLimit) {
					if(!(ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.RED);
						txtWarning.setText("Attention: " //$NON-NLS-1$
						+ "only an upper limit of 2^" + limitExp + " is allowed"); //$NON-NLS-1$ //$NON-NLS-2$
						showControl(txtWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.GREEN);
						txtWarning.setText("Attention: " //$NON-NLS-1$
						+ "only an upper limit of 2^" + limitExp + " is allowed"); //$NON-NLS-1$ //$NON-NLS-2$
						showControl(txtWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(ColorService.GREEN);
						cmbQ.setBackground(ColorService.RED);
						txtWarning.setText("Attention: " //$NON-NLS-1$
						+ "only an upper limit of 2^" + limitExp + " is allowed"); //$NON-NLS-1$ //$NON-NLS-2$
						showControl(txtWarning);
					}
					return;
				}
				
				
				if(rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					if(!ptmp.equals(qtmp)) {
						if(rabinFirst.isCompositeSuitable(ptmp, qtmp)) {
							cmbP.setBackground(ColorService.GREEN);
							cmbQ.setBackground(ColorService.GREEN);
							hideControl(txtWarning);
							if(btnGenKeysMan.getSelection()) {
								btnStartGenKeys.setEnabled(true);
							}
						}
						else {
							cmbP.setBackground(ColorService.RED);
							cmbQ.setBackground(ColorService.RED);
							txtWarning.setText("Attention: " //$NON-NLS-1$
									+ "N = p \u2219 q < 256"); //$NON-NLS-1$
							showControl(txtWarning);
						}
					}
					else {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.RED);
						txtWarning.setText("Attention: the condition p \u2260 q must be satisfied"); //$NON-NLS-1$
						showControl(txtWarning);
					}
				}
				else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbP.setBackground(ColorService.GREEN);
					cmbQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention: q is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbP.setBackground(ColorService.RED);
					cmbQ.setBackground(ColorService.GREEN);
					txtWarning.setText("Attention: p is not a suitable prime"); //$NON-NLS-1$
					showControl(txtWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					cmbP.setBackground(ColorService.RED);
					cmbQ.setBackground(ColorService.RED);
					txtWarning.setText("Attention: p and q are not suitable primes"); //$NON-NLS-1$
					showControl(txtWarning);
				}
			}
		}
		
		
		
		
	}
	
	
	
	
	
	
	/**
	 * update textfields p and q for Cryptosystem and Algorithm tab, its currently used
	 * @param tabComposite
	 */
	/*public void updateTextfields(Control tabComposite) {
		BigInteger ptmp = null;
		BigInteger qtmp = null;
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		RabinFirstTabComposite rftc = null;
		RabinSecondTabComposite rstc = null;
		
		Color colorBackgroundCorrect = this.colorBackgroundCorrectPQ;
		Color colorBackgroundWrong = this.colorBackgroundWrongPQ;
		Color colorBackgroundNeutral = this.colorBackgroundNeutralPQ;
		
		String strNumberRestriction = Messages.GUIHandler_strNumberRestriction;
		String strMissingP = Messages.GUIHandler_strMissingP;
		String strUpperLimitRestriction = Messages.GUIHandler_strUpperLimitRestriction;
		
		String strQnotSuitable = Messages.GUIHandler_strQnotSuitable;
		String strMissingQ = Messages.GUIHandler_strMissingQ;
		String strPnotEqualQ = Messages.GUIHandler_strPnotEqualQ;
		String strPnotSuitable = Messages.GUIHandler_strPnotSuitable;
		String strNotSuitableN = Messages.GUIHandler_strNotSuitableN;
		
		if(tabComposite instanceof RabinSecondTabComposite) {
			rstc = (RabinSecondTabComposite) tabComposite;
			
			if(rstc.getBtnGenKeysMan().getSelection())
				rstc.getBtnStartGenKeys().setEnabled(false);
			
			Combo cmbP = rstc.getCmbP();
			Combo cmbQ = rstc.getCmbQ();
			Text pWarning = rstc.getPWarning();
			Text qWarning = rstc.getQWarning();
			Text nWarning = rstc.getNWarning();
			
			
			String pAsStr = cmbP.getText();
			String qAsStr = cmbQ.getText();
			
			if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
				
				cmbP.setBackground(ColorService.WHITE);
				cmbQ.setBackground(ColorService.WHITE);
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				return;
			}
			
			if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				if(!qAsStr.matches(pattern)) {
					cmbP.setBackground(ColorService.WHITE);
					cmbQ.setBackground(ColorService.RED);
					qWarning.setText(strNumberRestriction);
					showControl(qWarning);
					return;
				}
				
				cmbP.setBackground(ColorService.RED);
				pWarning.setText(strMissingP);
				showControl(pWarning);
				
				qtmp = new BigInteger(qAsStr);
				
				if(qtmp.compareTo(limitUp) > 0) {
					cmbQ.setBackground(ColorService.RED);
					qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(qWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbQ.setBackground(ColorService.GREEN);
					pWarning.setText(strMissingP);
					showControl(pWarning);
				}
				else {
					cmbQ.setBackground(ColorService.RED);
					qWarning.setText(strQnotSuitable);
					pWarning.setText(strMissingP);
					showControl(pWarning);
					showControl(qWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				if(!pAsStr.matches(pattern)) {
					cmbP.setBackground(ColorService.RED);
					cmbQ.setBackground(ColorService.WHITE);
					pWarning.setText(strNumberRestriction);
					showControl(pWarning);
					return;
				}
				
				cmbQ.setBackground(ColorService.RED);
				qWarning.setText(strMissingQ);
				showControl(qWarning);
				ptmp = new BigInteger(pAsStr);
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					cmbP.setBackground(ColorService.RED);
					pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(pWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(ptmp)) {
					cmbP.setBackground(ColorService.GREEN);
					qWarning.setText(strMissingQ);
					showControl(qWarning);
				}
				else {
					cmbP.setBackground(ColorService.RED);
					qWarning.setText(strMissingQ);
					pWarning.setText(strPnotSuitable);
					showControl(qWarning);
					showControl(pWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				boolean checkPattern = pAsStr.matches(pattern) && qAsStr.matches(pattern);
				
				if(!checkPattern) {
					if(!pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.RED);
						pWarning.setText(strNumberRestriction);
						qWarning.setText(strNumberRestriction);
						showControl(pWarning);
						showControl(qWarning);
					}
					if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.WHITE);
						pWarning.setText(strNumberRestriction);
						showControl(pWarning);
					}
					if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						cmbP.setBackground(ColorService.WHITE);
						cmbQ.setBackground(ColorService.RED);
						qWarning.setText(strNumberRestriction);
						showControl(qWarning);
					}
					return;
					
				}
				
				ptmp = new BigInteger(pAsStr);
				qtmp = new BigInteger(qAsStr);
				
				boolean checkUpperLimit = ptmp.compareTo(limitUp) <= 0 && qtmp.compareTo(limitUp) <= 0;
				
				if(!checkUpperLimit) {
					if(!(ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.RED);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
						showControl(qWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.GREEN);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(ColorService.GREEN);
						cmbQ.setBackground(ColorService.RED);
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(qWarning);
					}
					return;
				}
				
				
				if(rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					if(!ptmp.equals(qtmp)) {
						if(rabinFirst.isCompositeSuitable(ptmp, qtmp)) {
							cmbP.setBackground(ColorService.GREEN);
							cmbQ.setBackground(ColorService.GREEN);
							hideControl(pWarning);
							hideControl(qWarning);
							hideControl(nWarning);
							if(rstc.getBtnGenKeysMan().getSelection()) {
								rstc.getBtnStartGenKeys().setEnabled(true);
							}
						}
						else {
							cmbP.setBackground(ColorService.RED);
							cmbQ.setBackground(ColorService.RED);
							nWarning.setText(strNotSuitableN);
							showControl(nWarning);
						}
					}
					else {
						cmbP.setBackground(ColorService.RED);
						cmbQ.setBackground(ColorService.RED);
						nWarning.setText(strPnotEqualQ);
						showControl(nWarning);
					}
				}
				else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbP.setBackground(ColorService.GREEN);
					cmbQ.setBackground(ColorService.RED);
					qWarning.setText(strQnotSuitable);
					showControl(qWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbP.setBackground(ColorService.RED);
					cmbQ.setBackground(ColorService.GREEN);
					pWarning.setText(strPnotSuitable);
					showControl(pWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					cmbP.setBackground(ColorService.RED);
					cmbQ.setBackground(ColorService.RED);
					pWarning.setText(strPnotSuitable);
					qWarning.setText(strQnotSuitable);
					showControl(pWarning);
					showControl(qWarning);
				}
			}
			

		}
		else {
			rftc = (RabinFirstTabComposite) tabComposite;
			
			if(rftc.getBtnGenKeysMan().getSelection())
				rftc.getBtnStartGenKeys().setEnabled(false);
			
			Text txtP = rftc.getTxtP();
			Text txtQ = rftc.getTxtQ();
			Text pWarning = rftc.getPWarning();
			Text qWarning = rftc.getQWarning();
			Text nWarning = rftc.getNWarning();
			
			
			String pAsStr = txtP.getText();
			String qAsStr = txtQ.getText();
			
			if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
				
				txtP.setBackground(ColorService.WHITE);
				txtQ.setBackground(ColorService.WHITE);
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				return;
			}
			
			if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				if(!qAsStr.matches(pattern)) {
					txtP.setBackground(ColorService.WHITE);
					txtQ.setBackground(ColorService.RED);
					qWarning.setText(strNumberRestriction);
					showControl(qWarning);
					return;
				}
				
				txtP.setBackground(ColorService.RED);
				pWarning.setText(strMissingP);
				showControl(pWarning);
				
				qtmp = new BigInteger(qAsStr);
				
				if(qtmp.compareTo(limitUp) > 0) {
					txtQ.setBackground(ColorService.RED);
					qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(qWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(qtmp)) {
					
					txtQ.setBackground(ColorService.GREEN);
					pWarning.setText(strMissingP);
					showControl(pWarning);
				}
				else {
					txtQ.setBackground(ColorService.RED);
					qWarning.setText(strQnotSuitable);
					pWarning.setText(strMissingP);
					showControl(pWarning);
					showControl(qWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				if(!pAsStr.matches(pattern)) {
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.WHITE);
					pWarning.setText(strNumberRestriction);
					showControl(pWarning);
					return;
				}
				
				txtQ.setBackground(ColorService.RED);
				qWarning.setText(strMissingQ);
				showControl(qWarning);
				ptmp = new BigInteger(pAsStr);
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					txtP.setBackground(ColorService.RED);
					pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(pWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(ptmp)) {
					txtP.setBackground(ColorService.GREEN);
					qWarning.setText(strMissingQ);
					showControl(qWarning);
				}
				else {
					txtP.setBackground(ColorService.RED);
					qWarning.setText(strMissingQ);
					pWarning.setText(strPnotSuitable);
					showControl(qWarning);
					showControl(pWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				boolean checkPattern = pAsStr.matches(pattern) && qAsStr.matches(pattern);
				
				if(!checkPattern) {
					if(!pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						pWarning.setText(strNumberRestriction);
						qWarning.setText(strNumberRestriction);
						showControl(pWarning);
						showControl(qWarning);
					}
					if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.WHITE);
						pWarning.setText(strNumberRestriction);
						showControl(pWarning);
					}
					if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.WHITE);
						txtQ.setBackground(ColorService.RED);
						qWarning.setText(strNumberRestriction);
						showControl(qWarning);
					}
					return;
					
				}
				
				ptmp = new BigInteger(pAsStr);
				qtmp = new BigInteger(qAsStr);
				
				boolean checkUpperLimit = ptmp.compareTo(limitUp) <= 0 && qtmp.compareTo(limitUp) <= 0;
				
				if(!checkUpperLimit) {
					if(!(ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
						showControl(qWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.GREEN);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.GREEN);
						txtQ.setBackground(ColorService.RED);
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(qWarning);
					}
					return;
				}
				
				
				if(rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					if(!ptmp.equals(qtmp)) {
						if(rabinFirst.isCompositeSuitable(ptmp, qtmp)) {
							txtP.setBackground(ColorService.GREEN);
							txtQ.setBackground(ColorService.GREEN);
							hideControl(pWarning);
							hideControl(qWarning);
							hideControl(nWarning);
							if(rftc.getBtnGenKeysMan().getSelection()) {
								rftc.getBtnStartGenKeys().setEnabled(true);
							}
						}
						else {
							txtP.setBackground(ColorService.RED);
							txtQ.setBackground(ColorService.RED);
							nWarning.setText(strNotSuitableN);
							showControl(nWarning);
						}
					}
					else {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						nWarning.setText(strPnotEqualQ);
						showControl(nWarning);
					}
				}
				else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(ColorService.GREEN);
					txtQ.setBackground(ColorService.RED);
					qWarning.setText(strQnotSuitable);
					showControl(qWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.GREEN);
					pWarning.setText(strPnotSuitable);
					showControl(pWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.RED);
					pWarning.setText(strPnotSuitable);
					qWarning.setText(strQnotSuitable);
					showControl(pWarning);
					showControl(qWarning);
				}
			}

		}
	}*/
	
	
	
	public void updateTextfields(RabinFirstTabComposite rftc) {
		BigInteger ptmp = null;
		BigInteger qtmp = null;
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		//RabinFirstTabComposite rftc = null;
		//RabinSecondTabComposite rstc = null;
		
		Color colorBackgroundCorrect = this.colorBackgroundCorrect;
		Color colorBackgroundWrong = this.colorBackgroundWrong;
		Color colorBackgroundNeutral = this.colorBackgroundNeutral;
		
		String strNumberRestriction = Messages.GUIHandler_strNumberRestriction;
		String strMissingP = Messages.GUIHandler_strMissingP;
		String strUpperLimitRestriction = Messages.GUIHandler_strUpperLimitRestriction;
		
		String strQnotSuitable = Messages.GUIHandler_strQnotSuitable;
		String strMissingQ = Messages.GUIHandler_strMissingQ;
		String strPnotEqualQ = Messages.GUIHandler_strPnotEqualQ;
		String strPnotSuitable = Messages.GUIHandler_strPnotSuitable;
		String strNotSuitableN = Messages.GUIHandler_strNotSuitableN;
		
		
		if(rftc.getBtnGenKeysMan().getSelection())
			rftc.getBtnStartGenKeys().setEnabled(false);
		
		Combo cmbP = rftc.getCmbP();
		Combo cmbQ = rftc.getCmbQ();
		Text pWarning = rftc.getPWarning();
		Text qWarning = rftc.getQWarning();
		Text nWarning = rftc.getNWarning();
		
		
		String pAsStr = cmbP.getText();
		String qAsStr = cmbQ.getText();
		
		if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
			
			cmbP.setBackground(colorBackgroundNeutral);
			cmbQ.setBackground(colorBackgroundNeutral);
			hideControl(nWarning);
			hideControl(pWarning);
			hideControl(qWarning);
			return;
		}
		
		if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
			hideControl(nWarning);
			hideControl(pWarning);
			hideControl(qWarning);
			
			if(!qAsStr.matches(pattern)) {
				cmbP.setBackground(colorBackgroundNeutral);
				cmbQ.setBackground(colorBackgroundWrong);
				qWarning.setText(strNumberRestriction);
				showControl(qWarning);
				return;
			}
			
			cmbP.setBackground(colorBackgroundWrong);
			pWarning.setText(strMissingP);
			showControl(pWarning);
			
			qtmp = new BigInteger(qAsStr);
			
			if(qtmp.compareTo(limitUpPQ) > 0) {
				cmbQ.setBackground(colorBackgroundWrong);
				qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExpPQ));
				showControl(qWarning);
				return;
			}
			
			if(rabinFirst.isSuitablePrime(qtmp)) {
				
				cmbQ.setBackground(colorBackgroundCorrect);
				pWarning.setText(strMissingP);
				showControl(pWarning);
			}
			else {
				cmbQ.setBackground(colorBackgroundWrong);
				qWarning.setText(strQnotSuitable);
				pWarning.setText(strMissingP);
				showControl(pWarning);
				showControl(qWarning);
			}
			return;
		}
		
		if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
			hideControl(nWarning);
			hideControl(pWarning);
			hideControl(qWarning);
			
			if(!pAsStr.matches(pattern)) {
				cmbP.setBackground(colorBackgroundWrong);
				cmbQ.setBackground(colorBackgroundNeutral);
				pWarning.setText(strNumberRestriction);
				showControl(pWarning);
				return;
			}
			
			cmbQ.setBackground(colorBackgroundWrong);
			qWarning.setText(strMissingQ);
			showControl(qWarning);
			ptmp = new BigInteger(pAsStr);
			
			
			if(ptmp.compareTo(limitUpPQ) > 0) {
				cmbP.setBackground(colorBackgroundWrong);
				pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExpPQ));
				showControl(pWarning);
				return;
			}
			
			if(rabinFirst.isSuitablePrime(ptmp)) {
				cmbP.setBackground(colorBackgroundCorrect);
				qWarning.setText(strMissingQ);
				showControl(qWarning);
			}
			else {
				cmbP.setBackground(colorBackgroundWrong);
				qWarning.setText(strMissingQ);
				pWarning.setText(strPnotSuitable);
				showControl(qWarning);
				showControl(pWarning);
			}
			return;
		}
		
		if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
			hideControl(nWarning);
			hideControl(pWarning);
			hideControl(qWarning);
			
			boolean checkPattern = pAsStr.matches(pattern) && qAsStr.matches(pattern);
			
			if(!checkPattern) {
				if(!pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
					cmbP.setBackground(colorBackgroundWrong);
					cmbQ.setBackground(colorBackgroundWrong);
					pWarning.setText(strNumberRestriction);
					qWarning.setText(strNumberRestriction);
					showControl(pWarning);
					showControl(qWarning);
				}
				if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
					cmbP.setBackground(colorBackgroundWrong);
					cmbQ.setBackground(colorBackgroundNeutral);
					pWarning.setText(strNumberRestriction);
					showControl(pWarning);
				}
				if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
					cmbP.setBackground(colorBackgroundNeutral);
					cmbQ.setBackground(colorBackgroundWrong);
					qWarning.setText(strNumberRestriction);
					showControl(qWarning);
				}
				return;
				
			}
			
			ptmp = new BigInteger(pAsStr);
			qtmp = new BigInteger(qAsStr);
			
			boolean checkUpperLimit = ptmp.compareTo(limitUpPQ) <= 0 && qtmp.compareTo(limitUpPQ) <= 0;
			
			if(!checkUpperLimit) {
				if(!(ptmp.compareTo(limitUpPQ) <= 0) && !(qtmp.compareTo(limitUpPQ) <= 0)) {
					cmbP.setBackground(colorBackgroundWrong);
					cmbQ.setBackground(colorBackgroundWrong);
					pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExpPQ));
					qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExpPQ));
					showControl(pWarning);
					showControl(qWarning);
				}
				
				if(!(ptmp.compareTo(limitUpPQ) <= 0) && (qtmp.compareTo(limitUpPQ) <= 0)) {
					cmbP.setBackground(colorBackgroundWrong);
					cmbQ.setBackground(colorBackgroundCorrect);
					pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExpPQ));
					showControl(pWarning);
				}
				
				if((ptmp.compareTo(limitUpPQ) <= 0) && !(qtmp.compareTo(limitUpPQ) <= 0)) {
					cmbP.setBackground(colorBackgroundCorrect);
					cmbQ.setBackground(colorBackgroundWrong);
					qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExpPQ));
					showControl(qWarning);
				}
				return;
			}
			
			
			if(rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
				
				if(!ptmp.equals(qtmp)) {
					if(rabinFirst.isCompositeSuitable(ptmp, qtmp)) {
						cmbP.setBackground(colorBackgroundCorrect);
						cmbQ.setBackground(colorBackgroundCorrect);
						hideControl(pWarning);
						hideControl(qWarning);
						hideControl(nWarning);
						if(rftc.getBtnGenKeysMan().getSelection()) {
							rftc.getBtnStartGenKeys().setEnabled(true);
						}
					}
					else {
						cmbP.setBackground(colorBackgroundWrong);
						cmbQ.setBackground(colorBackgroundWrong);
						nWarning.setText(strNotSuitableN);
						showControl(nWarning);
					}
				}
				else {
					cmbP.setBackground(colorBackgroundWrong);
					cmbQ.setBackground(colorBackgroundWrong);
					nWarning.setText(strPnotEqualQ);
					showControl(nWarning);
				}
			}
			else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
				
				cmbP.setBackground(colorBackgroundCorrect);
				cmbQ.setBackground(colorBackgroundWrong);
				qWarning.setText(strQnotSuitable);
				showControl(qWarning);
			}
			else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
				
				cmbP.setBackground(colorBackgroundWrong);
				cmbQ.setBackground(colorBackgroundCorrect);
				pWarning.setText(strPnotSuitable);
				showControl(pWarning);
			}
			else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
				cmbP.setBackground(colorBackgroundWrong);
				cmbQ.setBackground(colorBackgroundWrong);
				pWarning.setText(strPnotSuitable);
				qWarning.setText(strQnotSuitable);
				showControl(pWarning);
				showControl(qWarning);
			}
		}
			

		
		/*else {
			rftc = (RabinFirstTabComposite) tabComposite;
			
			if(rftc.getBtnGenKeysMan().getSelection())
				rftc.getBtnStartGenKeys().setEnabled(false);
			
			Text txtP = rftc.getCmbP();
			Text txtQ = rftc.getCmbQ();
			Text pWarning = rftc.getPWarning();
			Text qWarning = rftc.getQWarning();
			Text nWarning = rftc.getNWarning();
			
			
			String pAsStr = txtP.getText();
			String qAsStr = txtQ.getText();
			
			if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
				
				txtP.setBackground(colorBackgroundNeutral);
				txtQ.setBackground(colorBackgroundNeutral);
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				return;
			}
			
			if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				if(!qAsStr.matches(pattern)) {
					txtP.setBackground(colorBackgroundNeutral);
					txtQ.setBackground(colorBackgroundWrong);
					qWarning.setText(strNumberRestriction);
					showControl(qWarning);
					return;
				}
				
				txtP.setBackground(colorBackgroundWrong);
				pWarning.setText(strMissingP);
				showControl(pWarning);
				
				qtmp = new BigInteger(qAsStr);
				
				if(qtmp.compareTo(limitUp) > 0) {
					txtQ.setBackground(colorBackgroundWrong);
					qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(qWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(qtmp)) {
					
					txtQ.setBackground(colorBackgroundCorrect);
					pWarning.setText(strMissingP);
					showControl(pWarning);
				}
				else {
					txtQ.setBackground(colorBackgroundWrong);
					qWarning.setText(strQnotSuitable);
					pWarning.setText(strMissingP);
					showControl(pWarning);
					showControl(qWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				if(!pAsStr.matches(pattern)) {
					txtP.setBackground(colorBackgroundWrong);
					txtQ.setBackground(colorBackgroundNeutral);
					pWarning.setText(strNumberRestriction);
					showControl(pWarning);
					return;
				}
				
				txtQ.setBackground(colorBackgroundWrong);
				qWarning.setText(strMissingQ);
				showControl(qWarning);
				ptmp = new BigInteger(pAsStr);
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					txtP.setBackground(colorBackgroundWrong);
					pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(pWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(ptmp)) {
					txtP.setBackground(colorBackgroundCorrect);
					qWarning.setText(strMissingQ);
					showControl(qWarning);
				}
				else {
					txtP.setBackground(colorBackgroundWrong);
					qWarning.setText(strMissingQ);
					pWarning.setText(strPnotSuitable);
					showControl(qWarning);
					showControl(pWarning);
				}
				return;
			}
			
			if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
				hideControl(nWarning);
				hideControl(pWarning);
				hideControl(qWarning);
				
				boolean checkPattern = pAsStr.matches(pattern) && qAsStr.matches(pattern);
				
				if(!checkPattern) {
					if(!pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(colorBackgroundWrong);
						txtQ.setBackground(colorBackgroundWrong);
						pWarning.setText(strNumberRestriction);
						qWarning.setText(strNumberRestriction);
						showControl(pWarning);
						showControl(qWarning);
					}
					if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
						txtP.setBackground(colorBackgroundWrong);
						txtQ.setBackground(colorBackgroundNeutral);
						pWarning.setText(strNumberRestriction);
						showControl(pWarning);
					}
					if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(colorBackgroundNeutral);
						txtQ.setBackground(colorBackgroundWrong);
						qWarning.setText(strNumberRestriction);
						showControl(qWarning);
					}
					return;
					
				}
				
				ptmp = new BigInteger(pAsStr);
				qtmp = new BigInteger(qAsStr);
				
				boolean checkUpperLimit = ptmp.compareTo(limitUp) <= 0 && qtmp.compareTo(limitUp) <= 0;
				
				if(!checkUpperLimit) {
					if(!(ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(colorBackgroundWrong);
						txtQ.setBackground(colorBackgroundWrong);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
						showControl(qWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(colorBackgroundWrong);
						txtQ.setBackground(colorBackgroundCorrect);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(colorBackgroundCorrect);
						txtQ.setBackground(colorBackgroundWrong);
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(qWarning);
					}
					return;
				}
				
				
				if(rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					if(!ptmp.equals(qtmp)) {
						if(rabinFirst.isCompositeSuitable(ptmp, qtmp)) {
							txtP.setBackground(colorBackgroundCorrect);
							txtQ.setBackground(colorBackgroundCorrect);
							hideControl(pWarning);
							hideControl(qWarning);
							hideControl(nWarning);
							if(rftc.getBtnGenKeysMan().getSelection()) {
								rftc.getBtnStartGenKeys().setEnabled(true);
							}
						}
						else {
							txtP.setBackground(colorBackgroundWrong);
							txtQ.setBackground(colorBackgroundWrong);
							nWarning.setText(strNotSuitableN);
							showControl(nWarning);
						}
					}
					else {
						txtP.setBackground(colorBackgroundWrong);
						txtQ.setBackground(colorBackgroundWrong);
						nWarning.setText(strPnotEqualQ);
						showControl(nWarning);
					}
				}
				else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(colorBackgroundCorrect);
					txtQ.setBackground(colorBackgroundWrong);
					qWarning.setText(strQnotSuitable);
					showControl(qWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(colorBackgroundWrong);
					txtQ.setBackground(colorBackgroundCorrect);
					pWarning.setText(strPnotSuitable);
					showControl(pWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					txtP.setBackground(colorBackgroundWrong);
					txtQ.setBackground(colorBackgroundWrong);
					pWarning.setText(strPnotSuitable);
					qWarning.setText(strQnotSuitable);
					showControl(pWarning);
					showControl(qWarning);
				}
			}

		}*/
	}
	
	
	
	
	/**
	 * @param elem
	 * @param a
	 * @return startIdx the startIdx of elem in a
	 */
	public int getStartIdx(int elem, ArrayList<String> a) {
		int startIdx = 0;
		for(int i = 0; i < elem - 1; i++) {
			startIdx += a.get(i).length();
			startIdx += separator.length() + 2;
		}
		return startIdx;
	}
	
	/**
	 * @param startIdx
	 * @param elem
	 * @param a
	 * @return endIdx the endIdx of elem in a
	 */
	public int getEndIdx(int startIdx, int elem, ArrayList<String> a) {
		int endIdx = startIdx + a.get(elem - 1).length();
		return endIdx;
	}
		
		
		
		
		
		
		
	
	
	
	
	
	
	
	
		
	
	/**
	 * not used
	 * @param e
	 */
	public void verifyControlFields(VerifyEvent e) {
		Text txt = null;
		CCombo cmb = null;
		
		if(e.getSource() instanceof CCombo) {
			cmb = (CCombo) e.getSource();
			
			boolean doit = true;
			
			if((cmb.getText().length() == 0 && e.text.compareTo("0") == 0) //$NON-NLS-1$
					|| !(e.text.matches("\\d")) //$NON-NLS-1$
					//|| cmb.getText().length() == 5
					|| cmb == null)
				doit = false;
			
			if(e.character == '\b')
				doit = true;

			e.doit = doit;
		}
		else if(e.getSource() instanceof Text) {
			txt = (Text) e.getSource();
			
			boolean doit = true;
			
			if((txt.getText().length() == 0 && e.text.compareTo("0") == 0) //$NON-NLS-1$
					|| !(e.text.matches("\\d")) //$NON-NLS-1$
					//|| cmb.getText().length() == 5
					|| txt == null)
				doit = false;
			
			if(e.character == '\b')
				doit = true;

			e.doit = doit;
		}
	}
	


	
	
	
	
	
	
}
