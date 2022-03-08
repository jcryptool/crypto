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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.ui.RabinThirdTabComposite;

public class HandleThirdTab extends GUIHandler {
	
	private int maxBitLimit = 40;
	private Rabin savedRabin;
	

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
		String n = rttc.getTxtN().getText();
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		
		Color neutral = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
		rttc.getBtnFactorize().setEnabled(false);
		
		if(n.isEmpty()) {
			rttc.getTxtN().setBackground(neutral);
			hideControl(rttc.getTxtNWarning());
			return;
		}
		
		if(!n.matches(pattern)) {
			rttc.getTxtN().setBackground(wrong);
			String strOnlyNumbersGreaterZero = Messages.HandleThirdTab_strOnlyNumbersGreaterZero;
			rttc.getTxtNWarning().setText(strOnlyNumbersGreaterZero);
			showControl(rttc.getTxtNWarning());
			return;
		}
		
		BigInteger nAsNum = new BigInteger(n);
		
		if(nAsNum.bitLength() > maxBitLimit) {
			String strMaxBitlengthOfN = Messages.HandleThirdTab_strMaxBitlengthOfN;
			rttc.getTxtNWarning().setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			showControl(rttc.getTxtNWarning());
			rttc.getTxtN().setBackground(wrong);
			return;
		}
		
		if(nAsNum.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
			String strNmustBeOdd = Messages.HandleThirdTab_strNmustBeOdd;
			rttc.getTxtNWarning().setText(strNmustBeOdd);
			showControl(rttc.getTxtNWarning());
			rttc.getTxtN().setBackground(wrong);
			return;
		}
		
		if(nAsNum.isProbablePrime(1000)) {
			String strNmustBeComposite = Messages.HandleThirdTab_strNmustBeComposite;
			rttc.getTxtNWarning().setText(strNmustBeComposite);
			showControl(rttc.getTxtNWarning());
			rttc.getTxtN().setBackground(wrong);
			return;
		}
		
		hideControl(rttc.getTxtNWarning());
		rttc.getTxtN().setBackground(neutral);
		//rttc.getBtnStartGen().setEnabled(true);
		rttc.getBtnFactorize().setEnabled(true);

	}
	
	
	/**
	 * @param e
	 * @param rttc
	 * @param txtN
	 * @param txtWarning
	 * @return true if modulus N is correct, else false
	 */
	public boolean updateTextfieldN(TypedEvent e, RabinThirdTabComposite rttc, Text txtN, Text txtWarning) {
		String n = txtN.getText();
		String pattern = "^[1-9]+\\d*$"; //$NON-NLS-1$
		
		Color neutral = this.getColorBackgroundNeutral();
		Color wrong = this.getColorBackgroundWrong();
		
		if(n.isEmpty()) {
			txtN.setBackground(neutral);
			hideControl(txtWarning);
			return false;
		}
		
		if(!n.matches(pattern)) {
			txtN.setBackground(wrong);
			String strOnlyNumbersGreaterZero = Messages.HandleThirdTab_strOnlyNumbersGreaterZero;
			txtWarning.setText(strOnlyNumbersGreaterZero);
			showControl(txtWarning);
			return false;
		}
		
		BigInteger nAsNum = new BigInteger(n);
		
		if(nAsNum.bitLength() > maxBitLimit) {
			String strMaxBitlengthOfN = Messages.HandleThirdTab_strMaxBitlengthOfN;
			txtWarning.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			showControl(txtWarning);
			txtN.setBackground(wrong);
			return false;
		}
		
		if(nAsNum.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
			String strNmustBeOdd = Messages.HandleThirdTab_strNmustBeOdd;
			txtWarning.setText(strNmustBeOdd);
			showControl(txtWarning);
			txtN.setBackground(wrong);
			return false;
		}
		
		if(nAsNum.isProbablePrime(1000)) {
			String strNmustBeComposite = Messages.HandleThirdTab_strNmustBeComposite;
			txtWarning.setText(strNmustBeComposite);
			showControl(txtWarning);
			txtN.setBackground(wrong);
			return false;
		}
		
		hideControl(txtWarning);
		txtN.setBackground(neutral);
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
	public boolean updateTextfieldxyc(TypedEvent e, RabinThirdTabComposite rttc, Text txtN, Text txt, Text txtWarning, Text txtWarningNPollard) {
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
		
		
		if(txtN.getText().isEmpty()) {
			txt.setBackground(wrong);
			txtWarning.setText("Attention: N is missing");
			showControl(txtWarning);
			return false;
		}
		
		this.hideControl(txtWarning);
		txt.setBackground(neutral);
		
		
		BigInteger txtAsNum = new BigInteger(strTxt);
		BigInteger nAsNum = new BigInteger(txtN.getText());
		
		
		if(nAsNum.bitLength() > maxBitLimit) {
			String strMaxBitlengthOfN = Messages.HandleThirdTab_strMaxBitlengthOfN;
			txtWarningNPollard.setText(MessageFormat.format(strMaxBitlengthOfN, maxBitLimit));
			showControl(txtWarningNPollard);
			txtN.setBackground(wrong);
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
	public void updateTextFieldsPollard(TypedEvent e, RabinThirdTabComposite rttc, Text txtN, Text txtNWarning, 
			Text txtx, Text txtxWarning, Text txty, Text txtyWarning, Text txtgx, Text txtgxWarning) {
		boolean checkResult = true;
		
		checkResult &= this.updateTextfieldN(e, rttc, txtN, txtNWarning);
		checkResult &= this.updateTextfieldxyc(e, rttc, txtN, txtx, txtxWarning, txtNWarning);
		checkResult &= this.updateTextfieldxyc(e, rttc, txtN, txty, txtyWarning, txtNWarning);
		checkResult &= this.updateTextfieldxyc(e, rttc, txtN, txtgx, txtgxWarning, txtNWarning);
		
		if(checkResult)
			rttc.getBtnFactorizePollard().setEnabled(true);
		else
			rttc.getBtnFactorizePollard().setEnabled(false);
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
		rttc.getFactorTable().removeAll();
		
		rttc.getTxtP1().setText(""); //$NON-NLS-1$
		rttc.getTxtP2().setText(""); //$NON-NLS-1$
		rttc.getTxtResultP().setText(""); //$NON-NLS-1$
		rttc.getTxtQ1().setText(""); //$NON-NLS-1$
		rttc.getTxtQ2().setText(""); //$NON-NLS-1$
		rttc.getTxtResultQ().setText(""); //$NON-NLS-1$
		rttc.getTxtResultP().setBackground(this.getColorBackgroundNeutral());
		rttc.getTxtResultQ().setBackground(this.getColorBackgroundNeutral());
	}
	
	
	
	
	
	/**
	 * action for btnGenKeysAlgo
	 * @param rttc
	 * @param txtWarningN
	 * @param txtN
	 */
	public void btnGenKeysAlgoAction(RabinThirdTabComposite rttc, Text txtWarningN, Text txtN) {
		this.btnGenKeysAlgoPollardAction(rttc, txtWarningN, txtN);
	}
	
	
	
	
	/**
	 * action for btnGenKeysAlgoPollard
	 * @param rttc
	 * @param txtWarningNPollard
	 * @param txtNPollard
	 */
	public void btnGenKeysAlgoPollardAction(RabinThirdTabComposite rttc, Text txtWarningNPollard, Text txtNPollard) {
		BigInteger n = this.getRabinSecond().getN();
		
		if(n == null) {
			txtWarningNPollard.setText(Messages.HandleThirdTab_strGenKeyPairTT);
			this.showControl(txtWarningNPollard);
			return;
		}
		
		this.hideControl(txtWarningNPollard);
		txtNPollard.setText(n.toString());
		this.getRabinFirst().setN(n);
	}
	

	
	
	
	
	
	
	/**
	 * action for btnStartGen
	 * @param rttc
	 */
	public void btnStartGenAction(RabinThirdTabComposite rttc) {
		if(rttc.getBtnGenKeysMan().getSelection()) {
			String nStr = rttc.getTxtN().getText();
			BigInteger n = new BigInteger(nStr);
			getRabinFirst().setN(n);
		}
		
		if(rttc.getBtnGenKeysAlgo().getSelection()) {
			BigInteger n = getRabinSecond().getN();
			
			if(n == null) {
				String strGenKeyPairTT = Messages.HandleThirdTab_strGenKeyPairTT;
				rttc.getTxtNWarning().setText(strGenKeyPairTT);
				showControl(rttc.getTxtNWarning());
				return;
			}
			
			
			/*if(n.bitLength() > maxBitLimit) {
				rttc.getTxtNWarning().setText("\tAttention: bitlength of N must be <= " + maxBitLimit +  " bits");
				showControl(rttc.getTxtNWarning());
				rttc.getTxtN().setBackground(ColorService.RED);
				return;
			}*/
			
			
			hideControl(rttc.getTxtNWarning());
			getRabinFirst().setN(n);
			rttc.getTxtN().setText(n.toString());
			
		}
		
		this.resetTabContent(rttc);
		
		
		rttc.getBtnFactorize().setEnabled(true);
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
	public void btnFactorizePollardAction(RabinThirdTabComposite rttc, Text txtNPollard, Text txtxPollard, Text txtyPollard, Text txtcPollard, Text txtWarningNPollard, 
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
		
		rttc.getPollardFactorTable().removeAll();
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
			TableItem tableItem= new TableItem(rttc.getPollardFactorTable(), SWT.NONE);
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
	}
	
	
	
	
	/**
	 * action for btnFactorize
	 * @param rttc
	 */
	public void btnFactorizeAction(RabinThirdTabComposite rttc) {
		//BigInteger n = getRabinFirst().getN();
		String nAsStr = rttc.getTxtN().getText();
		BigInteger n = new BigInteger(nAsStr);
		
		Color neutral = this.getColorBackgroundNeutral();
		Color correct = this.getColorBackgroundCorrect();
	
		
		/*if(n == null) {
			//txtWarningApplyAndFactor.setText("Attention: you must choose N first");
			rttc.getTxtNWarning().setText("Attention: you must choose N first");
			showControl(rttc.getTxtNWarning());
			rttc.getTxtN().setBackground(ColorService.RED);
			return;
		}*/
		
		hideControl(rttc.getTxtNWarning());
		rttc.getTxtN().setBackground(neutral);
		
		rttc.getFactorTable().removeAll();
		
		
		BigInteger y = n.sqrt();
		BigInteger y_2_n = null;
		BigDecimal y_2_nAsBigDecimal = null;
		BigDecimal y_2_nSquare = null;
		BigInteger y_2 = null;
		do{
			y = y.add(BigInteger.ONE);
			y_2 = y.pow(2);
			y_2_n = y_2.subtract(n);
			y_2_nAsBigDecimal = new BigDecimal(y_2_n);
			y_2_nSquare = y_2_nAsBigDecimal.sqrt(new MathContext(y_2_nAsBigDecimal.precision() + 5, RoundingMode.FLOOR));

			String[] tableComponents = new String[] {y.toString(), y_2.toString(), y_2_n.toString(), y_2_nSquare.toString()};
			TableItem tableItem= new TableItem(rttc.getFactorTable(), SWT.NONE);
			tableItem.setText(tableComponents);
			
		}while(!getRabinFirst().isInteger(y_2_nSquare)); 
		
		BigInteger p = y.add(y_2_nSquare.toBigInteger());
		BigInteger q = y.subtract(y_2_nSquare.toBigInteger());
		
		boolean isValidPrime = p.isProbablePrime(1000) && q.isProbablePrime(1000);
		
		if(isValidPrime) {
			rttc.getTxtP1().setText(y.toString());
			rttc.getTxtP2().setText(y_2_nSquare.toString());
			rttc.getTxtResultP().setText(p.toString());
			rttc.getTxtQ1().setText(y.toString());
			rttc.getTxtQ2().setText(y_2_nSquare.toString());
			rttc.getTxtResultQ().setText(q.toString());
			rttc.getTxtResultP().setBackground(correct);
			rttc.getTxtResultQ().setBackground(correct);
		}
		else {
			String strCouldNotFindPandQ = Messages.HandleThirdTab_strCouldNotFindPandQ;
			rttc.getTxtNWarning().setText(strCouldNotFindPandQ);
			showControl(rttc.getTxtNWarning());
		}

	}

}
