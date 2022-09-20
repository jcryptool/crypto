package org.jcryptool.visual.rabin;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.jcryptool.core.operations.editors.AbstractEditorService;
import org.jcryptool.core.operations.editors.EditorsManager;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.jcryptool.visual.rabin.ui.RabinFirstTabComposite;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;


public class HandleFirstTab extends GUIHandler {
	
	private String strOnlyDecAllowed = Messages.HandleFirstTab_strOnlyDecAllowed;
	private String strUpperLimitRestriction = Messages.HandleFirstTab_strUpperLimitRestriction;
	private String strLowerLimitLessUpperLimit = Messages.HandleFirstTab_strLowerLimitLessUpperLimit;
	private String strAppropriatePrimesWarning = Messages.HandleFirstTab_strAppropriatePrimesWarning;
	public boolean stopComputation = false;

//	/**
//	 * @param scMain
//	 * @param compMain
//	 * @param rabinFirst
//	 * @param rabinSecond
//	 */
//	public HandleFirstTab(ScrolledComposite scMain, Composite compMain, Rabin rabinFirst, Rabin rabinSecond) {
//		super(scMain, compMain, rabinFirst, rabinSecond);
//	}
	
	/**
	 * @param scMain
	 * @param compMain
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public HandleFirstTab(ScrolledComposite scMain, Composite compMain, Rabin rabin) {
		super(scMain, compMain, rabin);
	}
	
	
	
	
	
	
	/**
	 * parses strings of the form decmial^decimal
	 * @param limit
	 * @return parsed integer of the form decimal^decimal
	 */
	private BigInteger getParsedNumFromLimit(String limit) {
		BigInteger ret = null;
		String pattern = "\\^"; //$NON-NLS-1$
		
		String[] parsedNum = limit.split(pattern);
		BigInteger base = new BigInteger(parsedNum[0]);
		int exp = Integer.parseInt(parsedNum[1]);
		ret = base.pow(exp);
		
		return ret;
		
	}
	
	
	
	/**
	 * @param limit
	 * @return converted string limit to BigInteger
	 */
	private BigInteger getNumFromLimit(String limit) {
		String pattern = "^([1-9]\\d*)$"; //$NON-NLS-1$
		BigInteger ret = null;
		
		if(limit.matches(pattern)) {
			ret = new BigInteger(limit);
			return ret;
		}
		
		ret = getParsedNumFromLimit(limit);
		return ret;
	}
	
	
	
	
	
	
	/**
	 * method as a thread, not used
	 * @param rftc
	 * @param rstc
	 * @param strLow
	 * @param strUp
	 * @param iterations
	 * @return
	 */
	private Thread generateKeysWithLimitSingle(RabinFirstTabComposite rftc, RabinSecondTabComposite rstc, String strLow, String strUp, int iterations) {
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				BigInteger low = null; 
				BigInteger up = null; 

				low = getNumFromLimit(strLow);
				up = getNumFromLimit(strUp);
				
				int iteration = iterations;
				
				BigInteger maxMin = up.subtract(low);
				
				Random rand = new SecureRandom();
				
				BigInteger primeP = null;
				BigInteger primeQ = null;
				BigInteger resP = null;
				BigInteger resQ = null;
				stopComputation = false;
				
				do {
					resP = new BigInteger(up.bitLength(), rand);
					resQ = new BigInteger(up.bitLength(), rand);
					
					if(resP.compareTo(low) < 0) {
						resP = resP.add(low);
					}
					
					if(resQ.compareTo(low) < 0) {
						resQ = resQ.add(low);
					}
					
					if(resP.compareTo(up) >= 0) {
						resP = resP.mod(maxMin).add(low);
					}
					
					if(resQ.compareTo(up) >= 0) {
						resQ = resQ.mod(maxMin).add(low);
					}
					
					primeP = resP.nextProbablePrime();
					primeQ = resQ.nextProbablePrime();
					
					iteration--;
					
					// delete the stopComputation is the while loop if you want to remove the
					// stop button
				}while(!(rabinFirst.isAppropriatePrime(primeP, low, up)
							&& rabinFirst.isAppropriatePrime(primeQ, low, up)
							&& !primeP.equals(primeQ)
							&& rabinFirst.isCompositeSuitable(primeP, primeQ)
							) && iterations > 0 && !stopComputation);
				
				if(stopComputation) {
					return;
				}
				
				
				if(iterations == 0) {
					
					Runnable r = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							rftc.txtSinglePQWarning.setText(strAppropriatePrimesWarning);
							showControl(rftc.txtSinglePQWarning);
							
						}
					};
					
					Display.getDefault().asyncExec(r);
					
					return;
					
				}
				
				Runnable r2 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						hideControl(rftc.txtSinglePQWarning);
						
					}
				};
					
				Display.getDefault().asyncExec(r2);
				
				String strPrimeP = primeP.toString();
				String strPrimeQ = primeQ.toString();
			
				BigInteger p = new BigInteger(strPrimeP);
				BigInteger q = new BigInteger(strPrimeQ);
				BigInteger n = p.multiply(q);
				rabinFirst.setP(p);
				rabinFirst.setQ(q);
				rabinFirst.setN(n);
				
					
				Runnable r3 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						rftc.cmbP.setText(strPrimeP);
						rftc.cmbQ.setText(strPrimeQ);
						rftc.txtModN.setText(n.toString());
					}
				};				
				
				Display.getDefault().asyncExec(r3);
				
				
				int bitlength = rabinFirst.getN().bitLength();
				int maxBytesPerBlock = bitlength / 8;
						
				int ibytesPerBlock = (bitlength / 8) * 2;
				int blocklength = ((bitlength / 8) + 1) * 2;
				bytesPerBlock = ibytesPerBlock;
				blocklength = blocklength;
				
				rstc.guiHandler.blocklength = blocklength;
				rstc.guiHandler.bytesPerBlock = 2;
				
				Runnable r4 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						rstc.cmbBlockN.removeAll();
						
						for(int i = 1; i <= maxBytesPerBlock; i++) {
							rstc.cmbBlockN.add(String.valueOf(i));
						}
						
						rstc.cmbBlockN.select(0);
						
						// to eliminate warnings
						rstc.guiHandler.handlePlaintextTextMode(rstc);
						rstc.guiHandler.handleDecimalNumbersEncDecMode(rstc);
						rstc.guiHandler.handleHexNumDecMode(rstc);
						rstc.guiHandler.handleDecimalNumbersDecMode(rstc);
						
						// for current mode
						if(rstc.btnSelectionEnc.getSelection()) {
							if(rstc.btnText.getSelection()) {
								rstc.guiHandler.handlePlaintextTextMode(rstc);
							}
							if(rstc.btnNum.getSelection()) {
								rstc.guiHandler.handleDecimalNumbersEncDecMode(rstc);
							}
						}
						
						if(rstc.btnSelectionDec.getSelection()) {
							if(rstc.btnRadHex.getSelection()) {
								rstc.guiHandler.handleHexNumDecMode(rstc);
							}
							if(rstc.btnRadDecimal.getSelection()) {
								rstc.guiHandler.handleDecimalNumbersDecMode(rstc);
							}
						}
						
						
						rftc.txtLowLimP.setBackground(ColorService.WHITE);
						rftc.txtLowLimQ.setBackground(ColorService.WHITE);
						rftc.txtUpperLimP.setBackground(ColorService.WHITE);
						rftc.txtUpperLimQ.setBackground(ColorService.WHITE);
						
						hideControl(rftc.txtcompGenPandQWarning);
				
					}
				};
				
				Display.getDefault().asyncExec(r4);
			}
		});
		
		return t;

	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @param rftc
	 * @param strLow
	 * @param strUp
	 * @param iterations
	 * @return true if keys were successfully generated, else false
	 */
	private boolean generateKeysWithLimitSingle(RabinFirstTabComposite rftc, String strLow, String strUp, int iterations) {
	

		
		BigInteger low = null; 
		BigInteger up = null; 

		low = getNumFromLimit(strLow);
		up = getNumFromLimit(strUp);
		
		
		BigInteger maxMin = up.subtract(low);
		
		Random rand = new SecureRandom();
		
		BigInteger primeP = null;
		BigInteger primeQ = null;
		BigInteger resP = null;
		BigInteger resQ = null;
		this.stopComputation = false;
		
		do {
			resP = new BigInteger(up.bitLength(), rand);
			resQ = new BigInteger(up.bitLength(), rand);
			
			if(resP.compareTo(low) < 0) {
				resP = resP.add(low);
			}
			
			if(resQ.compareTo(low) < 0) {
				resQ = resQ.add(low);
			}
			
			if(resP.compareTo(up) >= 0) {
				resP = resP.mod(maxMin).add(low);
			}
			
			if(resQ.compareTo(up) >= 0) {
				resQ = resQ.mod(maxMin).add(low);
			}
			
			primeP = resP.nextProbablePrime();
			primeQ = resQ.nextProbablePrime();
			
			iterations--;
			
			// delete the stopComputation is the while loop if you want to remove the
			// stop button
		}while(!(rabinFirst.isAppropriatePrime(primeP, low, up)
					&& rabinFirst.isAppropriatePrime(primeQ, low, up)
					&& !primeP.equals(primeQ)
					&& rabinFirst.isCompositeSuitable(primeP, primeQ)
					) && iterations > 0 && !this.stopComputation);
		
		if(this.stopComputation) {
			return false;
		}
		
		
		if(iterations == 0) {
			rftc.txtSinglePQWarning.setText(strAppropriatePrimesWarning);
			showControl(rftc.txtSinglePQWarning);
			return false;
		}
		
			
		hideControl(rftc.txtSinglePQWarning);
		
		String strPrimeP = primeP.toString();
		String strPrimeQ = primeQ.toString();
	
		BigInteger p = new BigInteger(strPrimeP);
		BigInteger q = new BigInteger(strPrimeQ);
		BigInteger n = p.multiply(q);
		rabinFirst.setP(p);
		rabinFirst.setQ(q);
		rabinFirst.setN(n);
		
		
		
		
		rftc.cmbP.setText(strPrimeP);
		rftc.cmbQ.setText(strPrimeQ);
		
			
		
		rftc.txtModN.setText(n.toString());
		
		return true;
			
		

	}
	
	
	
	
	
	
	/**
	 * @param rftc
	 * @param strLowP
	 * @param strUpP
	 * @param strLowQ
	 * @param strUpQ
	 * @param iterations
	 * @return true if keys were successfully generated, else false
	 */
	private boolean generateKeysWithLimit(RabinFirstTabComposite rftc, String strLowP, String strUpP, String strLowQ, String strUpQ, int iterations) {
		
		BigInteger lowP = null; 
		BigInteger upP = null; 
		BigInteger lowQ = null; 
		BigInteger upQ = null;
		
		
		lowP = getNumFromLimit(strLowP);
		upP = getNumFromLimit(strUpP);
		lowQ = getNumFromLimit(strLowQ);
		upQ = getNumFromLimit(strUpQ);
		
		BigInteger maxMinP = upP.subtract(lowP);
		BigInteger maxMinQ = upQ.subtract(lowQ);
		Random rand = new SecureRandom();
		
		BigInteger primeP = null;
		BigInteger primeQ = null;
		BigInteger resP = null;
		BigInteger resQ = null;
		
		do {
			resP = new BigInteger(upP.bitLength(), rand);
			resQ = new BigInteger(upQ.bitLength(), rand);
			
			if(resP.compareTo(lowP) < 0) {
				resP = resP.add(lowP);
			}
			
			if(resQ.compareTo(lowQ) < 0) {
				resQ = resQ.add(lowQ);
			}
			
			if(resP.compareTo(upP) >= 0) {
				resP = resP.mod(maxMinP).add(lowP);
			}
			
			if(resQ.compareTo(upQ) >= 0) {
				resQ = resQ.mod(maxMinQ).add(lowQ);
			}
			
			primeP = resP.nextProbablePrime();
			primeQ = resQ.nextProbablePrime();
			
			iterations--;
			
		}while(!(rabinFirst.isAppropriatePrime(primeP, lowP, upP)
					&& rabinFirst.isAppropriatePrime(primeQ, lowQ, upQ)
					&& !primeP.equals(primeQ)
					&& rabinFirst.isCompositeSuitable(primeP, primeQ)
					) && iterations > 0);
		
		
		if(iterations == 0) {
			rftc.txtcompGenPandQWarning.setText(strAppropriatePrimesWarning);
			showControl(rftc.txtcompGenPandQWarning);
			return false;
		}
		
			
		hideControl(rftc.txtcompGenPandQWarning);
		
		String strPrimeP = primeP.toString();
		String strPrimeQ = primeQ.toString();
		BigInteger p = new BigInteger(strPrimeP);
		BigInteger q = new BigInteger(strPrimeQ);
		BigInteger n = p.multiply(q);
		rabinFirst.setP(p);
		rabinFirst.setQ(q);
		rabinFirst.setN(n);		
		rftc.cmbP.setText(strPrimeP);
		rftc.cmbQ.setText(strPrimeQ);
		rftc.txtModN.setText(n.toString());
		
		return true;
	}
	
	
	
	/**
	 * action for btnGenKeysMan
	 * @param rftc
	 * @param e
	 */
	public void btnGenKeysManAction(RabinFirstTabComposite rftc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			hideControl(rftc.compHoldSelectionPrimesAndLimits);
			hideControl(rftc.compSelectPrimeGen);
			String info = this.getMessageByControl("btnGenKeysMan_selection");
			rftc.txtInfoSetParam.setText(info);
			this.updateTextfields(rftc);
		}
	}
	
	
	
	
	
	/**
	 * action for btnGenKeys
	 * @param rftc
	 * @param e
	 */
	public void btnGenKeysAction(RabinFirstTabComposite rftc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			
			this.showControl(rftc.compHoldSelectionPrimesAndLimits);
			showControl(rftc.compSelectPrimeGen);
			
			String info = this.getMessageByControl("btnGenKeys_selection");
			rftc.txtInfoSetParam.setText(info);
		
			
			if(rftc.btnSelectSingleLimit.getSelection()) {
				this.updateLimitFieldsSingle(rftc);
			}
			
			if(rftc.btnSelectMultiPandQ.getSelection()) {
				this.updateLimitFields(rftc.txtLowLimP, rftc.txtUpperLimP, rftc.txtLowLimQ, rftc.txtUpperLimQ, rftc.txtcompGenPandQWarning, rftc.btnGenKeys, rftc.btnStartGenKeys);
				
			}	
		}
	}
	

	
	
	public void initializePrimes(int numOfPrimes, RabinFirstTabComposite rftc) {
		
		for(int i = 0, count = 0; count < numOfPrimes; i++) {
			BigInteger possiblePrime = BigInteger.valueOf(i);
			if(this.rabinFirst.isSuitablePrime(possiblePrime)) {
				rftc.cmbP.add(possiblePrime.toString());
				rftc.cmbQ.add(possiblePrime.toString());
				count++;
			}
		}
	}
	
	
	
	
	
	public void cmbSelectionAction(Combo cmbP) {
		int idx = cmbP.getSelectionIndex();
		String item = cmbP.getItem(idx);
		
		cmbP.setText(item);
	}
	
	
	
	
	
	public void btnStartGenKeysAction(RabinFirstTabComposite rftc, RabinSecondTabComposite rstc, int iterations) {
		
		if(rftc.btnGenKeysMan.getSelection()) {
			this.btnGenKeysManAction(rftc.cmbP, rftc.cmbQ, rftc.txtModN);
		}
		
		if(rftc.btnGenKeys.getSelection()) {
			
			if(rftc.btnSelectSingleLimit.getSelection()) {
				
				String strLow = rftc.txtLowLimPQSingle.getText();
				String strUp = rftc.txtUpperLimPQSingle.getText();
					
				boolean success = generateKeysWithLimitSingle(rftc, strLow, strUp, iterations);
				
				if(!success)
					return;
				
			}
			
			if(rftc.btnSelectMultiPandQ.getSelection()) {
				String strLowP = rftc.txtLowLimP.getText();
				String strUpP = rftc.txtUpperLimP.getText();
				String strLowQ = rftc.txtLowLimQ.getText();
				String strUpQ = rftc.txtUpperLimQ.getText();
				
				boolean success = this.generateKeysWithLimit(rftc, strLowP, strUpP, strLowQ, strUpQ, iterations);
				if(!success)
					return;
			}
			
		}
		
		
		int bitlength = rabinFirst.getN().bitLength();
		int maxBytesPerBlock = bitlength / 8;
		
		//rftc.txtInfoModulus.setText("Number of bits of N = " + bitlength + "\n"
				//+ "Max. number of bytes to encrypt = " + maxBytesPerBlock);
		
		String txtInfoModulusStr  = "Number of bits of N = " + bitlength + " ";
		
		if(bitlength > 1)
			txtInfoModulusStr += "bits";
		else
			txtInfoModulusStr += "bit";
		
		txtInfoModulusStr += "\n";
		txtInfoModulusStr += "Max. number of bytes to encrypt = " + maxBytesPerBlock + " ";
		
		if(maxBytesPerBlock > 1)
			txtInfoModulusStr += "bytes";
		else
			txtInfoModulusStr += "byte";
		
		rftc.txtInfoModulus.setText(txtInfoModulusStr);
		
		showControl(rftc.lblSepInfoModulus);
		showControl(rftc.txtInfoModulus);
				
		int ibytesPerBlock = (bitlength / 8) * 2;
		int blocklength = ((bitlength / 8) + 1) * 2;
		bytesPerBlock = ibytesPerBlock;
		this.blocklength = blocklength;
		
		rstc.guiHandler.blocklength = blocklength;
		rstc.guiHandler.bytesPerBlock = 2;
		
		
		rstc.cmbBlockN.removeAll();
		
		for(int i = 1; i <= maxBytesPerBlock; i++) {
			rstc.cmbBlockN.add(String.valueOf(i));
		}
		
		rstc.cmbBlockN.select(0);
		
		// to eliminate warnings
		rstc.guiHandler.handlePlaintextTextMode(rstc);
		rstc.guiHandler.handleDecimalNumbersEncDecMode(rstc);
		rstc.guiHandler.handleHexNumDecMode(rstc);
		rstc.guiHandler.handleDecimalNumbersDecMode(rstc);
		
		// for current mode
		if(rstc.btnSelectionEnc.getSelection()) {
			if(rstc.btnText.getSelection()) {
				rstc.guiHandler.handlePlaintextTextMode(rstc);
			}
			if(rstc.btnNum.getSelection()) {
				rstc.guiHandler.handleDecimalNumbersEncDecMode(rstc);
			}
		}
		
		if(rstc.btnSelectionDec.getSelection()) {
			if(rstc.btnRadHex.getSelection()) {
				rstc.guiHandler.handleHexNumDecMode(rstc);
			}
			if(rstc.btnRadDecimal.getSelection()) {
				rstc.guiHandler.handleDecimalNumbersDecMode(rstc);
			}
		}
		
		
		rftc.txtLowLimP.setBackground(ColorService.WHITE);
		rftc.txtLowLimQ.setBackground(ColorService.WHITE);
		rftc.txtUpperLimP.setBackground(ColorService.WHITE);
		rftc.txtUpperLimQ.setBackground(ColorService.WHITE);
		
		hideControl(rftc.txtcompGenPandQWarning);
	}
	
	
	/**
	 * update limit fields (single mode) each time you enter a char
	 * @param rftc
	 */
	public void updateLimitFieldsSingle(RabinFirstTabComposite rftc) {
		String strLowLim = rftc.txtLowLimPQSingle.getText();
		String strUpperLim = rftc.txtUpperLimPQSingle.getText();
		//String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		String pattern = "^(1\\d+|[2-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		
		Color white = this.colorBackgroundNeutral;
		Color wrong = this.colorBackgroundWrong;
		
		if(rftc.btnGenKeys.getSelection()) 
			rftc.btnStartGenKeys.setEnabled(false);
		
		BigInteger lowPQ = null;
		BigInteger upPQ = null;
		
		if(strLowLim.isEmpty() && strUpperLim.isEmpty()) {
			this.hideControl(rftc.txtSinglePQWarning);
			rftc.txtLowLimPQSingle.setBackground(white);
			rftc.txtUpperLimPQSingle.setBackground(white);
			return;
		}
		
		if(strLowLim.isEmpty() && !strUpperLim.isEmpty()) {
			if(!strUpperLim.matches(pattern)) {	
				
				rftc.txtSinglePQWarning.setText(strOnlyDecAllowed);	
				
				this.showControl(rftc.txtSinglePQWarning);
				rftc.txtUpperLimPQSingle.setBackground(wrong);
				return;
			}
			
			hideControl(rftc.txtSinglePQWarning);
			rftc.txtUpperLimPQSingle.setBackground(white);
			rftc.txtLowLimPQSingle.setBackground(white);
			
			upPQ = getNumFromLimit(strUpperLim);
			if(upPQ.compareTo(limitUp) > 0) {
				rftc.txtSinglePQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
				showControl(rftc.txtSinglePQWarning);
				rftc.txtUpperLimPQSingle.setBackground(wrong);
			}
			else {
				hideControl(rftc.txtSinglePQWarning);
				rftc.txtUpperLimPQSingle.setBackground(white);
			}
			rftc.txtLowLimPQSingle.setBackground(white);
			return;
		}
		
		
		if(!strLowLim.isEmpty() && strUpperLim.isEmpty()) {
			if(!strLowLim.matches(pattern)) {
				rftc.txtSinglePQWarning.setText(strOnlyDecAllowed);
				this.showControl(rftc.txtSinglePQWarning);
				rftc.txtLowLimPQSingle.setBackground(wrong);
				return;
			}
			
			hideControl(rftc.txtSinglePQWarning);
			rftc.txtLowLimPQSingle.setBackground(white);
			rftc.txtUpperLimPQSingle.setBackground(white);
			
			
			return;
		}
		
		
		if(!strLowLim.isEmpty() && !strUpperLim.isEmpty()) {
			boolean checkPattern = strLowLim.matches(pattern) && strUpperLim.matches(pattern);
			
			if(!checkPattern) {
				rftc.txtSinglePQWarning.setText(strOnlyDecAllowed);
				this.showControl(rftc.txtSinglePQWarning);
					
				if(strLowLim.matches(pattern) && !strUpperLim.matches(pattern)) {
					rftc.txtLowLimPQSingle.setBackground(white);
					rftc.txtUpperLimPQSingle.setBackground(wrong);
				}
				
				if(!strLowLim.matches(pattern) && strUpperLim.matches(pattern)) {
					rftc.txtLowLimPQSingle.setBackground(wrong);
					rftc.txtUpperLimPQSingle.setBackground(white);
				}
				
				if(!strLowLim.matches(pattern) && !strUpperLim.matches(pattern)) {
					rftc.txtLowLimPQSingle.setBackground(wrong);
					rftc.txtUpperLimPQSingle.setBackground(wrong);
				}
				return;
			}
			
			this.hideControl(rftc.txtSinglePQWarning);
			rftc.txtLowLimPQSingle.setBackground(white);
			
			
			upPQ = getNumFromLimit(strUpperLim);
			lowPQ = getNumFromLimit(strLowLim);
			
			if(upPQ.compareTo(limitUp) > 0) {
				rftc.txtSinglePQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
				showControl(rftc.txtSinglePQWarning);
				rftc.txtUpperLimPQSingle.setBackground(wrong);
			}
			else {
				hideControl(rftc.txtSinglePQWarning);
				rftc.txtUpperLimPQSingle.setBackground(white);
				
				if(lowPQ.compareTo(upPQ) >= 0) {
					rftc.txtSinglePQWarning.setText(strLowerLimitLessUpperLimit);
					showControl(rftc.txtSinglePQWarning);
					rftc.txtLowLimPQSingle.setBackground(wrong);
					rftc.txtUpperLimPQSingle.setBackground(wrong);
				}
				else {
					hideControl(rftc.txtSinglePQWarning);
					rftc.txtLowLimPQSingle.setBackground(white);
					rftc.txtUpperLimPQSingle.setBackground(white);
					
					if(rftc.btnGenKeys.getSelection()) 
						rftc.btnStartGenKeys.setEnabled(true);
				}
		
			}
		}
		
	}
		
	
	
	/**
	 * update limit fields (multi mode) each time you enter a char
	 * @param txtLowLimP
	 * @param txtUpperLimP
	 * @param txtLowLimQ
	 * @param txtUpperLimQ
	 * @param txtcompGenPandQWarning
	 * @param btnGenKeys
	 * @param btnStartGenKeys
	 */
	public void updateLimitFields(Text txtLowLimP, Text txtUpperLimP, Text txtLowLimQ, Text txtUpperLimQ, Text txtcompGenPandQWarning, Button btnGenKeys, Button btnStartGenKeys) {
		String txtLowP = txtLowLimP.getText();
		String txtUpP = txtUpperLimP.getText();
		String txtLowQ = txtLowLimQ.getText();
		String txtUpQ = txtUpperLimQ.getText();
		//String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		String pattern = "^(1\\d+|[2-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		
		Color white = this.colorBackgroundNeutral;
		Color wrong = this.colorBackgroundWrong;
		
		if(btnGenKeys.getSelection()) 
			btnStartGenKeys.setEnabled(false);
		
		BigInteger lowP = null;
		BigInteger upP = null;
		BigInteger lowQ = null;
		BigInteger upQ = null;
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			hideControl(txtcompGenPandQWarning);
			txtLowLimP.setBackground(white);
			txtUpperLimP.setBackground(white);
			txtLowLimQ.setBackground(white);
			txtUpperLimQ.setBackground(white);
			return;
		}
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			if(!txtUpQ.matches(pattern)) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				txtUpperLimQ.setBackground(wrong);
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimQ.setBackground(white);
				
				upQ = getNumFromLimit(txtUpQ);
				if(upQ.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimQ.setBackground(wrong);
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimQ.setBackground(white);
				}
			}
			
			txtUpperLimP.setBackground(white);
			txtLowLimP.setBackground(white);
			txtLowLimQ.setBackground(white);
			return;
		}
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			if(!txtLowQ.matches(pattern)) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				txtLowLimQ.setBackground(wrong);
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimQ.setBackground(white);
			}
			
			txtUpperLimP.setBackground(white);
			txtLowLimP.setBackground(white);
			txtUpperLimQ.setBackground(white);
			
			return;
		}
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(white);
				}
				if(txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimQ.setBackground(white);
					txtUpperLimQ.setBackground(wrong);
				}
			}
			else {
				
				hideControl(txtcompGenPandQWarning);
				txtLowLimQ.setBackground(white);
				txtUpperLimQ.setBackground(white);
				
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				
				if(upQ.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimQ.setBackground(wrong);
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimQ.setBackground(white);
					
					if(lowQ.compareTo(upQ) >= 0) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
					}
				}
				
				
			}
			txtLowLimP.setBackground(white);
			txtUpperLimP.setBackground(white);
			
			return;
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			if(!txtUpP.matches(pattern)) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				txtUpperLimP.setBackground(wrong);
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimP.setBackground(white);
				
				upP = getNumFromLimit(txtUpP);
				if(upP.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(wrong);
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);
				}
			}
			
			txtLowLimP.setBackground(white);
			txtLowLimQ.setBackground(white);
			txtUpperLimQ.setBackground(white);
			
			return;
			
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtUpQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(white);
					txtUpperLimQ.setBackground(wrong);
				}
				
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimQ.setBackground(white);
				txtUpperLimP.setBackground(white);
				
				upP = getNumFromLimit(txtUpP);
				upQ = getNumFromLimit(txtUpQ);
				
				boolean checkLimit = upP.compareTo(limitUp) <= 0 && upQ.compareTo(limitUp) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(limitUp) <= 0) && (upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
					}
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);
					txtUpperLimQ.setBackground(white);
				}
			}
			txtLowLimP.setBackground(white);
			txtLowLimQ.setBackground(white);
			
			return;
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(white);
					txtLowLimQ.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimQ.setBackground(white);
				txtUpperLimP.setBackground(white);
				
				upP = getNumFromLimit(txtUpP);
				if(upP.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(wrong);
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);	
				}
			}
			txtLowLimP.setBackground(white);
			txtUpperLimQ.setBackground(white);
			
			return;
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			
			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern)
					&& txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(white);
				}
				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimQ.setBackground(white);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimQ.setBackground(white);
					txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(white);
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(white);
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(white);
					txtLowLimQ.setBackground(white);
					txtUpperLimQ.setBackground(wrong);
				}
				
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimP.setBackground(white);
				txtLowLimQ.setBackground(white);
				txtUpperLimQ.setBackground(white);
				
				
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				upP =getNumFromLimit(txtUpP);
				
				boolean checkLimit = upP.compareTo(limitUp) <= 0 && upQ.compareTo(limitUp) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(limitUp) <= 0) && (upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
					}
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);
					txtUpperLimQ.setBackground(white);
					
					if(lowQ.compareTo(upQ) >= 0) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
						txtUpperLimP.setBackground(white);
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
						txtUpperLimP.setBackground(white);
					}
				}	
			}
			txtLowLimP.setBackground(white);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			if(!txtLowP.matches(pattern)) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				txtLowLimP.setBackground(wrong);
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimP.setBackground(white);
			}
			
			txtUpperLimP.setBackground(white);
			txtLowLimQ.setBackground(white);
			txtUpperLimQ.setBackground(white);
			
			return;

		}
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtUpQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtLowP.matches(pattern) && txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtUpperLimQ.setBackground(white);
				}
				if(txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(white);
					txtUpperLimQ.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimP.setBackground(white);
				txtUpperLimQ.setBackground(white);
				
				upQ = getNumFromLimit(txtUpQ);
				
				if(upQ.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimQ.setBackground(wrong);
				}
				else {
					txtUpperLimQ.setBackground(white);
					hideControl(txtcompGenPandQWarning);
				}
			}
			txtUpperLimP.setBackground(white);
			txtLowLimQ.setBackground(white);
			
			return;

		}
		
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
				}
				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtLowLimQ.setBackground(white);
				}
				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtLowLimP.setBackground(white);
					txtLowLimQ.setBackground(wrong);
				}
				
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimP.setBackground(white);
				txtLowLimQ.setBackground(white);
			}
			txtUpperLimP.setBackground(white);
			txtUpperLimQ.setBackground(white);
			
			return;

		}
		
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern)
					&& txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
					&& !txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
				}
				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
				}
				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(txtLowP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimP.setBackground(white);
				txtLowLimQ.setBackground(white);
				txtUpperLimQ.setBackground(white);

				
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				
				if(upQ.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimQ.setBackground(wrong);
				}
				else {
					txtUpperLimQ.setBackground(white);
					hideControl(txtcompGenPandQWarning);
					
					if(lowQ.compareTo(upQ) >= 0) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
						txtLowLimP.setBackground(white);
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
						txtLowLimP.setBackground(white);
					}
				}
				
				
			}
			txtUpperLimP.setBackground(white);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtUpP.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtUpperLimP.setBackground(wrong);
				}
				if(!txtLowP.matches(pattern) && txtUpP.matches(pattern)) {
					txtLowLimP.setBackground(wrong);
					txtUpperLimP.setBackground(white);
				}
				if(txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
					txtLowLimP.setBackground(white);
					txtUpperLimP.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtLowLimP.setBackground(white);
				txtUpperLimP.setBackground(white);
	
				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				
				if(upP.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(wrong);
				}
				else {
					txtUpperLimP.setBackground(white);
					hideControl(txtcompGenPandQWarning);
					
					if(lowP.compareTo(upP) >= 0) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
						txtLowLimP.setBackground(wrong);
						txtUpperLimP.setBackground(wrong);
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtLowLimP.setBackground(white);
						txtUpperLimP.setBackground(white);
					}
				}
				
				
			}
			txtLowLimQ.setBackground(white);
			txtUpperLimQ.setBackground(white);
			
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
					&& txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
					&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimP.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimP.setBackground(white);
				txtLowLimP.setBackground(white);
				txtUpperLimQ.setBackground(white);

				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				upQ = getNumFromLimit(txtUpQ);
				
				boolean checkLimit = upP.compareTo(limitUp) <= 0 && upQ.compareTo(limitUp) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(limitUp) <= 0) && (upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
					}
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);
					txtUpperLimQ.setBackground(white);
					
					if(lowP.compareTo(upP) >= 0) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
						txtLowLimP.setBackground(wrong);
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtLowLimP.setBackground(white);
						txtUpperLimP.setBackground(white);
						txtUpperLimQ.setBackground(white);
					}
				}
			}
			txtLowLimQ.setBackground(white);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
					&& txtLowQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
					&& !txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(wrong);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimP.setBackground(white);
				txtLowLimP.setBackground(white);
				txtLowLimQ.setBackground(white);

				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				
				if(upP.compareTo(limitUp) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(wrong);
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);
	
					
					if(lowP.compareTo(upP) >= 0) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
						txtLowLimP.setBackground(wrong);
						txtUpperLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtLowLimP.setBackground(white);
						txtUpperLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
					}
				}
				
				
			}
			txtUpperLimQ.setBackground(white);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			
			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
					&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
				showControl(txtcompGenPandQWarning);
				
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
					&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(wrong);
					txtLowLimP.setBackground(wrong);
					txtLowLimQ.setBackground(wrong);
					txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(wrong);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(wrong);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
				}
			}
			else {
				hideControl(txtcompGenPandQWarning);
				txtUpperLimP.setBackground(white);
				txtLowLimP.setBackground(white);
				txtLowLimQ.setBackground(white);
				txtUpperLimQ.setBackground(white);

				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				
				boolean checkLimit = upP.compareTo(limitUp) <= 0 && upQ.compareTo(limitUp) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, limitExp));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(limitUp) <= 0) && (upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(limitUp) <= 0) && !(upQ.compareTo(limitUp) <= 0)) {
						txtUpperLimP.setBackground(white);
						txtUpperLimQ.setBackground(wrong);
					}
				}
				else {
					hideControl(txtcompGenPandQWarning);
					txtUpperLimP.setBackground(white);
					txtUpperLimQ.setBackground(white);
					
					
					boolean validCondAll = lowP.compareTo(upP) < 0 && lowQ.compareTo(upQ) < 0;
					
					if(!validCondAll) {
						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
						showControl(txtcompGenPandQWarning);
					
						
						if(!(lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
							txtLowLimP.setBackground(wrong);
							txtUpperLimP.setBackground(wrong);
							txtLowLimQ.setBackground(wrong);
							txtUpperLimQ.setBackground(wrong);
						}
						if(!(lowP.compareTo(upP) < 0) && (lowQ.compareTo(upQ) < 0)) {
							txtLowLimP.setBackground(wrong);
							txtUpperLimP.setBackground(wrong);
							txtLowLimQ.setBackground(white);
							txtUpperLimQ.setBackground(white);
						}
						if((lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
							txtLowLimP.setBackground(white);
							txtUpperLimP.setBackground(white);
							txtLowLimQ.setBackground(wrong);
							txtUpperLimQ.setBackground(wrong);
						}
					}
					else {
						hideControl(txtcompGenPandQWarning);
						txtUpperLimP.setBackground(white);
						txtLowLimP.setBackground(white);
						txtLowLimQ.setBackground(white);
						txtUpperLimQ.setBackground(white);

						
						if(btnGenKeys.getSelection()) {
							btnStartGenKeys.setEnabled(true);
						}
					}

				}
	
				
			}
			
		}
	}




}
