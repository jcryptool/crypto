package org.jcryptool.visual.rabin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.MessageFormat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.ui.RabinThirdTabComposite;

public class HandleThirdTab extends GUIHandler {
	
	private int maxBitLimit = 200;
	private Rabin savedRabin;
	private boolean stopComputation = false;
	private Thread fermatFactorizeThread = null;
	private boolean endFermatFactorize = false;
	private Thread threadPollardFactorize = null;
	private boolean endPollardFactorize = false;
	private boolean stopComputationPollard = false;
	

	/**
	 * @param scMain
	 * @param compMain
	 * @param rabinFirst
	 * @param rabinSecond
	 */
	public HandleThirdTab(ScrolledComposite scMain, Composite compMain, Rabin rabinFirst, Rabin rabinSecond) {
		super(scMain, compMain, rabinFirst, rabinSecond);
		savedRabin = new Rabin();
	}
	
	
	
	public void setStopComputation(boolean state) {
		stopComputation = state;
	}
	
	
	
	/**
	 * @return savedRabin
	 */
	public Rabin getSavedRabin() {
		return savedRabin;
	}
	
	
	/**
	 * upadte textfield for modulus N each time you enter a char
	 * @param e
	 * @param rttc
	 */
	public void updateTextfieldN(TypedEvent e, RabinThirdTabComposite rttc) {
		String n = rttc.cmbN.getText();
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		
		Color neutral = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
		rttc.btnFactorize.setEnabled(false);
		
		if(n.isEmpty()) {
			rttc.cmbN.setBackground(neutral);
			hideControl(rttc.txtNWarning);
			return;
		}
		
		if(!n.matches(pattern)) {
			rttc.cmbN.setBackground(wrong);
			String strOnlyNumbersGreaterZero = Messages.HandleThirdTab_strOnlyNumbersGreaterZero;
			rttc.txtNWarning.setText(strOnlyNumbersGreaterZero);
			showControl(rttc.txtNWarning);
			return;
		}
		
		BigInteger nAsNum = new BigInteger(n);
		
		/*if(nAsNum.bitLength() > maxBitLimit) {
			String strMaxBitlengthOfN = Messages.HandleThirdTab_strMaxBitlengthOfN;
			rttc.txtNWarning.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			showControl(rttc.txtNWarning);
			rttc.cmbN.setBackground(wrong);
			return;
		}*/
		
		
		if(nAsNum.compareTo(getLimitUpAttacks()) > 0) {
			String strMaxBitlengthOfN = "Attention: only an upper limit of 2^" + getLimitExpAttacks() + " is allowed";
			//txtWarning.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			rttc.txtNWarning.setText(strMaxBitlengthOfN);
			showControl(rttc.txtNWarning);
			rttc.cmbN.setBackground(wrong);
			return;
		}
		
		
		
		if(nAsNum.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
			String strNmustBeOdd = Messages.HandleThirdTab_strNmustBeOdd;
			rttc.txtNWarning.setText(strNmustBeOdd);
			showControl(rttc.txtNWarning);
			rttc.cmbN.setBackground(wrong);
			return;
		}
		
		if(nAsNum.isProbablePrime(1000)) {
			String strNmustBeComposite = Messages.HandleThirdTab_strNmustBeComposite;
			rttc.txtNWarning.setText(strNmustBeComposite);
			showControl(rttc.txtNWarning);
			rttc.cmbN.setBackground(wrong);
			return;
		}
		
		hideControl(rttc.txtNWarning);
		rttc.cmbN.setBackground(neutral);
		//rttc.getBtnStartGen().setEnabled(true);
		rttc.btnFactorize.setEnabled(true);

	}
	
	
	
	
	public void cmbSelectionAction(Combo cmbP) {
		int idx = cmbP.getSelectionIndex();
		String item = cmbP.getItem(idx);
		
		//cmbP.removeVerifyListener(vlNumbers);
		cmbP.setText(item);
		//cmbP.addVerifyListener(vlNumbers);
	}
	
	
	
	
	
	/**
	 * @param e
	 * @param rttc
	 * @param txtN
	 * @param txtWarning
	 * @return true if modulus N is correct, else false
	 */
	public boolean updateTextfieldN(TypedEvent e, RabinThirdTabComposite rttc, Combo cmbN, Text txtWarning) {
		String n = cmbN.getText();
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		
		Color neutral = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
		if(n.isEmpty()) {
			cmbN.setBackground(neutral);
			hideControl(txtWarning);
			return false;
		}
		
		if(!n.matches(pattern)) {
			cmbN.setBackground(wrong);
			String strOnlyNumbersGreaterZero = Messages.HandleThirdTab_strOnlyNumbersGreaterZero;
			txtWarning.setText(strOnlyNumbersGreaterZero);
			showControl(txtWarning);
			return false;
		}
		
		BigInteger nAsNum = new BigInteger(n);
		
		if(nAsNum.compareTo(getLimitUpAttacks()) > 0) {
			String strMaxBitlengthOfN = "Attention: only an upper limit of 2^" + getLimitExpAttacks() + " is allowed";
			//txtWarning.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			txtWarning.setText(strMaxBitlengthOfN);
			showControl(txtWarning);
			cmbN.setBackground(wrong);
			return false;
		}
		
		if(nAsNum.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
			String strNmustBeOdd = Messages.HandleThirdTab_strNmustBeOdd;
			txtWarning.setText(strNmustBeOdd);
			showControl(txtWarning);
			cmbN.setBackground(wrong);
			return false;
		}
		
		if(nAsNum.isProbablePrime(1000)) {
			String strNmustBeComposite = Messages.HandleThirdTab_strNmustBeComposite;
			txtWarning.setText(strNmustBeComposite);
			showControl(txtWarning);
			cmbN.setBackground(wrong);
			return false;
		}
		
		hideControl(txtWarning);
		cmbN.setBackground(neutral);
		return true;
	}
	
	
	
	
	
	/**
	 * update textfields for x, y, c 
	 * @param e
	 * @param rttc
	 * @param txtN
	 * @param txt
	 * @param txtWarning
	 * @param txtWarningNPollard
	 * @return
	 */
	public boolean updateTextfieldxyc(TypedEvent e, RabinThirdTabComposite rttc, Combo cmbN, Text txt, Text txtWarning, Text txtWarningNPollard) {
		String strTxt = txt.getText();
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		
		Color neutral = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
	
		
		if(strTxt.isEmpty()) {
			txt.setBackground(neutral);
			hideControl(txtWarning);
			return false;
		}
		
		if(!strTxt.matches(pattern)) {
			txt.setBackground(wrong);
			String strOnlyNumbersGreaterZero = Messages.HandleThirdTab_strOnlyNumbersGreaterZero;
			txtWarning.setText(strOnlyNumbersGreaterZero);
			showControl(txtWarning);
			return false;
		}
		
		
		if(cmbN.getText().isEmpty()) {
			txt.setBackground(wrong);
			txtWarning.setText("Attention: N is missing");
			showControl(txtWarning);
			return false;
		}
		
		this.hideControl(txtWarning);
		txt.setBackground(neutral);
		
		
		BigInteger txtAsNum = new BigInteger(strTxt);
		BigInteger nAsNum = new BigInteger(cmbN.getText());
		
		
		/*if(nAsNum.bitLength() > maxBitLimit) {
			String strMaxBitlengthOfN = Messages.HandleThirdTab_strMaxBitlengthOfN;
			txtWarningNPollard.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			showControl(txtWarningNPollard);
			cmbN.setBackground(wrong);
			return false;
		}*/
		
		
		if(nAsNum.compareTo(getLimitUpAttacks()) > 0) {
			String strMaxBitlengthOfN = "Attention: only an upper limit of 2^" + getLimitExpAttacks() + " is allowed";
			txtWarningNPollard.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			showControl(txtWarningNPollard);
			cmbN.setBackground(wrong);
			return false;
		}
		
		
		if(txtAsNum.compareTo(nAsNum) >= 0) {
			// TODO continue here
			txt.setBackground(wrong);
			txtWarning.setText("Attention: the conditon number < N has to be satisfied");
			showControl(txtWarning);
			return false;
		}
		
		
		hideControl(txtWarning);
		txt.setBackground(neutral);
		return true;

	}
	
	
	/**
	 * update all textfields in pollard rho group
	 * @param e
	 * @param rttc
	 * @param txtN
	 * @param txtNWarning
	 * @param txtx
	 * @param txtxWarning
	 * @param txty
	 * @param txtyWarning
	 * @param txtgx
	 * @param txtgxWarning
	 */
	public void updateTextFieldsPollard(TypedEvent e, RabinThirdTabComposite rttc, Combo cmbN, Text txtNWarning, 
			Text txtx, Text txtxWarning, Text txty, Text txtyWarning, Text txtgx, Text txtgxWarning) {
		boolean checkResult = true;
		
		checkResult &= this.updateTextfieldN(e, rttc, cmbN, txtNWarning);
		checkResult &= this.updateTextfieldxyc(e, rttc, cmbN, txtx, txtxWarning, txtNWarning);
		checkResult &= this.updateTextfieldxyc(e, rttc, cmbN, txty, txtyWarning, txtNWarning);
		checkResult &= this.updateTextfieldxyc(e, rttc, cmbN, txtgx, txtgxWarning, txtNWarning);
		
		if(checkResult)
			rttc.btnFactorizePollard.setEnabled(true);
		else
			rttc.btnFactorizePollard.setEnabled(false);
	}

	
	

	
	
	
	/**
	 * action for btnGenKeysMan
	 * @param e
	 * @param rttc
	 */
	public void btnGenKeysManAction(SelectionEvent e, RabinThirdTabComposite rttc) {
		Button src = (Button) e.getSource();
		
		if(src.getSelection())
			updateTextfieldN(e, rttc);
	}
	
	
	
	
	/**
	 * reset tab content, not used
	 * @param rttc
	 */
	private void resetTabContent(RabinThirdTabComposite rttc) {
		rttc.factorTable.removeAll();
		
		rttc.txtP1.setText(""); //$NON-NLS-1$
		rttc.txtP2.setText(""); //$NON-NLS-1$
		rttc.txtResultP.setText(""); //$NON-NLS-1$
		rttc.txtQ1.setText(""); //$NON-NLS-1$
		rttc.txtQ2.setText(""); //$NON-NLS-1$
		rttc.txtResultQ.setText(""); //$NON-NLS-1$
		rttc.txtResultP.setBackground(this.getColorBackgroundNeutral());
		rttc.txtResultQ.setBackground(this.getColorBackgroundNeutral());
	}
	
	
	
	
	
	/**
	 * action for btnGenKeysAlgo
	 * @param rttc
	 * @param txtWarningN
	 * @param txtN
	 */
	public void btnGenKeysAlgoAction(RabinThirdTabComposite rttc, Text txtWarningN, Combo cmbN) {
		this.btnGenKeysAlgoPollardAction(rttc, txtWarningN, cmbN);
	}
	
	
	
	
	/**
	 * action for btnGenKeysAlgoPollard
	 * @param rttc
	 * @param txtWarningNPollard
	 * @param txtNPollard
	 */
	public void btnGenKeysAlgoPollardAction(RabinThirdTabComposite rttc, Text txtWarningNPollard, Combo cmbNPollard) {
		BigInteger n = this.getRabinSecond().getN();
		
		if(n == null) {
			txtWarningNPollard.setText(Messages.HandleThirdTab_strGenKeyPairTT);
			this.showControl(txtWarningNPollard);
			return;
		}
		
		this.hideControl(txtWarningNPollard);
		cmbNPollard.setText(n.toString());
		this.getRabinFirst().setN(n);
	}
	

	
	
	
	
	
	
	/**
	 * action for btnStartGen
	 * @param rttc
	 */
	public void btnStartGenAction(RabinThirdTabComposite rttc) {
		if(rttc.btnGenKeysMan.getSelection()) {
			String nStr = rttc.cmbN.getText();
			BigInteger n = new BigInteger(nStr);
			getRabinFirst().setN(n);
		}
		
		if(rttc.btnGenKeysAlgo.getSelection()) {
			BigInteger n = getRabinSecond().getN();
			
			if(n == null) {
				String strGenKeyPairTT = Messages.HandleThirdTab_strGenKeyPairTT;
				rttc.txtNWarning.setText(strGenKeyPairTT);
				showControl(rttc.txtNWarning);
				return;
			}
			
			
			/*if(n.bitLength() > maxBitLimit) {
				rttc.txtNWarning.setText("\tAttention: bitlength of N must be <= " + maxBitLimit +  " bits");
				showControl(rttc.txtNWarning);
				rttc.cmbN.setBackground(ColorService.RED);
				return;
			}*/
			
			
			hideControl(rttc.txtNWarning);
			getRabinFirst().setN(n);
			rttc.cmbN.setText(n.toString());
			
		}
		
		this.resetTabContent(rttc);
		
		
		rttc.btnFactorize.setEnabled(true);
	}
	
	
	/**
	 * pseudo random number function g(x) = x^2 + c
	 * @param x
	 * @param c
	 * @param n
	 * @return
	 */
	private BigInteger gRndFunction(BigInteger x, BigInteger c, BigInteger n) {
		BigInteger ret = x.multiply(x).add(c).mod(n);
		return ret;
	}
	
	
	/**
	 * action for btnFactorizePollard
	 * @param rttc
	 * @param txtNPollard
	 * @param txtxPollard
	 * @param txtyPollard
	 * @param txtcPollard
	 * @param txtWarningNPollard
	 * @param txtpPollard
	 * @param txtqPollard
	 */
	/*public void btnFactorizePollardAction(RabinThirdTabComposite rttc, Text txtNPollard, Text txtxPollard, Text txtyPollard, Text txtcPollard, Text txtWarningNPollard, 
			Text txtpPollard, Text txtqPollard) {
		String nAsStr = txtNPollard.getText();
		String xAsStr = txtxPollard.getText();
		String yAsStr = txtyPollard.getText();
		String cAsStr = txtcPollard.getText();
		
		Color neutral = this.getColorBackgroundNeutral();
		Color correct = this.getColorBackgroundCorrect();
		
		BigInteger n = new BigInteger(nAsStr);
		BigInteger x = new BigInteger(xAsStr);
		BigInteger y = new BigInteger(yAsStr);
		BigInteger c = new BigInteger(cAsStr);
		
		BigInteger d = BigInteger.ONE;
		
		BigInteger cnt = BigInteger.ONE;
		
		rttc.pollardFactorTable.removeAll();
		txtpPollard.setText("");
		txtqPollard.setText("");
		txtpPollard.setBackground(neutral);
		txtqPollard.setBackground(neutral);
		
		while(d.compareTo(BigInteger.ONE) == 0) {
			x = this.gRndFunction(x, c, n);
			y = this.gRndFunction(y, c, n);
			y = this.gRndFunction(y, c, n);
			BigInteger temp = x.subtract(y).abs();
			BigInteger[] gcdRes = this.getRabinFirst().Gcd(temp, n);
			d = gcdRes[0];
			String[] itemContent = new String[] {cnt.toString(), x.toString(), y.toString(), d.toString()};
			TableItem tableItem= new TableItem(rttc.pollardFactorTable, SWT.NONE);
			tableItem.setText(itemContent);
			cnt = cnt.add(BigInteger.ONE);
		}
		
		if(d.compareTo(n) == 0) {
			txtWarningNPollard.setText("Attention: could not find primes p and q. Please try again or use other parameters");
			this.showControl(txtWarningNPollard);
			return;
		}
		
		this.hideControl(txtWarningNPollard);
		
		BigInteger p = d;
		BigInteger q = n.divide(p);
		txtpPollard.setText(p.toString());
		txtqPollard.setText(q.toString());
		txtpPollard.setBackground(correct);
		txtqPollard.setBackground(correct);
	}*/
	
	
	
	
	public Thread btnFactorizePollard(RabinThirdTabComposite rttc, Combo cmbNPollard, Text txtxPollard, Text txtyPollard, Text txtcPollard, Text txtWarningNPollard, 
			Text txtpPollard, Text txtqPollard) {
		
		//stopComputationPollard = false;
		
		Thread t = new Thread(new Runnable() {
			
			String nAsStr;
			String xAsStr;
			String yAsStr;
			String cAsStr;
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Runnable r = new Runnable() {
					
					

					@Override
					public void run() {
						// TODO Auto-generated method stub
						nAsStr = cmbNPollard.getText();
						xAsStr = txtxPollard.getText();
						yAsStr = txtyPollard.getText();
						cAsStr = txtcPollard.getText();
					}
				};
				
				Display.getDefault().syncExec(r);
				
				
				Color neutral = getColorBGinfo();
				Color correct = getColorBackgroundCorrect();
				
				BigInteger n = new BigInteger(nAsStr);
				BigInteger x = new BigInteger(xAsStr);
				BigInteger y = new BigInteger(yAsStr);
				BigInteger c = new BigInteger(cAsStr);
				
				BigInteger d = BigInteger.ONE;
				
				BigInteger cnt = BigInteger.ONE;
				
				r = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						rttc.pollardFactorTable.removeAll();
						txtpPollard.setText("");
						txtqPollard.setText("");
						
						if(getDarkmode()) {
							txtpPollard.setBackground(getColorDarkModeBG());
							txtqPollard.setBackground(getColorDarkModeBG());
						}
						else {
							txtpPollard.setBackground(neutral);
							txtqPollard.setBackground(neutral);
						}
					}
				};
				
				Display.getDefault().syncExec(r);
				
				
				while(d.compareTo(BigInteger.ONE) == 0 && !stopComputationPollard) {
					x = gRndFunction(x, c, n);
					y = gRndFunction(y, c, n);
					y = gRndFunction(y, c, n);
					BigInteger temp = x.subtract(y).abs();
					BigInteger[] gcdRes = getRabinFirst().Gcd(temp, n);
					d = gcdRes[0];
					String[] itemContent = new String[] {cnt.toString(), x.toString(), y.toString(), d.toString()};
					
					r = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							TableItem tableItem = new TableItem(rttc.pollardFactorTable, SWT.NONE);
							tableItem.setText(itemContent);
						}
					};
					
					Display.getDefault().syncExec(r);
					
					cnt = cnt.add(BigInteger.ONE);
				}
				
				if(stopComputationPollard) {
					
					r = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							showControl(rttc.txtInfoStopComputationPollard);
							rttc.pollardFactorTable.removeAll();
						}
					};
					
					Display.getDefault().syncExec(r);
					
					return;
				}
				
				
				if(d.compareTo(n) == 0) {
					
					r = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							txtWarningNPollard.setText("Attention: could not find primes p and q. Please try again or use other parameters");
							showControl(txtWarningNPollard);
						}
					};
					
					Display.getDefault().syncExec(r);
					
					return;
				}
				
				r = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						hideControl(txtWarningNPollard);
					}
				};
				
				Display.getDefault().syncExec(r);
							
				BigInteger p = d;
				BigInteger q = n.divide(p);
				
				r = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						txtpPollard.setText(p.toString());
						txtqPollard.setText(q.toString());
						txtpPollard.setBackground(correct);
						txtqPollard.setBackground(correct);
					}
				};
				
				Display.getDefault().syncExec(r);
				
				endPollardFactorize = true;
				
			}
		});
		
		
		return t;
		
	}
	
	
	
	public void btnFactorizePollardAction(RabinThirdTabComposite rttc, Combo cmbNPollard, Text txtxPollard, Text txtyPollard, Text txtcPollard, Text txtWarningNPollard, 
			Text txtpPollard, Text txtqPollard) {
		this.hideControl(rttc.txtInfoStopComputationPollard);
		stopComputationPollard = false;
		
		//stopComputation = false;
		//rttc.btnStopComputation.setEnabled(false);
		
		//endFermatFactorize = false;
		//rttc.btnStopComputation.setEnabled(false);
		
		if(threadPollardFactorize == null) {
			threadPollardFactorize = btnFactorizePollard(rttc, cmbNPollard, txtxPollard, txtyPollard, txtcPollard, txtWarningNPollard, txtpPollard, txtqPollard);
			threadPollardFactorize.start();
		}
		
		if(endPollardFactorize) {
			threadPollardFactorize = null;
			threadPollardFactorize = btnFactorizePollard(rttc, cmbNPollard, txtxPollard, txtyPollard, txtcPollard, txtWarningNPollard, txtpPollard, txtqPollard);
			threadPollardFactorize.start();
		}
	}
	
	
	public void btnStopComputationPollardAction(RabinThirdTabComposite rttc) {
		stopComputationPollard = true;
		threadPollardFactorize = null;
	}
	
	
	
	
	
	public Thread btnFactorizeAction(RabinThirdTabComposite rttc) {
		endFermatFactorize = false;
		hideControl(rttc.txtNWarning);
		
		Thread t = new Thread(new Runnable() {
			
			String nAsStr = null;
			BigInteger y = null;
			BigInteger y_2_n = null;
			BigDecimal y_2_nAsBigDecimal = null;
			BigDecimal y_2_nSquare = null;
			BigInteger y_2 = null;
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			
				Runnable r1 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						rttc.txtP1.setText("");
						rttc.txtP2.setText("");
						rttc.txtResultP.setText("");
						//rttc.txtP1.setBackground(getColorBGinfo());
						//rttc.txtP1.setBackground(getColorBGinfo());
						if(getDarkmode())
							rttc.txtResultP.setBackground(getColorDarkModeBG());
						else
							rttc.txtResultP.setBackground(getColorBGinfo());
						rttc.txtQ1.setText("");
						rttc.txtQ2.setText("");
						rttc.txtResultQ.setText("");
						
						if(getDarkmode())
							rttc.txtResultQ.setBackground(getColorDarkModeBG());
						else
							rttc.txtResultQ.setBackground(getColorBGinfo());
					
						nAsStr = rttc.cmbN.getText();
							
					}
				};
				
				Display.getDefault().syncExec(r1);
				
				BigInteger n = new BigInteger(nAsStr);
				
				Color neutral = getColorBackgroundNeutral();
				Color correct = getColorBackgroundCorrect();
			
			
				r1 = new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						hideControl(rttc.txtNWarning);
						rttc.cmbN.setBackground(neutral);
						
						rttc.factorTable.removeAll();
						
					}
				};
				
				Display.getDefault().syncExec(r1);
				
				y = n.sqrt();
				
				do{
					y = y.add(BigInteger.ONE);
					y_2 = y.pow(2);
					y_2_n = y_2.subtract(n);
					y_2_nAsBigDecimal = new BigDecimal(y_2_n);
					y_2_nSquare = y_2_nAsBigDecimal.sqrt(new MathContext(y_2_nAsBigDecimal.precision() + 5, RoundingMode.FLOOR));

					String[] tableComponents = new String[] {y.toString(), y_2.toString(), y_2_n.toString(), y_2_nSquare.toString()};
				
					r1 = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							TableItem tableItem = new TableItem(rttc.factorTable, SWT.NONE);
							tableItem.setText(tableComponents);
						}
					};
					
					Display.getDefault().syncExec(r1);
					
					
				}while(!getRabinFirst().isInteger(y_2_nSquare) && !stopComputation);
				
				if(stopComputation) {
					
					r1 = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							//rttc.txtNWarning.setText("stopped computation successfully");
							showControl(rttc.txtInfoStopComputation);
							rttc.factorTable.removeAll();
						}
					};
					
					Display.getDefault().syncExec(r1);
					
					
					return;
				}
				
				
				BigInteger p = y.add(y_2_nSquare.toBigInteger());
				BigInteger q = y.subtract(y_2_nSquare.toBigInteger());
				
				boolean isValidPrime = p.isProbablePrime(1000) && q.isProbablePrime(1000);
				
				if(isValidPrime) {
					
					r1 = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							rttc.txtP1.setText(y.toString());
							rttc.txtP2.setText(y_2_nSquare.toString());
							rttc.txtResultP.setText(p.toString());
							rttc.txtQ1.setText(y.toString());
							rttc.txtQ2.setText(y_2_nSquare.toString());
							rttc.txtResultQ.setText(q.toString());
							rttc.txtResultP.setBackground(correct);
							rttc.txtResultQ.setBackground(correct);
					
						}
					};
					
					Display.getDefault().syncExec(r1);
					
				}
				else {
					String strCouldNotFindPandQ = Messages.HandleThirdTab_strCouldNotFindPandQ;
					
					r1 = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							rttc.txtNWarning.setText(strCouldNotFindPandQ);
							showControl(rttc.txtNWarning);
						}
					};
					
					Display.getDefault().syncExec(r1);
				}
				endFermatFactorize = true;
			}
			
			
		});
		//BigInteger n = getRabinFirst().getN();
		
		return t;

	}
	
	
	
	public void btnFactorizeFermatAction(RabinThirdTabComposite rttc) {
		this.hideControl(rttc.txtInfoStopComputation);
		stopComputation = false;
		
		//stopComputation = false;
		//rttc.btnStopComputation.setEnabled(false);
		
		//endFermatFactorize = false;
		//rttc.btnStopComputation.setEnabled(false);
		
		if(fermatFactorizeThread == null) {
			fermatFactorizeThread = btnFactorizeAction(rttc);
			fermatFactorizeThread.start();
		}
		/*else {
			this.setStopComputation(true);
			fermatFactorizeThread = null;
			//rttc.factorTable.removeAll();
			fermatFactorizeThread = btnFactorizeAction(rttc);
			fermatFactorizeThread.start();
		}*/
		if(endFermatFactorize) {
			fermatFactorizeThread = null;
			fermatFactorizeThread = btnFactorizeAction(rttc);
			fermatFactorizeThread.start();
		}
		
		
		/*Runnable r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String resultP = rttc.txtResultP.getText();
				String resultQ = rttc.txtResultQ.getText();
				
				if(resultP.isEmpty() && resultQ.isEmpty()) {
					rttc.btnStopComputation.setEnabled(true);
					
				}
			}
		};
		
		Display.getDefault().timerExec(3000, r);*/
		
		
		/*String resultP = rttc.txtResultP.getText();
		String resultQ = rttc.txtResultQ.getText();
		
		
		if(resultP.isEmpty() && resultQ.isEmpty())
			rttc.btnStopComputation.setEnabled(true);*/

	}
	
	
	
	public void btnStopComputation(RabinThirdTabComposite rttc) {
		this.setStopComputation(true);
		fermatFactorizeThread = null;
		//rttc.btnStopComputation.setEnabled(false);
	}
	
	
	
	public void txtResultPQAction(RabinThirdTabComposite rttc) {
		String strResultP = rttc.txtResultP.getText();
		String strResultQ = rttc.txtResultQ.getText();
		
		if(strResultP.isEmpty() && strResultQ.isEmpty())
			return;
		
		rttc.btnStopComputation.setEnabled(false);
	}
	
	
	
	
	
	/**
	 * action for btnFactorize
	 * @param rttc
	 */
//	public void btnFactorizeAction(RabinThirdTabComposite rttc) {
//		//BigInteger n = getRabinFirst().getN();
//		String nAsStr = rttc.getTxtN().getText();
//		BigInteger n = new BigInteger(nAsStr);
//		
//		Color neutral = this.getColorBackgroundNeutral();
//		Color correct = this.getColorBackgroundCorrect();
//	
//		
//		
//		
//		hideControl(rttc.txtNWarning);
//		rttc.getTxtN().setBackground(neutral);
//		
//		rttc.factorTable.removeAll();
//		
//		
//		BigInteger y = n.sqrt();
//		BigInteger y_2_n = null;
//		BigDecimal y_2_nAsBigDecimal = null;
//		BigDecimal y_2_nSquare = null;
//		BigInteger y_2 = null;
//		do{
//			y = y.add(BigInteger.ONE);
//			y_2 = y.pow(2);
//			y_2_n = y_2.subtract(n);
//			y_2_nAsBigDecimal = new BigDecimal(y_2_n);
//			y_2_nSquare = y_2_nAsBigDecimal.sqrt(new MathContext(y_2_nAsBigDecimal.precision() + 5, RoundingMode.FLOOR));
//
//			String[] tableComponents = new String[] {y.toString(), y_2.toString(), y_2_n.toString(), y_2_nSquare.toString()};
//			TableItem tableItem= new TableItem(rttc.factorTable, SWT.NONE);
//			tableItem.setText(tableComponents);
//			
//		}while(!getRabinFirst().isInteger(y_2_nSquare)); 
//		
//		BigInteger p = y.add(y_2_nSquare.toBigInteger());
//		BigInteger q = y.subtract(y_2_nSquare.toBigInteger());
//		
//		boolean isValidPrime = p.isProbablePrime(1000) && q.isProbablePrime(1000);
//		
//		if(isValidPrime) {
//			rttc.txtP1.setText(y.toString());
//			rttc.txtP2.setText(y_2_nSquare.toString());
//			rttc.txtResultP.setText(p.toString());
//			rttc.txtQ1.setText(y.toString());
//			rttc.txtQ2.setText(y_2_nSquare.toString());
//			rttc.txtResultQ.setText(q.toString());
//			rttc.txtResultP.setBackground(correct);
//			rttc.txtResultQ.setBackground(correct);
//		}
//		else {
//			String strCouldNotFindPandQ = Messages.HandleThirdTab_strCouldNotFindPandQ;
//			rttc.txtNWarning.setText(strCouldNotFindPandQ);
//			showControl(rttc.txtNWarning);
//		}
//
//	}

}
