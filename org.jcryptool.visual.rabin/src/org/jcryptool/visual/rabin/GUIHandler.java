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
	private BigInteger limitUp = BigInteger.TWO.pow(limitExp);
	private Rabin rabinFirst;
	private Rabin rabinSecond;
	private int bytesPerBlock;
	private int blocklength;
	private int radix = 16;
	private String separator = "||"; //$NON-NLS-1$
	
	// colors for p and q in Cryptosystem and Algorithm tab
	private Color colorBackgroundCorrect = ColorService.LIGHT_AREA_GREEN;
	private Color colorBackgroundWrong = ColorService.LIGHT_AREA_RED;
	private Color colorBackgroundNeutral = ColorService.WHITE;
	
	// colors for warning textfields	
	private Color colorBackgroundWarning = ColorService.LIGHTGRAY;
	private Color colorForegroundWarning = ColorService.RED;
	
	// colors for info textfields
	private Color colorBGinfo = ColorService.LIGHTGRAY;
	
	// colors for selecting controls
	private Color colorSelectControl = ColorService.LIGHT_AREA_BLUE;
	private Color colorDeselectControl = ColorService.LIGHTGRAY;
	
	// colors for buttons
	private Color colorButtons = ColorService.LIGHT_AREA_BLUE;
	
	
	
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
	
	
	public Color getColorSelectControl() {
		return colorSelectControl;
	}
	
	
	public Color getColorDeselectControl() {
		return colorDeselectControl;
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
	
	
	
	public void updateTextfields(Control tabComposite) {
		BigInteger ptmp = null;
		BigInteger qtmp = null;
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		RabinFirstTabComposite rftc = null;
		RabinSecondTabComposite rstc = null;
		
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
				
				if(qtmp.compareTo(limitUp) > 0) {
					cmbQ.setBackground(colorBackgroundWrong);
					qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
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
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					cmbP.setBackground(colorBackgroundWrong);
					pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
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
				
				boolean checkUpperLimit = ptmp.compareTo(limitUp) <= 0 && qtmp.compareTo(limitUp) <= 0;
				
				if(!checkUpperLimit) {
					if(!(ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(colorBackgroundWrong);
						cmbQ.setBackground(colorBackgroundWrong);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
						showControl(qWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(colorBackgroundWrong);
						cmbQ.setBackground(colorBackgroundCorrect);
						pWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
						showControl(pWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						cmbP.setBackground(colorBackgroundCorrect);
						cmbQ.setBackground(colorBackgroundWrong);
						qWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
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
							if(rstc.getBtnGenKeysMan().getSelection()) {
								rstc.getBtnStartGenKeys().setEnabled(true);
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

		}
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
