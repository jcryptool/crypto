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
	

	/**
	 * @param scMain
	 * @param compMain
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public HandleFirstTab(ScrolledComposite scMain, Composite compMain, Rabin rabinFirst, Rabin rabinSecond) {
		super(scMain, compMain, rabinFirst, rabinSecond);
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
	 * method as a thread, can be used
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
				setStopComputation(false);
				
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
				}while(!(getRabinFirst().isAppropriatePrime(primeP, low, up)
							&& getRabinFirst().isAppropriatePrime(primeQ, low, up)
							&& !primeP.equals(primeQ)
							&& getRabinFirst().isCompositeSuitable(primeP, primeQ)
							) && iterations > 0 && !getStopComputation());
				
				if(getStopComputation()) {
					return;
				}
				
				
				if(iterations == 0) {
					
					Runnable r = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							rftc.getTxtSinglePQWarning().setText(strAppropriatePrimesWarning);
							showControl(rftc.getTxtSinglePQWarning());
							
						}
					};
					
					Display.getDefault().asyncExec(r);
					
					return;
					
				}
				
				Runnable r2 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						hideControl(rftc.getTxtSinglePQWarning());
						
					}
				};
					
				Display.getDefault().asyncExec(r2);
				
				String strPrimeP = primeP.toString();
				String strPrimeQ = primeQ.toString();
			
				BigInteger p = new BigInteger(strPrimeP);
				BigInteger q = new BigInteger(strPrimeQ);
				BigInteger n = p.multiply(q);
				getRabinFirst().setP(p);
				getRabinFirst().setQ(q);
				getRabinFirst().setN(n);
				
				
				
				//txtP.removeVerifyListener(vlNumbers);
				//txtQ.removeVerifyListener(vlNumbers);
				
				Runnable r3 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						rftc.getCmbP().setText(strPrimeP);
						rftc.getCmbQ().setText(strPrimeQ);
						
						
						//txtP.addVerifyListener(vlNumbers);
						//txtQ.addVerifyListener(vlNumbers);
						
						
						rftc.getTxtModN().setText(n.toString());
					}
				};				
				
				Display.getDefault().asyncExec(r3);
				
				
				int bitlength = getRabinFirst().getN().bitLength();
				int maxBytesPerBlock = bitlength / 8;
						
				int bytesPerBlock = (bitlength / 8) * 2;
				int blocklength = ((bitlength / 8) + 1) * 2;
				setBytesPerBlock(bytesPerBlock);
				setBlocklength(blocklength);
				
				rstc.getGuiHandler().setBlocklength(blocklength);
				rstc.getGuiHandler().setBytesPerBlock(2);
				
				Runnable r4 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						rstc.getCmbBlockN().removeAll();
						
						for(int i = 1; i <= maxBytesPerBlock; i++) {
							rstc.getCmbBlockN().add(String.valueOf(i));
						}
						
						rstc.getCmbBlockN().select(0);
						
						// to eliminate warnings
						rstc.getGuiHandler().handlePlaintextTextMode(rstc);
						rstc.getGuiHandler().handleDecimalNumbersEncDecMode(rstc);
						rstc.getGuiHandler().handleHexNumDecMode(rstc);
						rstc.getGuiHandler().handleDecimalNumbersDecMode(rstc);
						
						// for current mode
						if(rstc.getBtnSelectionEnc().getSelection()) {
							if(rstc.getBtnText().getSelection()) {
								rstc.getGuiHandler().handlePlaintextTextMode(rstc);
							}
							if(rstc.getBtnNum().getSelection()) {
								rstc.getGuiHandler().handleDecimalNumbersEncDecMode(rstc);
							}
						}
						
						if(rstc.getBtnSelectionDec().getSelection()) {
							if(rstc.getBtnRadHex().getSelection()) {
								rstc.getGuiHandler().handleHexNumDecMode(rstc);
							}
							if(rstc.getBtnRadDecimal().getSelection()) {
								rstc.getGuiHandler().handleDecimalNumbersDecMode(rstc);
							}
						}
						
						
						rftc.getTxtLowLimP().setBackground(ColorService.WHITE);
						rftc.getTxtLowLimQ().setBackground(ColorService.WHITE);
						rftc.getTxtUpperLimP().setBackground(ColorService.WHITE);
						rftc.getTxtUpperLimQ().setBackground(ColorService.WHITE);
						
						hideControl(rftc.getTxtcompGenPandQWarning());
				
					}
				};
				
				
				
				Display.getDefault().asyncExec(r4);
				
				
				//return;

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
		this.setStopComputation(false);
		
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
		}while(!(getRabinFirst().isAppropriatePrime(primeP, low, up)
					&& getRabinFirst().isAppropriatePrime(primeQ, low, up)
					&& !primeP.equals(primeQ)
					&& getRabinFirst().isCompositeSuitable(primeP, primeQ)
					) && iterations > 0 && !this.getStopComputation());
		
		if(this.getStopComputation()) {
			return false;
		}
		
		
		if(iterations == 0) {
			rftc.getTxtSinglePQWarning().setText(strAppropriatePrimesWarning);
			showControl(rftc.getTxtSinglePQWarning());
			return false;
		}
		
			
		hideControl(rftc.getTxtSinglePQWarning());
		
		String strPrimeP = primeP.toString();
		String strPrimeQ = primeQ.toString();
	
		BigInteger p = new BigInteger(strPrimeP);
		BigInteger q = new BigInteger(strPrimeQ);
		BigInteger n = p.multiply(q);
		getRabinFirst().setP(p);
		getRabinFirst().setQ(q);
		getRabinFirst().setN(n);
		
		
		
		//txtP.removeVerifyListener(vlNumbers);
		//txtQ.removeVerifyListener(vlNumbers);
		
		rftc.getCmbP().setText(strPrimeP);
		rftc.getCmbQ().setText(strPrimeQ);
		
		
		//txtP.addVerifyListener(vlNumbers);
		//txtQ.addVerifyListener(vlNumbers);
		
		
		rftc.getTxtModN().setText(n.toString());
		
		return true;
			
		

	}
	
	
	
	
	/*private Thread generateKeysWithLimitSingle(RabinFirstTabComposite rftc, String strLow, String strUp, int iterations) {
		
		//boolean success = false;
		Thread t = new Thread(new Runnable() {
			int iteration = iterations;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Display.getDefault().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
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
						setStopComputation(false);
						
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
						}while(!(getRabinFirst().isAppropriatePrime(primeP, low, up)
									&& getRabinFirst().isAppropriatePrime(primeQ, low, up)
									&& !primeP.equals(primeQ)
									&& getRabinFirst().isCompositeSuitable(primeP, primeQ)
									) && iterations > 0 && !getStopComputation());
						
						if(getStopComputation()) {
							//success = false;
							return;
						}
						
						
						if(iterations == 0) {
							rftc.getTxtSinglePQWarning().setText(strAppropriatePrimesWarning);
							showControl(rftc.getTxtSinglePQWarning());
							//success = false;
							return;
						}
						
							
						hideControl(rftc.getTxtSinglePQWarning());
						
						String strPrimeP = primeP.toString();
						String strPrimeQ = primeQ.toString();
					
						BigInteger p = new BigInteger(strPrimeP);
						BigInteger q = new BigInteger(strPrimeQ);
						BigInteger n = p.multiply(q);
						getRabinFirst().setP(p);
						getRabinFirst().setQ(q);
						getRabinFirst().setN(n);
						
						
						
						//txtP.removeVerifyListener(vlNumbers);
						//txtQ.removeVerifyListener(vlNumbers);
						
						rftc.getCmbP().setText(strPrimeP);
						rftc.getCmbQ().setText(strPrimeQ);
						
						
						//txtP.addVerifyListener(vlNumbers);
						//txtQ.addVerifyListener(vlNumbers);
						
						
						rftc.getTxtModN().setText(n.toString());
						
						//success = true;
					}
				});
					
			}
		});
		
		
		//return success;
		
		return t;
		

	}*/
	
	
	
	
	
	
	
	
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
			
		}while(!(getRabinFirst().isAppropriatePrime(primeP, lowP, upP)
					&& getRabinFirst().isAppropriatePrime(primeQ, lowQ, upQ)
					&& !primeP.equals(primeQ)
					&& getRabinFirst().isCompositeSuitable(primeP, primeQ)
					) && iterations > 0);
		
		
		if(iterations == 0) {
			rftc.getTxtcompGenPandQWarning().setText(strAppropriatePrimesWarning);
			showControl(rftc.getTxtcompGenPandQWarning());
			return false;
		}
		
			
		hideControl(rftc.getTxtcompGenPandQWarning());
		
		String strPrimeP = primeP.toString();
		String strPrimeQ = primeQ.toString();
		//System.out.println("p = " + strPrimeP);
		//System.out.println("q = " + strPrimeQ);
		BigInteger p = new BigInteger(strPrimeP);
		BigInteger q = new BigInteger(strPrimeQ);
		BigInteger n = p.multiply(q);
		getRabinFirst().setP(p);
		getRabinFirst().setQ(q);
		getRabinFirst().setN(n);
		//System.out.println("N = " + n.toString());
		
		
		//txtP.removeVerifyListener(vlNumbers);
		//txtQ.removeVerifyListener(vlNumbers);
		
		rftc.getCmbP().setText(strPrimeP);
		rftc.getCmbQ().setText(strPrimeQ);
		
		
		//txtP.addVerifyListener(vlNumbers);
		//txtQ.addVerifyListener(vlNumbers);
		
		
		rftc.getTxtModN().setText(n.toString());
		
		return true;
			
		

	}
	
	
	
	
	/**
	 * action for txtModN
	 * @param txtModN
	 * @param btnEncDecStart
	 */
	public void updateEncryptButton(Text txtModN, Button btnEncrypt, TextLoadController textSelector) {
		
		String n = txtModN.getText();
		String plaintextToEncrypt = textSelector.getText().getText();
		
		boolean check = n.isEmpty() || plaintextToEncrypt.isEmpty();
		
		if(check) {
			btnEncrypt.setEnabled(false);
			return;
		}
		
		btnEncrypt.setEnabled(true);
		
	}
	
	
	
	/**
	 * action for btnGenKeysMan
	 * @param rftc
	 * @param e
	 */
	public void btnGenKeysManAction(RabinFirstTabComposite rftc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			hideControl(rftc.getCompHoldSelectionPrimesAndLimits());
			String info = this.getMessageByControl("btnGenKeysMan_selection");
			rftc.getTxtInfoSetParam().setText(info);
			//this.updateTextfields(rftc.getCmbP(), rftc.getCmbQ(), rftc.getBtnGenKeysMan(), rftc.getBtnStartGenKeys(), rftc.getTxtWarningNpq());
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
			
			this.showControl(rftc.getCompHoldSelectionPrimesAndLimits());
			String info = this.getMessageByControl("btnGenKeys_selection");
			rftc.getTxtInfoSetParam().setText(info);
		
			
			if(rftc.getBtnSelectSingleLimit().getSelection()) {
				this.updateLimitFieldsSingle(rftc);
			}
			
			if(rftc.getBtnSelectMultiPandQ().getSelection()) {
				this.updateLimitFields(rftc.getTxtLowLimP(), rftc.getTxtUpperLimP(), rftc.getTxtLowLimQ(), rftc.getTxtUpperLimQ(), rftc.getTxtcompGenPandQWarning(), rftc.getBtnGenKeys(), rftc.getBtnStartGenKeys());
				
			}	
		}
	}
	
	
	
	
	/**
	 * action for btnUseKeysAlgo
	 * @param rftc
	 * @param e
	 */
	public void btnUseKeysAlgoAction(RabinFirstTabComposite rftc, SelectionEvent e) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection()) {
			rftc.getBtnStartGenKeys().setEnabled(true);
		}
	}
	
	
	

	
	/**
	 * action for btnStartGenKeys
	 * @param rftc
	 * @param iterations
	 */
	/*public void btnStartGenKeysAction(RabinFirstTabComposite rftc, int iterations) {
		if(rftc.getBtnGenKeysMan().getSelection()) {
			this.btnGenKeysManAction(rftc.getTxtP(), rftc.getTxtQ(), rftc.getTxtModN());
		}
		
		if(rftc.getBtnGenKeys().getSelection()) {
			
			if(rftc.getBtnSelectSingleLimit().getSelection()) {
				String strLow = rftc.getTxtLowLimPQSingle().getText();
				String strUp = rftc.getTxtUpperLimPQSingle().getText();
				
				boolean success = this.generateKeysWithLimitSingle(rftc, strLow, strUp, iterations);
				if(!success)
					return;
			}
			
			if(rftc.getBtnSelectMultiPandQ().getSelection()) {
				String strLowP = rftc.getTxtLowLimP().getText();
				String strUpP = rftc.getTxtUpperLimP().getText();
				String strLowQ = rftc.getTxtLowLimQ().getText();
				String strUpQ = rftc.getTxtUpperLimQ().getText();
				
				boolean success = this.generateKeysWithLimit(rftc, strLowP, strUpP, strLowQ, strUpQ, iterations);
				if(!success)
					return;
			}
			
		}
		
	
		
		//guiHandler.btnStartGenKeysAction(txtLowLimP, txtLowLimQ, txtUpperLimP, txtUpperLimQ, txtcompGenPandQWarning);
		
		int bytesPerBlock = (getRabinFirst().getN().bitLength() / 8) * 2;
		int blocklength = ((getRabinFirst().getN().bitLength() / 8) + 1) * 2;
		this.setBytesPerBlock(bytesPerBlock);
		this.setBlocklength(blocklength);
		
		rftc.getTxtLowLimP().setBackground(ColorService.WHITE);
		rftc.getTxtLowLimQ().setBackground(ColorService.WHITE);
		rftc.getTxtUpperLimP().setBackground(ColorService.WHITE);
		rftc.getTxtUpperLimQ().setBackground(ColorService.WHITE);
		
		hideControl(rftc.getTxtcompGenPandQWarning());
	}*/
	
	
	
	public void initializePrimes(int numOfPrimes, RabinFirstTabComposite rftc) {
		
		for(int i = 0, count = 0; count < numOfPrimes; i++) {
			BigInteger possiblePrime = BigInteger.valueOf(i);
			if(this.getRabinFirst().isSuitablePrime(possiblePrime)) {
				rftc.getCmbP().add(possiblePrime.toString());
				rftc.getCmbQ().add(possiblePrime.toString());
				count++;
			}
		}
	}
	
	
	
	
	
	public void cmbSelectionAction(Combo cmbP) {
		int idx = cmbP.getSelectionIndex();
		String item = cmbP.getItem(idx);
		
		//cmbP.removeVerifyListener(vlNumbers);
		cmbP.setText(item);
		//cmbP.addVerifyListener(vlNumbers);
	}
	
	
	
	public void cmbQSelectionAction(Combo cmbQ) {
		int idx = cmbQ.getSelectionIndex();
		String item = cmbQ.getItem(idx);
		
		cmbQ.setText(item);
	}
	
	
	
	
	
	
	public void btnStartGenKeysAction(RabinFirstTabComposite rftc, RabinSecondTabComposite rstc, int iterations) {
		//Thread t = null;
		
		if(rftc.getBtnGenKeysMan().getSelection()) {
			this.btnGenKeysManAction(rftc.getCmbP(), rftc.getCmbQ(), rftc.getTxtModN());
		}
		
		if(rftc.getBtnGenKeys().getSelection()) {
			
			if(rftc.getBtnSelectSingleLimit().getSelection()) {
				
				String strLow = rftc.getTxtLowLimPQSingle().getText();
				String strUp = rftc.getTxtUpperLimPQSingle().getText();
			
				//generateKeysWithLimitSingle(rftc, strLow, strUp, iterations).start();
				//return;
				
				boolean success = generateKeysWithLimitSingle(rftc, strLow, strUp, iterations);
				
				if(!success)
					return;
				
			}
			
			if(rftc.getBtnSelectMultiPandQ().getSelection()) {
				String strLowP = rftc.getTxtLowLimP().getText();
				String strUpP = rftc.getTxtUpperLimP().getText();
				String strLowQ = rftc.getTxtLowLimQ().getText();
				String strUpQ = rftc.getTxtUpperLimQ().getText();
				
				boolean success = this.generateKeysWithLimit(rftc, strLowP, strUpP, strLowQ, strUpQ, iterations);
				if(!success)
					return;
			}
			
		}
		
		/*if(rftc.getBtnUseKeysAlgo().getSelection()) {
			BigInteger n = getRabinSecond().getN();
			if(n == null) {
				String strGenKeyPairFT = Messages.HandleFirstTab_strGenKeyPairFT;
				//rftc.getTxtcompGenPandQWarning().setText(strGenKeyPairFT);
				//showControl(rftc.getTxtcompGenPandQWarning());
				rftc.getNWarning().setText(strGenKeyPairFT);
				showControl(rftc.getNWarning());
				return;
			}
			//hideControl(rftc.getTxtcompGenPandQWarning());
			hideControl(rftc.getNWarning());
			
			
			getRabinFirst().setP(getRabinSecond().getP());
			getRabinFirst().setQ(getRabinSecond().getQ());
			getRabinFirst().setN(getRabinSecond().getN());
			
			//rftc.getTxtP().removeVerifyListener(vlNumbers);
			//rftc.getTxtQ().removeVerifyListener(vlNumbers);
			rftc.getTxtP().setText(getRabinFirst().getP().toString());
			rftc.getTxtQ().setText(getRabinFirst().getQ().toString());
			//rftc.getTxtP().addVerifyListener(vlNumbers);
			//rftc.getTxtQ().addVerifyListener(vlNumbers);
			rftc.getTxtModN().setText(getRabinFirst().getN().toString());
			
			//guiHandler.btnUseKeysAlgoAction(txtP, txtQ, txtModN, vlNumbers, txtcompGenPandQWarning);
		}*/
		
		//guiHandler.btnStartGenKeysAction(txtLowLimP, txtLowLimQ, txtUpperLimP, txtUpperLimQ, txtcompGenPandQWarning);
		
		
	
		
		int bitlength = getRabinFirst().getN().bitLength();
		int maxBytesPerBlock = bitlength / 8;
				
		int bytesPerBlock = (bitlength / 8) * 2;
		int blocklength = ((bitlength / 8) + 1) * 2;
		this.setBytesPerBlock(bytesPerBlock);
		this.setBlocklength(blocklength);
		
		rstc.getGuiHandler().setBlocklength(blocklength);
		rstc.getGuiHandler().setBytesPerBlock(2);
		
		
		rstc.getCmbBlockN().removeAll();
		
		for(int i = 1; i <= maxBytesPerBlock; i++) {
			rstc.getCmbBlockN().add(String.valueOf(i));
		}
		
		rstc.getCmbBlockN().select(0);
		
		// to eliminate warnings
		rstc.getGuiHandler().handlePlaintextTextMode(rstc);
		rstc.getGuiHandler().handleDecimalNumbersEncDecMode(rstc);
		rstc.getGuiHandler().handleHexNumDecMode(rstc);
		rstc.getGuiHandler().handleDecimalNumbersDecMode(rstc);
		
		// for current mode
		if(rstc.getBtnSelectionEnc().getSelection()) {
			if(rstc.getBtnText().getSelection()) {
				rstc.getGuiHandler().handlePlaintextTextMode(rstc);
			}
			if(rstc.getBtnNum().getSelection()) {
				rstc.getGuiHandler().handleDecimalNumbersEncDecMode(rstc);
			}
		}
		
		if(rstc.getBtnSelectionDec().getSelection()) {
			if(rstc.getBtnRadHex().getSelection()) {
				rstc.getGuiHandler().handleHexNumDecMode(rstc);
			}
			if(rstc.getBtnRadDecimal().getSelection()) {
				rstc.getGuiHandler().handleDecimalNumbersDecMode(rstc);
			}
		}
		
		
		rftc.getTxtLowLimP().setBackground(ColorService.WHITE);
		rftc.getTxtLowLimQ().setBackground(ColorService.WHITE);
		rftc.getTxtUpperLimP().setBackground(ColorService.WHITE);
		rftc.getTxtUpperLimQ().setBackground(ColorService.WHITE);
		
		hideControl(rftc.getTxtcompGenPandQWarning());
	}
	
	
	
	
	
	
	/**
	 * action for btnEncDecStart
	 * @param textSelector
	 * @param txtEnc
	 * @param txtEncDecStartWarning
	 * @param txtChosenPlain
	 * @param plaintexts
	 * @param btnRadEnc
	 * @param btnRadDec
	 */
	public void btnEncDecStartAction(TextLoadController textSelector, Text txtEnc, Text txtEncDecStartWarning, Text txtChosenPlain, Text[][] plaintexts, Button btnRadEnc, Button btnRadDec) {
		if(textSelector.getText() == null || textSelector.getText().getText().isEmpty()) {
			String strLoadTextWarning = Messages.HandleFirstTab_strLoadTextWarning;
			txtEncDecStartWarning.setText(strLoadTextWarning);
			showControl(txtEncDecStartWarning);
			return;
		}

		hideControl(txtEncDecStartWarning);
		
		if(btnRadEnc.getSelection()) {
		
		
			String plaintext = textSelector.getText().getText();
			String ciphertext = getRabinFirst().encryptPlaintext(plaintext, getBytesPerBlock(), getBlocklength(), getRadix());
			
			
			txtEnc.setText(ciphertext);
			
		}
		
		if(btnRadDec.getSelection()) {
			
			//int x = 4;
			//int y = 5;
			String pattern = "[0-9a-fA-F]+"; //$NON-NLS-1$
			String ciphertext = textSelector.getText().getText();
			ciphertext = ciphertext.replaceAll("\\s+", ""); //$NON-NLS-1$ //$NON-NLS-2$
			
			if(!ciphertext.matches(pattern)) {
				String strOnlyHexAllowed = Messages.HandleFirstTab_strOnlyHexAllowed;
				txtEncDecStartWarning.setText(strOnlyHexAllowed);
				showControl(txtEncDecStartWarning);
				return;
			}
			
			boolean checkLength = ciphertext.length() % getBlocklength() == 0;
			
			if(!checkLength) {
				String strLengthCipher = Messages.HandleFirstTab_strLengthCipher;
				txtEncDecStartWarning.setText(MessageFormat.format(
						strLengthCipher,
						(getBlocklength() / 2)));
				showControl(txtEncDecStartWarning);
				return;
			}
			
			hideControl(txtEncDecStartWarning);
			txtChosenPlain.setText(""); //$NON-NLS-1$
			
			for(int i = 0; i < plaintexts.length; i++) {
				for(int j = 0; j < plaintexts[i].length; j++) {
					plaintexts[i][j].setText(""); //$NON-NLS-1$
				}
			}
			
			ArrayList<String> ciphertextList = getRabinFirst().parseString(ciphertext, getBlocklength());
			
			int lengthToWork = 0;
			
			if(ciphertextList.size() > 5) {
				lengthToWork = 5;
			}
			else
				lengthToWork = ciphertextList.size();
			
			
			for(int i = 0; i < lengthToWork; i++) {
				String currentCiphertext = ciphertextList.get(i);
				BigInteger currCiphertextAsNum = new BigInteger(currentCiphertext, getRadix());
				ArrayList<String> possiblePlaintexts = getRabinFirst().getPossiblePlaintextsEncoded(currCiphertextAsNum);
				for(int j = 0; j < plaintexts.length; j++) {
					plaintexts[j][i].setText(possiblePlaintexts.get(j));
				}
			}
		}
	}
	
	
	
	
	/**
	 * action for tableMouseDown
	 * @param e
	 * @param txtChosenPlain
	 * @param txtChosenPlainInfo
	 * @param plaintexts
	 */
	public void tableMouseDownAction(MouseEvent e, Text txtChosenPlain, Text txtChosenPlainInfo, Text[][] plaintexts) {
		Text src = (Text) e.getSource();
		
		src.setBackground(ColorService.LIGHT_AREA_BLUE);
		
		String plaintext = src.getText();
		txtChosenPlain.setText(plaintext);
		
		for(int i = 0; i < plaintexts.length; i++) {
			for(int j = 0; j < plaintexts[i].length; j++) {
				if(!plaintexts[i][j].equals(src)) {
					plaintexts[i][j].setBackground(ColorService.WHITE);
				}
				else
					txtChosenPlainInfo.setText("c[" + (i+1) + "]" + "[" + (j+1) + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		}

	}
	
	
	
	
	/**
	 * action for tableMouseEnter
	 * @param e
	 */
	public void tableMouseEnterAction(MouseEvent e) {
		Text src = (Text) e.getSource();
		src.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
	}
	
	
	
	
	/**
	 * action for btnWriteToEditor
	 * @param btnRadEnc
	 * @param btnRadDec
	 * @param txtEnc
	 * @param txtChosenPlain
	 * @throws PartInitException
	 */
	public void btnWriteToEditorAction(Button btnRadEnc, Button btnRadDec, Text txtEnc, Text txtChosenPlain) throws PartInitException {
		if(btnRadEnc.getSelection()) {
			String ciphertext = txtEnc.getText();
			int len = ciphertext.length();
			String ciphertextForFile= ""; //$NON-NLS-1$
			int limit = 100;
			
			if(len < 100) {
				ciphertextForFile = ciphertext;
			}
			else {
			
				for(int i = 0; i < len; i++) {
					ciphertextForFile += ciphertext.charAt(i);
					if((i+1) % limit == 0)
						ciphertextForFile += "\n"; //$NON-NLS-1$
				}
			}
			
			
			IEditorInput customEditorInput = AbstractEditorService.createOutputFile(ciphertextForFile);
			EditorsManager.getInstance().openNewTextEditor(customEditorInput);
		
		}
		
		if(btnRadDec.getSelection()) {
			String plaintext = txtChosenPlain.getText();
			IEditorInput customEditorInput = AbstractEditorService.createOutputFile(plaintext);
			EditorsManager.getInstance().openNewTextEditor(customEditorInput);
			
		}
	}
	
	
	
	
	
	
	/*public void updateLimitFieldsSingle(RabinFirstTabComposite rftc) {
		String strLowLim = rftc.getTxtLowLimPQSingle().getText();
		String strUpperLim = rftc.getTxtUpperLimPQSingle().getText();
		String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		
		Color white = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
		if(rftc.getBtnGenKeys().getSelection()) 
			rftc.getBtnStartGenKeys().setEnabled(false);
		
		BigInteger lowPQ = null;
		BigInteger upPQ = null;
		
		if(strLowLim.isEmpty() && strUpperLim.isEmpty()) {
			this.hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
			rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
			return;
		}
		
		if(strLowLim.isEmpty() && !strUpperLim.isEmpty()) {
			if(!strUpperLim.matches(pattern)) {
				rftc.getTxtSinglePQWarning().setText(strOnlyDecAllowed);
				this.showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(ColorService.RED);
				return;
			}
			
			hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
			rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
			
			upPQ = getNumFromLimit(strUpperLim);
			if(upPQ.compareTo(getLimitUp()) > 0) {
				rftc.getTxtSinglePQWarning().setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
				showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(ColorService.RED);
			}
			else {
				hideControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
			}
			rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
			return;
		}
		
		
		if(!strLowLim.isEmpty() && strUpperLim.isEmpty()) {
			if(!strLowLim.matches(pattern)) {
				rftc.getTxtSinglePQWarning().setText(strOnlyDecAllowed);
				this.showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtLowLimPQSingle().setBackground(ColorService.RED);
				return;
			}
			
			hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
			rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
			
			
			return;
		}
		
		
		if(!strLowLim.isEmpty() && !strUpperLim.isEmpty()) {
			boolean checkPattern = strLowLim.matches(pattern) && strUpperLim.matches(pattern);
			
			if(!checkPattern) {
				rftc.getTxtSinglePQWarning().setText(strOnlyDecAllowed);
				this.showControl(rftc.getTxtSinglePQWarning());
					
				if(strLowLim.matches(pattern) && !strUpperLim.matches(pattern)) {
					rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
					rftc.getTxtUpperLimPQSingle().setBackground(ColorService.RED);
				}
				
				if(!strLowLim.matches(pattern) && strUpperLim.matches(pattern)) {
					rftc.getTxtLowLimPQSingle().setBackground(ColorService.RED);
					rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
				}
				
				if(!strLowLim.matches(pattern) && !strUpperLim.matches(pattern)) {
					rftc.getTxtLowLimPQSingle().setBackground(ColorService.RED);
					rftc.getTxtUpperLimPQSingle().setBackground(ColorService.RED);
				}
				return;
			}
			
			this.hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
			
			
			upPQ = getNumFromLimit(strUpperLim);
			lowPQ = getNumFromLimit(strLowLim);
			
			if(upPQ.compareTo(getLimitUp()) > 0) {
				rftc.getTxtSinglePQWarning().setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
				showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(ColorService.RED);
			}
			else {
				hideControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
				
				if(lowPQ.compareTo(upPQ) >= 0) {
					rftc.getTxtSinglePQWarning().setText(strLowerLimitLessUpperLimit);
					showControl(rftc.getTxtSinglePQWarning());
					rftc.getTxtLowLimPQSingle().setBackground(ColorService.RED);
					rftc.getTxtUpperLimPQSingle().setBackground(ColorService.RED);
				}
				else {
					hideControl(rftc.getTxtSinglePQWarning());
					rftc.getTxtLowLimPQSingle().setBackground(ColorService.WHITE);
					rftc.getTxtUpperLimPQSingle().setBackground(ColorService.WHITE);
					
					if(rftc.getBtnGenKeys().getSelection()) 
						rftc.getBtnStartGenKeys().setEnabled(true);
				}
				
				
			}
		
		}
		
	}*/
	
	
	
	/**
	 * update limit fields (single mode) each time you enter a char
	 * @param rftc
	 */
	public void updateLimitFieldsSingle(RabinFirstTabComposite rftc) {
		String strLowLim = rftc.getTxtLowLimPQSingle().getText();
		String strUpperLim = rftc.getTxtUpperLimPQSingle().getText();
		String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		
		Color white = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
		if(rftc.getBtnGenKeys().getSelection()) 
			rftc.getBtnStartGenKeys().setEnabled(false);
		
		BigInteger lowPQ = null;
		BigInteger upPQ = null;
		
		if(strLowLim.isEmpty() && strUpperLim.isEmpty()) {
			this.hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtLowLimPQSingle().setBackground(white);
			rftc.getTxtUpperLimPQSingle().setBackground(white);
			return;
		}
		
		if(strLowLim.isEmpty() && !strUpperLim.isEmpty()) {
			if(!strUpperLim.matches(pattern)) {
				// testing and debugging, remove this line when done
				//this.setSizeControlTest(rftc.getTxtSinglePQWarning(), SWT.DEFAULT, SWT.DEFAULT);
				
				
				rftc.getTxtSinglePQWarning().setText(strOnlyDecAllowed);
				
				
				
				
				this.showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(wrong);
				return;
			}
			
			hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtUpperLimPQSingle().setBackground(white);
			rftc.getTxtLowLimPQSingle().setBackground(white);
			
			upPQ = getNumFromLimit(strUpperLim);
			if(upPQ.compareTo(getLimitUp()) > 0) {
				rftc.getTxtSinglePQWarning().setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
				showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(wrong);
			}
			else {
				hideControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(white);
			}
			rftc.getTxtLowLimPQSingle().setBackground(white);
			return;
		}
		
		
		if(!strLowLim.isEmpty() && strUpperLim.isEmpty()) {
			if(!strLowLim.matches(pattern)) {
				rftc.getTxtSinglePQWarning().setText(strOnlyDecAllowed);
				this.showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtLowLimPQSingle().setBackground(wrong);
				return;
			}
			
			hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtLowLimPQSingle().setBackground(white);
			rftc.getTxtUpperLimPQSingle().setBackground(white);
			
			
			return;
		}
		
		
		if(!strLowLim.isEmpty() && !strUpperLim.isEmpty()) {
			boolean checkPattern = strLowLim.matches(pattern) && strUpperLim.matches(pattern);
			
			if(!checkPattern) {
				rftc.getTxtSinglePQWarning().setText(strOnlyDecAllowed);
				this.showControl(rftc.getTxtSinglePQWarning());
					
				if(strLowLim.matches(pattern) && !strUpperLim.matches(pattern)) {
					rftc.getTxtLowLimPQSingle().setBackground(white);
					rftc.getTxtUpperLimPQSingle().setBackground(wrong);
				}
				
				if(!strLowLim.matches(pattern) && strUpperLim.matches(pattern)) {
					rftc.getTxtLowLimPQSingle().setBackground(wrong);
					rftc.getTxtUpperLimPQSingle().setBackground(white);
				}
				
				if(!strLowLim.matches(pattern) && !strUpperLim.matches(pattern)) {
					rftc.getTxtLowLimPQSingle().setBackground(wrong);
					rftc.getTxtUpperLimPQSingle().setBackground(wrong);
				}
				return;
			}
			
			this.hideControl(rftc.getTxtSinglePQWarning());
			rftc.getTxtLowLimPQSingle().setBackground(white);
			
			
			upPQ = getNumFromLimit(strUpperLim);
			lowPQ = getNumFromLimit(strLowLim);
			
			if(upPQ.compareTo(getLimitUp()) > 0) {
				rftc.getTxtSinglePQWarning().setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
				showControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(wrong);
			}
			else {
				hideControl(rftc.getTxtSinglePQWarning());
				rftc.getTxtUpperLimPQSingle().setBackground(white);
				
				if(lowPQ.compareTo(upPQ) >= 0) {
					rftc.getTxtSinglePQWarning().setText(strLowerLimitLessUpperLimit);
					showControl(rftc.getTxtSinglePQWarning());
					rftc.getTxtLowLimPQSingle().setBackground(wrong);
					rftc.getTxtUpperLimPQSingle().setBackground(wrong);
				}
				else {
					hideControl(rftc.getTxtSinglePQWarning());
					rftc.getTxtLowLimPQSingle().setBackground(white);
					rftc.getTxtUpperLimPQSingle().setBackground(white);
					
					if(rftc.getBtnGenKeys().getSelection()) 
						rftc.getBtnStartGenKeys().setEnabled(true);
				}
		
			}
		}
		
	}
	
	
	
	
//	public void updateLimitFields(Text txtLowLimP, Text txtUpperLimP, Text txtLowLimQ, Text txtUpperLimQ, Text txtcompGenPandQWarning, Button btnGenKeys, Button btnStartGenKeys) {
//		String txtLowP = txtLowLimP.getText();
//		String txtUpP = txtUpperLimP.getText();
//		String txtLowQ = txtLowLimQ.getText();
//		String txtUpQ = txtUpperLimQ.getText();
//		String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
//		
//		Color white = this.getColorBackgroundNeutral();
//		Color wrong = this.getColorBackgroundWrong();
//		
//		if(btnGenKeys.getSelection()) 
//			btnStartGenKeys.setEnabled(false);
//		
//		BigInteger lowP = null;
//		BigInteger upP = null;
//		BigInteger lowQ = null;
//		BigInteger upQ = null;
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			hideControl(txtcompGenPandQWarning);
//			txtLowLimP.setBackground(white);
//			txtUpperLimP.setBackground(white);
//			txtLowLimQ.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			return;
//		}
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			if(!txtUpQ.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtUpperLimQ.setBackground(wrong);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimQ.setBackground(white);
//			}
//			
//			txtUpperLimP.setBackground(white);
//			txtLowLimP.setBackground(white);
//			txtLowLimQ.setBackground(white);
//			return;
//		}
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			if(!txtLowQ.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(wrong);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(white);
//			}
//			
//			txtUpperLimP.setBackground(white);
//			txtLowLimP.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			
//			return;
//		}
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(white);
//				}
//				if(txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(white);
//				txtUpperLimQ.setBackground(white);
//				
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				
//					
//				if(lowQ.compareTo(upQ) >= 0) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(white);
//				}
//				
//				
//				
//			}
//			txtLowLimP.setBackground(white);
//			txtUpperLimP.setBackground(white);
//			
//			return;
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			if(!txtUpP.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(wrong);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(white);
//			}
//			
//			txtLowLimP.setBackground(white);
//			txtLowLimQ.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			
//			return;
//			
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtUpQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(white);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimQ.setBackground(white);
//				txtUpperLimP.setBackground(white);
//			}
//			txtLowLimP.setBackground(white);
//			txtLowLimQ.setBackground(white);
//			
//			return;
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(white);
//					txtLowLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(white);
//				txtUpperLimP.setBackground(white);
//			}
//			txtLowLimP.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			
//			return;
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			
//			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//					&& txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(white);
//				}
//				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(white);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(white);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(white);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(white);
//				txtLowLimQ.setBackground(white);
//				txtUpperLimQ.setBackground(white);
//				
//				
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//						
//				if(lowQ.compareTo(upQ) >= 0) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//					txtUpperLimP.setBackground(white);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(white);
//					txtUpperLimP.setBackground(white);
//				}
//				
//			}
//			txtLowLimP.setBackground(white);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			if(!txtLowP.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(wrong);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(white);
//			}
//			
//			txtUpperLimP.setBackground(white);
//			txtLowLimQ.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			
//			return;
//
//		}
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtUpQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtLowP.matches(pattern) && txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimQ.setBackground(white);
//				}
//				if(txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(white);
//					txtUpperLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(white);
//				txtUpperLimQ.setBackground(white);
//				
//			}
//			txtUpperLimP.setBackground(white);
//			txtLowLimQ.setBackground(white);
//			
//			return;
//
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//				}
//				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(white);
//				}
//				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtLowLimP.setBackground(white);
//					txtLowLimQ.setBackground(wrong);
//				}
//				
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(white);
//				txtLowLimQ.setBackground(white);
//			}
//			txtUpperLimP.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			
//			return;
//
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//					&& txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//					&& !txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(white);
//				txtLowLimQ.setBackground(white);
//				txtUpperLimQ.setBackground(white);
//
//				
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				
//					
//				if(lowQ.compareTo(upQ) >= 0) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//					txtLowLimP.setBackground(white);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(white);
//					txtLowLimP.setBackground(white);
//				}
//				
//			}
//			txtUpperLimP.setBackground(white);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtUpP.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimP.setBackground(wrong);
//				}
//				if(!txtLowP.matches(pattern) && txtUpP.matches(pattern)) {
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimP.setBackground(white);
//				}
//				if(txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
//					txtLowLimP.setBackground(white);
//					txtUpperLimP.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(white);
//				txtUpperLimP.setBackground(white);
//	
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				
//				if(lowP.compareTo(upP) >= 0) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimP.setBackground(wrong);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtLowLimP.setBackground(white);
//					txtUpperLimP.setBackground(white);
//				}
//			}
//			txtLowLimQ.setBackground(white);
//			txtUpperLimQ.setBackground(white);
//			
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
//					&& txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//					&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(white);
//				txtLowLimP.setBackground(white);
//				txtUpperLimQ.setBackground(white);
//
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				
//				if(lowP.compareTo(upP) >= 0) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimP.setBackground(wrong);
//					txtUpperLimQ.setBackground(white);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtLowLimP.setBackground(white);
//					txtUpperLimP.setBackground(white);
//					txtUpperLimQ.setBackground(white);
//				}
//			
//			}
//			txtLowLimQ.setBackground(white);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
//					&& txtLowQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//					&& !txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(wrong);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(white);
//				txtLowLimP.setBackground(white);
//				txtLowLimQ.setBackground(white);
//
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				
//				if(lowP.compareTo(upP) >= 0) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//					txtLowLimP.setBackground(wrong);
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(white);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtLowLimP.setBackground(white);
//					txtUpperLimP.setBackground(white);
//					txtLowLimQ.setBackground(white);
//				}
//				
//			}
//			txtUpperLimQ.setBackground(white);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			
//			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
//					&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//					&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(wrong);
//					txtLowLimP.setBackground(wrong);
//					txtLowLimQ.setBackground(wrong);
//					txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(white);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(white);
//						txtLowLimP.setBackground(white);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(wrong);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(white);
//				txtLowLimP.setBackground(white);
//				txtLowLimQ.setBackground(white);
//				txtUpperLimQ.setBackground(white);
//
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				
//				boolean validCondAll = lowP.compareTo(upP) < 0 && lowQ.compareTo(upQ) < 0;
//				
//				if(!validCondAll) {
//					txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//					showControl(txtcompGenPandQWarning);
//				
//					
//					if(!(lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
//						txtLowLimP.setBackground(wrong);
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//					}
//					if(!(lowP.compareTo(upP) < 0) && (lowQ.compareTo(upQ) < 0)) {
//						txtLowLimP.setBackground(wrong);
//						txtUpperLimP.setBackground(wrong);
//						txtLowLimQ.setBackground(white);
//						txtUpperLimQ.setBackground(white);
//					}
//					if((lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
//						txtLowLimP.setBackground(white);
//						txtUpperLimP.setBackground(white);
//						txtLowLimQ.setBackground(wrong);
//						txtUpperLimQ.setBackground(wrong);
//					}
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(white);
//					txtLowLimP.setBackground(white);
//					txtLowLimQ.setBackground(white);
//					txtUpperLimQ.setBackground(white);
//
//					
//					if(btnGenKeys.getSelection()) {
//						btnStartGenKeys.setEnabled(true);
//					}
//				}
//
//			}
//			
//		}
//	}

	
	
	
	
	
	
	
	
//	public void updateLimitFields(Text txtLowLimP, Text txtUpperLimP, Text txtLowLimQ, Text txtUpperLimQ, Text txtcompGenPandQWarning, Button btnGenKeys, Button btnStartGenKeys) {
//		String txtLowP = txtLowLimP.getText();
//		String txtUpP = txtUpperLimP.getText();
//		String txtLowQ = txtLowLimQ.getText();
//		String txtUpQ = txtUpperLimQ.getText();
//		String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
//		
//		Color white = this.getColorBackgroundNeutral();
//		Color wrong = this.getColorBackgroundWrong();
//		
//		if(btnGenKeys.getSelection()) 
//			btnStartGenKeys.setEnabled(false);
//		
//		BigInteger lowP = null;
//		BigInteger upP = null;
//		BigInteger lowQ = null;
//		BigInteger upQ = null;
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			hideControl(txtcompGenPandQWarning);
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			return;
//		}
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			if(!txtUpQ.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtUpperLimQ.setBackground(ColorService.RED);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//				
//				upQ = getNumFromLimit(txtUpQ);
//				if(upQ.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//			}
//			
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			return;
//		}
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			if(!txtLowQ.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(ColorService.RED);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//			}
//			
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//		}
//		
//		if(txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimQ.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//				
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				
//				if(upQ.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//					
//					if(lowQ.compareTo(upQ) >= 0) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//				}
//				
//				
//			}
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			
//			return;
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			if(!txtUpP.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(ColorService.RED);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				
//				upP = getNumFromLimit(txtUpP);
//				if(upP.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.RED);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//				}
//			}
//			
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//			
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtUpQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				
//				upP = getNumFromLimit(txtUpP);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
//				
//				if(!checkLimit) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//
//					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//			}
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtLowLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				
//				upP = getNumFromLimit(txtUpP);
//				if(upP.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.RED);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);	
//				}
//			}
//			txtLowLimP.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//		}
//		
//		
//		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			
//			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//					&& txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtLowLimQ.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//				
//				
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				upP =getNumFromLimit(txtUpP);
//				
//				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
//				
//				if(!checkLimit) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//
//					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//					
//					if(lowQ.compareTo(upQ) >= 0) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//						txtUpperLimP.setBackground(ColorService.WHITE);
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimP.setBackground(ColorService.WHITE);
//					}
//				}	
//			}
//			txtLowLimP.setBackground(ColorService.WHITE);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			if(!txtLowP.matches(pattern)) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(ColorService.RED);
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(ColorService.WHITE);
//			}
//			
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//
//		}
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtUpQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtLowP.matches(pattern) && txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//				
//				upQ = getNumFromLimit(txtUpQ);
//				
//				if(upQ.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				else {
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//					hideControl(txtcompGenPandQWarning);
//				}
//			}
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern);
//
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.WHITE);
//					txtLowLimQ.setBackground(ColorService.RED);
//				}
//				
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//			}
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//					&& txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//					&& !txtUpQ.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtLowP.matches(pattern) && txtLowQ.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//
//				
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				
//				if(upQ.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				else {
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//					hideControl(txtcompGenPandQWarning);
//					
//					if(lowQ.compareTo(upQ) >= 0) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//					}
//				}
//				
//				
//			}
//			txtUpperLimP.setBackground(ColorService.WHITE);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtLowP.matches(pattern) && txtUpP.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtUpperLimP.setBackground(ColorService.RED);
//				}
//				if(!txtLowP.matches(pattern) && txtUpP.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.RED);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//				}
//				if(txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
//					txtLowLimP.setBackground(ColorService.WHITE);
//					txtUpperLimP.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//	
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				
//				if(upP.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.RED);
//				}
//				else {
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					hideControl(txtcompGenPandQWarning);
//					
//					if(lowP.compareTo(upP) >= 0) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtUpperLimP.setBackground(ColorService.RED);
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtUpperLimP.setBackground(ColorService.WHITE);
//					}
//				}
//				
//				
//			}
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
//					&& txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//					&& !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimP.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
//				
//				if(!checkLimit) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//
//					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//					
//					if(lowP.compareTo(upP) >= 0) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//				}
//			}
//			txtLowLimQ.setBackground(ColorService.WHITE);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
//			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
//					&& txtLowQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//					&& !txtLowQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.RED);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				
//				if(upP.compareTo(getLimitUp()) > 0) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.RED);
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//	
//					
//					if(lowP.compareTo(upP) >= 0) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//					}
//				}
//				
//				
//			}
//			txtUpperLimQ.setBackground(ColorService.WHITE);
//			return;
//		}
//		
//		
//		
//		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
//				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
//			
//			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
//					&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
//			
//			if(!valid) {
//				txtcompGenPandQWarning.setText(strOnlyDecAllowed);
//				showControl(txtcompGenPandQWarning);
//				
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//					&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//					txtUpperLimP.setBackground(ColorService.RED);
//					txtLowLimP.setBackground(ColorService.RED);
//					txtLowLimQ.setBackground(ColorService.RED);
//					txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.RED);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//				}
//				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
//						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//				}
//			}
//			else {
//				hideControl(txtcompGenPandQWarning);
//				txtUpperLimP.setBackground(ColorService.WHITE);
//				txtLowLimP.setBackground(ColorService.WHITE);
//				txtLowLimQ.setBackground(ColorService.WHITE);
//				txtUpperLimQ.setBackground(ColorService.WHITE);
//
//				
//				lowP = getNumFromLimit(txtLowP);
//				upP = getNumFromLimit(txtUpP);
//				lowQ = getNumFromLimit(txtLowQ);
//				upQ = getNumFromLimit(txtUpQ);
//				
//				
//				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
//				
//				if(!checkLimit) {
//					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
//					showControl(txtcompGenPandQWarning);
//
//					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.RED);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//					}
//					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.RED);
//					}
//				}
//				else {
//					hideControl(txtcompGenPandQWarning);
//					txtUpperLimP.setBackground(ColorService.WHITE);
//					txtUpperLimQ.setBackground(ColorService.WHITE);
//					
//					
//					boolean validCondAll = lowP.compareTo(upP) < 0 && lowQ.compareTo(upQ) < 0;
//					
//					if(!validCondAll) {
//						txtcompGenPandQWarning.setText(strLowerLimitLessUpperLimit);
//						showControl(txtcompGenPandQWarning);
//					
//						
//						if(!(lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
//							txtLowLimP.setBackground(ColorService.RED);
//							txtUpperLimP.setBackground(ColorService.RED);
//							txtLowLimQ.setBackground(ColorService.RED);
//							txtUpperLimQ.setBackground(ColorService.RED);
//						}
//						if(!(lowP.compareTo(upP) < 0) && (lowQ.compareTo(upQ) < 0)) {
//							txtLowLimP.setBackground(ColorService.RED);
//							txtUpperLimP.setBackground(ColorService.RED);
//							txtLowLimQ.setBackground(ColorService.WHITE);
//							txtUpperLimQ.setBackground(ColorService.WHITE);
//						}
//						if((lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
//							txtLowLimP.setBackground(ColorService.WHITE);
//							txtUpperLimP.setBackground(ColorService.WHITE);
//							txtLowLimQ.setBackground(ColorService.RED);
//							txtUpperLimQ.setBackground(ColorService.RED);
//						}
//					}
//					else {
//						hideControl(txtcompGenPandQWarning);
//						txtUpperLimP.setBackground(ColorService.WHITE);
//						txtLowLimP.setBackground(ColorService.WHITE);
//						txtLowLimQ.setBackground(ColorService.WHITE);
//						txtUpperLimQ.setBackground(ColorService.WHITE);
//
//						
//						if(btnGenKeys.getSelection()) {
//							btnStartGenKeys.setEnabled(true);
//						}
//					}
//
//				}
//	
//				
//			}
//			
//		}
//	}
	
	
	
	
	
	
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
		String pattern = "^([1-9]\\d*|2\\^\\d+)$"; //$NON-NLS-1$
		
		Color white = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
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
				if(upQ.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				
				if(upQ.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				if(upP.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
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
				if(upP.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
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
				
				if(upQ.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				
				if(upQ.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				if(upP.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
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
				
				if(upP.compareTo(getLimitUp()) > 0) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
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
				
				
				boolean checkLimit = upP.compareTo(getLimitUp()) <= 0 && upQ.compareTo(getLimitUp()) <= 0;
				
				if(!checkLimit) {
					txtcompGenPandQWarning.setText(MessageFormat.format(strUpperLimitRestriction, getLimitExp()));
					showControl(txtcompGenPandQWarning);

					if(!(upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(wrong);
					}
					if(!(upP.compareTo(getLimitUp()) <= 0) && (upQ.compareTo(getLimitUp()) <= 0)) {
						txtUpperLimP.setBackground(wrong);
						txtUpperLimQ.setBackground(white);
					}
					if((upP.compareTo(getLimitUp()) <= 0) && !(upQ.compareTo(getLimitUp()) <= 0)) {
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
