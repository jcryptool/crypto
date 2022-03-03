package org.jcryptool.visual.rabin;

import java.math.BigInteger;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.visual.rabin.ui.RabinFirstTabComposite;
import org.jcryptool.visual.rabin.ui.RabinSecondTabComposite;


public class A {
	
	public void test() {
		B b = new B();
		b.foo(this);
	}
	
	/*public void updateTextfields(Control tabComposite) {
		BigInteger ptmp = null;
		BigInteger qtmp = null;
		String pattern = "^[1-9]+\\d*$";
		RabinFirstTabComposite rftc = null;
		RabinSecondTabComposite rstc = null;
		
		Text txtP = null;
		Text txtQ = null;
		Combo cmbP = null;
		Combo cmbQ = null;
		
		
		
		if(tabComposite instanceof RabinFirstTabComposite) {
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
					qWarning.setText("Attention: only numbers > 0 are allowed");
					showControl(qWarning);
					return;
				}
				
				txtP.setBackground(ColorService.RED);
				pWarning.setText("Attention: p is missing");
				showControl(pWarning);
				
				qtmp = new BigInteger(qAsStr);
				
				if(qtmp.compareTo(limitUp) > 0) {
					txtQ.setBackground(ColorService.RED);
					qWarning.setText("Attention: "
						+ "only an upper limit of 2^" + limitExp + " is allowed");
					showControl(qWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(qtmp)) {
					
					txtQ.setBackground(ColorService.GREEN);
					pWarning.setText("Attention: p is missing");
					showControl(pWarning);
				}
				else {
					txtQ.setBackground(ColorService.RED);
					qWarning.setText("Attention: q is not a suitable prime");
					pWarning.setText("Attention: p is missing");
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
					pWarning.setText("Attention: only numbers > 0 are allowed");
					showControl(pWarning);
					return;
				}
				
				txtQ.setBackground(ColorService.RED);
				qWarning.setText("q is missing");
				showControl(qWarning);
				ptmp = new BigInteger(pAsStr);
				
				
				if(ptmp.compareTo(limitUp) > 0) {
					txtP.setBackground(ColorService.RED);
					pWarning.setText("Attention: "
						+ "only an upper limit of 2^" + limitExp + " is allowed");
					showControl(pWarning);
					return;
				}
				
				if(rabinFirst.isSuitablePrime(ptmp)) {
					txtP.setBackground(ColorService.GREEN);
					qWarning.setText("Attention: q is missing");
					showControl(qWarning);
				}
				else {
					txtP.setBackground(ColorService.RED);
					qWarning.setText("Attention: q is missing");
					pWarning.setText("Attention: "
							+ "p is not a suitable prime");
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
						pWarning.setText("Attention: only numbers > 0 are allowed");
						qWarning.setText("Attention: only numbers > 0 are allowed");
						showControl(pWarning);
						showControl(qWarning);
					}
					if(!pAsStr.matches(pattern) && qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.WHITE);
						pWarning.setText("Attention: only numbers > 0 are allowed");
						showControl(pWarning);
					}
					if(pAsStr.matches(pattern) && !qAsStr.matches(pattern)) {
						txtP.setBackground(ColorService.WHITE);
						txtQ.setBackground(ColorService.RED);
						qWarning.setText("Attention: only numbers > 0 are allowed");
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
						pWarning.setText("Attention: "
						+ "only an upper limit of 2^" + limitExp + " is allowed");
						qWarning.setText("Attention: "
								+ "only an upper limit of 2^" + limitExp + " is allowed");
						showControl(pWarning);
						showControl(qWarning);
					}
					
					if(!(ptmp.compareTo(limitUp) <= 0) && (qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.GREEN);
						pWarning.setText("Attention: "
						+ "only an upper limit of 2^" + limitExp + " is allowed");
						showControl(pWarning);
					}
					
					if((ptmp.compareTo(limitUp) <= 0) && !(qtmp.compareTo(limitUp) <= 0)) {
						txtP.setBackground(ColorService.GREEN);
						txtQ.setBackground(ColorService.RED);
						qWarning.setText("Attention: "
						+ "only an upper limit of 2^" + limitExp + " is allowed");
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
							nWarning.setText("Attention: "
									+ "N = p \u2219 q < 256");
							showControl(nWarning);
						}
					}
					else {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						nWarning.setText("Attention: the condition p \u2260 q must be satisfied");
						showControl(nWarning);
					}
				}
				else if(rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					
					txtP.setBackground(ColorService.GREEN);
					txtQ.setBackground(ColorService.RED);
					qWarning.setText("Attention: q is not a suitable prime");
					showControl(qWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && rabinFirst.isSuitablePrime(qtmp)) {
					
					cmbP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.GREEN);
					pWarning.setText("Attention: p is not a suitable prime");
					showControl(pWarning);
				}
				else if(!rabinFirst.isSuitablePrime(ptmp) && !rabinFirst.isSuitablePrime(qtmp)) {
					cmbP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.RED);
					pWarning.setText("Attention: p is not suitable prime");
					qWarning.setText("Attention: q is not suitable prime");
					showControl(pWarning);
					showControl(qWarning);
				}
			}
			

		}*/

	
	
	
	public static void main(String[] args) {
		A a = new A();
		a.test();
	}
	
}



