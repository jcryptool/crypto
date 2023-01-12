package org.jcryptool.visual.rabin;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.core.util.colors.ColorService;
import org.jcryptool.crypto.ui.textloader.ui.wizard.TextLoadController;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;

public class RabinPrototyp extends Shell {
	private Group grpParam;
	private Composite paramMainComp;
	private Composite npqComp;
	private Label lblPrimeP;
	private Text txtP;
	private Label lblPrimeQ;
	private Text txtQ;
	private Label lblModN;
	private Text txtModN;
	private Composite genPComp;
	private Composite genQComp;
	private Composite compGenKeys;
	private Text txtLowLimP;
	private Text txtUpperLimP;
	private Text txtLowLimQ;
	private Text txtUpperLimQ;
	private Button btnGenKeysMan;
	private Button btnStartGenKeys;
	private BigInteger p;
	private BigInteger q;
	private Button btnGenKeys;
	private Button btnUseKeysAlgo;
	private BigInteger n;

	
	
	private boolean isSuitablePrime(BigInteger a) {
		return a.isProbablePrime(1000) 
				&& a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3));		
	}
	
	private VerifyListener vlNumbers = new VerifyListener() {
		
		@Override
		public void verifyText(VerifyEvent e) {
			// TODO Auto-generated method stub
			
			Text txt = null;
			
			if(e.getSource() instanceof Text) {
				txt = (Text) e.getSource();
			}
			
			boolean doit = true;
			
			if((txt.getText().length() == 0 && e.text.compareTo("0") == 0)
					|| !(e.text.matches("\\d"))
					//	|| txt.getText().length() == 10
					|| txt == null)
				doit = false;
			
			if(e.character == '\b')
				doit = true;

			e.doit = doit;
			
		}
	};
	
	
	
	private void updateLimitFields() {
		String txtLowP = txtLowLimP.getText();
		String txtUpP = txtUpperLimP.getText();
		String txtLowQ = txtLowLimQ.getText();
		String txtUpQ = txtUpperLimQ.getText();
		String pattern = "^([1-9]\\d*|2\\^\\d+)$";
		
		if(btnGenKeys.getSelection()) 
			btnStartGenKeys.setEnabled(false);
		
		BigInteger lowP = null;
		BigInteger upP = null;
		BigInteger lowQ = null;
		BigInteger upQ = null;
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
			txtLowLimP.setBackground(ColorService.WHITE);
			txtUpperLimP.setBackground(ColorService.WHITE);
			txtLowLimQ.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			return;
		}
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			if(!txtUpQ.matches(pattern)) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimQ.setBackground(ColorService.RED);
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimQ.setBackground(ColorService.WHITE);
			}
			
			txtUpperLimP.setBackground(ColorService.WHITE);
			txtLowLimP.setBackground(ColorService.WHITE);
			txtLowLimQ.setBackground(ColorService.WHITE);
			return;
		}
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			if(!txtLowQ.matches(pattern)) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimQ.setBackground(ColorService.RED);
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimQ.setBackground(ColorService.WHITE);
			}
			
			txtUpperLimP.setBackground(ColorService.WHITE);
			txtLowLimP.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			
			return;
		}
		
		if(txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimQ.setBackground(ColorService.WHITE);
				txtUpperLimQ.setBackground(ColorService.WHITE);
				
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				if(lowQ.compareTo(upQ) >= 0) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
			}
			txtLowLimP.setBackground(ColorService.WHITE);
			txtUpperLimP.setBackground(ColorService.WHITE);
			
			return;
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			if(!txtUpP.matches(pattern)) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimP.setBackground(ColorService.RED);
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimP.setBackground(ColorService.WHITE);
			}
			
			txtLowLimP.setBackground(ColorService.WHITE);
			txtLowLimQ.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			
			return;
			
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtUpQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimQ.setBackground(ColorService.WHITE);
				txtUpperLimP.setBackground(ColorService.WHITE);
			}
			txtLowLimP.setBackground(ColorService.WHITE);
			txtLowLimQ.setBackground(ColorService.WHITE);
			
			return;
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimQ.setBackground(ColorService.WHITE);
				txtUpperLimP.setBackground(ColorService.WHITE);
			}
			txtLowLimP.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			
			return;
		}
		
		
		if(txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			
			boolean valid = txtUpP.matches(pattern) && txtLowQ.matches(pattern)
					&& txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(txtUpP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimP.setBackground(ColorService.WHITE);
				txtLowLimQ.setBackground(ColorService.WHITE);
				txtUpperLimQ.setBackground(ColorService.WHITE);
				
				
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				if(lowQ.compareTo(upQ) >= 0) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
					txtUpperLimP.setBackground(ColorService.WHITE);
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.WHITE);
					txtUpperLimP.setBackground(ColorService.WHITE);
				}
			}
			txtLowLimP.setBackground(ColorService.WHITE);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			if(!txtLowP.matches(pattern)) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimP.setBackground(ColorService.RED);
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimP.setBackground(ColorService.WHITE);
			}
			
			txtUpperLimP.setBackground(ColorService.WHITE);
			txtLowLimQ.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			
			return;

		}
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtUpQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtLowP.matches(pattern) && txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtLowP.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtUpperLimQ.setBackground(ColorService.WHITE);
			}
			txtUpperLimP.setBackground(ColorService.WHITE);
			txtLowLimQ.setBackground(ColorService.WHITE);
			
			return;

		}
		
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern);

			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
				}
				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.WHITE);
				}
				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.RED);
				}
				
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtLowLimQ.setBackground(ColorService.WHITE);
			}
			txtUpperLimP.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			
			return;

		}
		
		
		
		if(!txtLowP.isEmpty() && txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtLowQ.matches(pattern)
					&& txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
					&& !txtUpQ.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtLowP.matches(pattern) && txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(txtLowP.matches(pattern) && !txtLowQ.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtLowP.matches(pattern) && txtLowQ.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtLowLimQ.setBackground(ColorService.WHITE);
				txtUpperLimQ.setBackground(ColorService.WHITE);

				
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				if(lowQ.compareTo(upQ) >= 0) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
					txtLowLimP.setBackground(ColorService.WHITE);
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.WHITE);
					txtLowLimP.setBackground(ColorService.WHITE);
				}
			}
			txtUpperLimP.setBackground(ColorService.WHITE);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtLowP.matches(pattern) && txtUpP.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimP.setBackground(ColorService.RED);
				}
				if(!txtLowP.matches(pattern) && txtUpP.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimP.setBackground(ColorService.WHITE);
				}
				if(txtLowP.matches(pattern) && !txtUpP.matches(pattern)) {
					txtLowLimP.setBackground(ColorService.WHITE);
					txtUpperLimP.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtUpperLimP.setBackground(ColorService.WHITE);
	
				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				
				if(lowP.compareTo(upP) >= 0) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimP.setBackground(ColorService.RED);
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimP.setBackground(ColorService.WHITE);
					txtUpperLimP.setBackground(ColorService.WHITE);
				}
			}
			txtLowLimQ.setBackground(ColorService.WHITE);
			txtUpperLimQ.setBackground(ColorService.WHITE);
			
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
					&& txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
					&& !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimP.setBackground(ColorService.WHITE);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtUpperLimQ.setBackground(ColorService.WHITE);

				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				
				if(lowP.compareTo(upP) >= 0) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimP.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimP.setBackground(ColorService.WHITE);
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.WHITE);
				}
			}
			txtLowLimQ.setBackground(ColorService.WHITE);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && txtUpQ.isEmpty()) {
			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
					&& txtLowQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
					&& !txtLowQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.RED);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimP.setBackground(ColorService.WHITE);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtLowLimQ.setBackground(ColorService.WHITE);

				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				
				if(lowP.compareTo(upP) >= 0) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimP.setBackground(ColorService.RED);
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.WHITE);
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtLowLimP.setBackground(ColorService.WHITE);
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.WHITE);
				}
			}
			txtUpperLimQ.setBackground(ColorService.WHITE);
			return;
		}
		
		
		
		if(!txtLowP.isEmpty() && !txtUpP.isEmpty()
				&& !txtLowQ.isEmpty() && !txtUpQ.isEmpty()) {
			
			boolean valid = txtUpP.matches(pattern) && txtLowP.matches(pattern)
					&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern);
			
			if(!valid) {
				txtcompGenPandQWarning.setText("Attention:\n"
						+ "only decimal numbers (1-9) and an expression of the "
						+ "form \"2^(decimal number)\" are allowed");
				showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
					&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
					txtUpperLimP.setBackground(ColorService.RED);
					txtLowLimP.setBackground(ColorService.RED);
					txtLowLimQ.setBackground(ColorService.RED);
					txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(!txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(txtUpP.matches(pattern) && !txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& !txtLowQ.matches(pattern) && txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.WHITE);
				}
				if(txtUpP.matches(pattern) && txtLowP.matches(pattern)
						&& txtLowQ.matches(pattern) && !txtUpQ.matches(pattern)) {
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.RED);
				}
			}
			else {
				hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				txtUpperLimP.setBackground(ColorService.WHITE);
				txtLowLimP.setBackground(ColorService.WHITE);
				txtLowLimQ.setBackground(ColorService.WHITE);
				txtUpperLimQ.setBackground(ColorService.WHITE);

				
				lowP = getNumFromLimit(txtLowP);
				upP = getNumFromLimit(txtUpP);
				lowQ = getNumFromLimit(txtLowQ);
				upQ = getNumFromLimit(txtUpQ);
				
				boolean validCondAll = lowP.compareTo(upP) < 0 && lowQ.compareTo(upQ) < 0;
				
				if(!validCondAll) {
					txtcompGenPandQWarning.setText("Attention: "
							+ "the condition lower limit < upper limit must be "
							+ "satisfied");
					showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
				
					
					if(!(lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
						txtLowLimP.setBackground(ColorService.RED);
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
					}
					if(!(lowP.compareTo(upP) < 0) && (lowQ.compareTo(upQ) < 0)) {
						txtLowLimP.setBackground(ColorService.RED);
						txtUpperLimP.setBackground(ColorService.RED);
						txtLowLimQ.setBackground(ColorService.WHITE);
						txtUpperLimQ.setBackground(ColorService.WHITE);
					}
					if((lowP.compareTo(upP) < 0) && !(lowQ.compareTo(upQ) < 0)) {
						txtLowLimP.setBackground(ColorService.WHITE);
						txtUpperLimP.setBackground(ColorService.WHITE);
						txtLowLimQ.setBackground(ColorService.RED);
						txtUpperLimQ.setBackground(ColorService.RED);
					}
				}
				else {
					hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
					txtUpperLimP.setBackground(ColorService.WHITE);
					txtLowLimP.setBackground(ColorService.WHITE);
					txtLowLimQ.setBackground(ColorService.WHITE);
					txtUpperLimQ.setBackground(ColorService.WHITE);

					
					if(btnGenKeys.getSelection()) {
						btnStartGenKeys.setEnabled(true);
					}
				}
				
				
				
			}
			
		}
	}
	
	
	
	
	
	
	
	
	private ModifyListener mlLimit = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {

			updateLimitFields();
			
		}
	};
	
	
	
	
	
	
	private Text txtWarningNpq;
	private GridData txtWarningNpqData;
	private Text txtcompGenPandQWarning;
	private GridData txtcompGenPandQWarningData;
	
	
	
	private boolean isAppropriatePrime(BigInteger p, BigInteger lowLim, BigInteger upLim) {
		return p.compareTo(lowLim) >= 0 
				&& p.compareTo(upLim) <= 0 
				&& isSuitablePrime(p);				
	}
	
	private boolean isCompositeSuitable(BigInteger a, BigInteger b) {
		return a.multiply(b).compareTo(BigInteger.valueOf(256)) > 0;
	}
	
	private void btnGenKeysManAction() {
		
		String pAsStr = txtP.getText();
		String qAsStr = txtQ.getText();
		p = new BigInteger(pAsStr);
		q = new BigInteger(qAsStr);
		n = p.multiply(q);
		txtModN.setText(n.toString());
	}
	
	
	private void hideTextWarningNum(Text textToHide, GridData gd, Composite parent) {
		gd.exclude = true;
		textToHide.setVisible(false);
		parent.requestLayout();
		
		sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.requestLayout();
	}
	
		
	private void showTextWarningNum(Text textToShow, GridData gd, Composite parent) {
		gd.exclude = false;
		textToShow.setVisible(true);
		parent.requestLayout();
		sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	

	private void setSizeWarningText(Text txt, GridData gd, int minWidth, int minHeigth) {
		gd.minimumWidth = minWidth;
		gd.minimumHeight = minHeigth;
		gd.widthHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
	}
	
	private void setSizeInfoText(Text txt, GridData gd, int minWidth, int minHeigth) {
		gd.minimumWidth = minWidth;
		gd.minimumHeight = minHeigth;
		gd.widthHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		gd.heightHint = txt.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
	}
	
	
	private void updateTextfields(TypedEvent e) {
		
		BigInteger ptmp = null;
		BigInteger qtmp = null;
		
		if(btnGenKeysMan.getSelection())
			btnStartGenKeys.setEnabled(false);
		
		String pAsStr = txtP.getText();
		String qAsStr = txtQ.getText();
		
		if(pAsStr.isEmpty() && qAsStr.isEmpty()) {
			
			txtP.setBackground(ColorService.WHITE);
			txtQ.setBackground(ColorService.WHITE);
			hideTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			return;
		}
		
		if(pAsStr.isEmpty() && !qAsStr.isEmpty()) {
			txtP.setBackground(ColorService.RED);
			qtmp = new BigInteger(qAsStr);
			if(isSuitablePrime(qtmp)) {
				txtQ.setBackground(ColorService.GREEN);
				txtWarningNpq.setText("Attention: p is missing");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
			else {
				txtQ.setBackground(ColorService.RED);
				txtWarningNpq.setText("Attention:\n"
						+ "1) p is missing\n"
						+ "2) q is not a suitable prime");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
			return;
		}
		
		if(!pAsStr.isEmpty() && qAsStr.isEmpty()) {
			txtQ.setBackground(ColorService.RED);
			ptmp = new BigInteger(pAsStr);
			if(isSuitablePrime(ptmp)) {
				txtP.setBackground(ColorService.GREEN);
				txtWarningNpq.setText("Attention: q is missing");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
			else {
				txtP.setBackground(ColorService.RED);
				txtWarningNpq.setText("Attention:\n"
						+ "1) q is missing\n"
						+ "2) p is not a suitable prime");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
			return;
		}
		
		if(!pAsStr.isEmpty() && !qAsStr.isEmpty()) {
			ptmp = new BigInteger(pAsStr);
			qtmp = new BigInteger(qAsStr);
			
			if(isSuitablePrime(ptmp) && isSuitablePrime(qtmp)) {
				
				if(!ptmp.equals(qtmp)) {
					if(isCompositeSuitable(ptmp, qtmp)) {
						txtP.setBackground(ColorService.GREEN);
						txtQ.setBackground(ColorService.GREEN);
						hideTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
						if(btnGenKeysMan.getSelection()) {
							btnStartGenKeys.setEnabled(true);
						}
						/*else {
							txtWarningNpq.setText("Attention: select"
									+ " \"Generate private and public key manually\"");
							showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
						}*/
					}
					else {
						txtP.setBackground(ColorService.RED);
						txtQ.setBackground(ColorService.RED);
						txtWarningNpq.setText("Attention: "
								+ "N = p \u2219 q < 256");
						showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
					}
				}
				else {
					txtP.setBackground(ColorService.RED);
					txtQ.setBackground(ColorService.RED);
					txtWarningNpq.setText("Attention: the condition p \u2260 q must be satisfied");
					showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
				}
			}
			else if(isSuitablePrime(ptmp) && !isSuitablePrime(qtmp)) {
				txtP.setBackground(ColorService.GREEN);
				txtQ.setBackground(ColorService.RED);
				txtWarningNpq.setText("Attention: q is not a suitable prime");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
			else if(!isSuitablePrime(ptmp) && isSuitablePrime(qtmp)) {
				txtP.setBackground(ColorService.RED);
				txtQ.setBackground(ColorService.GREEN);
				txtWarningNpq.setText("Attention: p is not a suitable prime");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
			else if(!isSuitablePrime(ptmp) && !isSuitablePrime(qtmp)) {
				txtP.setBackground(ColorService.RED);
				txtQ.setBackground(ColorService.RED);
				txtWarningNpq.setText("Attention: p and q are not suitable primes");
				showTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
			}
		}
		
	}
	
	
	
	
	private BigInteger getParsedNumFromLimit(String limit) {
		BigInteger ret = null;
		String pattern = "\\^";
		
		String[] parsedNum = limit.split(pattern);
		BigInteger base = new BigInteger(parsedNum[0]);
		int exp = Integer.parseInt(parsedNum[1]);
		
		ret = base.pow(exp);
		
		return ret;
		
	}
	
	private BigInteger getNumFromLimit(String limit) {
		String pattern = "^([1-9]\\d*)$";
		BigInteger ret = null;
		
		if(limit.matches(pattern)) {
			ret = new BigInteger(limit);
			return ret;
		}
		
		ret = getParsedNumFromLimit(limit);
		return ret;
	}
	
	private ModifyListener mlpq = new ModifyListener() {
		
		@Override
		public void modifyText(ModifyEvent e) {
			updateTextfields(e);
			
		}
	};
	private ScrolledComposite sc;
	private Composite rootComposite;
	
	
	private void generateKeysWithLimit(String strLowP, String strUpP, String strLowQ, String strUpQ, int iterations) {
		
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
			
		}while(!(isAppropriatePrime(primeP, lowP, upP)
					&& isAppropriatePrime(primeQ, lowQ, upQ)
					&& !primeP.equals(primeQ)
					&& isCompositeSuitable(primeP, primeQ)
					) && iterations > 0);
		
		
		if(iterations == 0) {
			txtcompGenPandQWarning.setText("Attention: "
					+ "could not find appropriate primes p and q.\n"
					+ "Please try again or use other limits for p and q");
			showTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
		}
		else {
			
			hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
			
			String strPrimeP = primeP.toString();
			String strPrimeQ = primeQ.toString();
			System.out.println("p = " + strPrimeP);
			System.out.println("q = " + strPrimeQ);
			p = new BigInteger(strPrimeP);
			q = new BigInteger(strPrimeQ);
			n = p.multiply(q);
			System.out.println("N = " + n.toString());
			
			//p = primeP;
			
			txtP.removeVerifyListener(vlNumbers);
			txtQ.removeVerifyListener(vlNumbers);
			
			txtP.setText(strPrimeP);
			txtQ.setText(strPrimeQ);
			
			
			txtP.addVerifyListener(vlNumbers);
			txtQ.addVerifyListener(vlNumbers);
			
			
			txtModN.setText(n.toString());
			
			
		}

	}
	
	
		
	
	private void createSetParamContent(Composite parent) {
		
		// create group for setting parameters
		grpParam = new Group(parent, SWT.NONE);
		grpParam.setLayout(new GridLayout(4, false));
		grpParam.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpParam.setText("Setting parameters");
		
		
		// create main composite for entering parameter settings
		paramMainComp = new Composite(grpParam, SWT.NONE);
		paramMainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		paramMainComp.setLayout(new GridLayout(2, false));
		
		// create composite for setting p, q, N manually
		npqComp = new Composite(paramMainComp, SWT.BORDER);
		npqComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		npqComp.setLayout(new GridLayout(2, false));
		
		

		
		// create label for prime p in npq composite
		lblPrimeP = new Label(npqComp, SWT.NONE);
		lblPrimeP.setText("p =");
		txtP = new Text(npqComp, SWT.BORDER);
		txtP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		txtP.addVerifyListener(vlNumbers);
		
		
		txtP.addModifyListener(mlpq);
		
		// create label for prime q in npq composite
		lblPrimeQ = new Label(npqComp, SWT.NONE);
		lblPrimeQ.setText("q =");
		txtQ = new Text(npqComp, SWT.BORDER);
		txtQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		txtQ.addVerifyListener(vlNumbers);
		
		
		txtQ.addModifyListener(mlpq);
		
		// create label for N in npq composite
		lblModN = new Label(npqComp, SWT.NONE);
		lblModN.setText("N =");
		txtModN = new Text(npqComp, SWT.READ_ONLY | SWT.BORDER);
		txtModN.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		// create text warning for npqComp
		
		txtWarningNpq = new Text(npqComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtWarningNpqData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtWarningNpq.setLayoutData(txtWarningNpqData);
		setSizeWarningText(txtWarningNpq, txtWarningNpqData, SWT.DEFAULT, SWT.DEFAULT);
		txtWarningNpq.setBackground(ColorService.LIGHTGRAY);
		txtWarningNpq.setForeground(ColorService.RED);
		hideTextWarningNum(txtWarningNpq, txtWarningNpqData, npqComp);
		

		// create composite for entering lower and upper limit for 
		// prime p
		genPComp = new Composite(paramMainComp, SWT.BORDER);
		genPComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genPComp.setLayout(new GridLayout(2, false));
		
		// create composite for entering lower and upper limit for 
		// prime q
		genQComp = new Composite(paramMainComp, SWT.BORDER);
		genQComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		genQComp.setLayout(new GridLayout(2, false));
		
		
		txtcompGenPandQWarning = new Text(paramMainComp, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		txtcompGenPandQWarningData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		txtcompGenPandQWarning.setLayoutData(txtcompGenPandQWarningData);
		setSizeWarningText(txtcompGenPandQWarning, txtcompGenPandQWarningData, SWT.DEFAULT, SWT.DEFAULT);
		txtcompGenPandQWarning.setBackground(ColorService.LIGHTGRAY);
		txtcompGenPandQWarning.setForeground(ColorService.RED);
		hideTextWarningNum(txtcompGenPandQWarning, txtcompGenPandQWarningData, paramMainComp);
		
		
		// create composite for how to generate the keys
		compGenKeys = new Composite(grpParam, SWT.NONE);
		compGenKeys.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		compGenKeys.setLayout(new GridLayout(1, false));
		
		// create radio button for generating keys manually 
		btnGenKeysMan = new Button(compGenKeys, SWT.RADIO);
		btnGenKeysMan.setText("Generate private and public key manually");
		
		
		btnGenKeysMan.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
				Button src = (Button) e.getSource();
				
				if(src.getSelection()) {
					
					updateTextfields(e);
					
				}
				
			}
		});
		
		btnGenKeys = new Button(compGenKeys, SWT.RADIO);
		btnGenKeys.setText("Generate private and public key");
		
		btnGenKeys.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				Button src = (Button) e.getSource();
				
				if(src.getSelection()) {
					
					updateLimitFields();
				}
				
			}
		});
		
		
		btnUseKeysAlgo = new Button(compGenKeys, SWT.RADIO);
		btnUseKeysAlgo.setText("Use private and public key generated in Algorithm tab");
		
		
		btnStartGenKeys = new Button(compGenKeys, SWT.PUSH);
		GridData btnStartGenData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		btnStartGenData.horizontalIndent = 5;
		btnStartGenKeys.setLayoutData(btnStartGenData);
		btnStartGenKeys.setText("Start");
		
		btnStartGenKeys.addSelectionListener(new SelectionAdapter() {		
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(btnGenKeysMan.getSelection()) {
					btnGenKeysManAction();
				}
				
				if(btnGenKeys.getSelection()) {
					
				
					String strLowP = txtLowLimP.getText();
					String strUpP = txtUpperLimP.getText();
					String strLowQ = txtLowLimQ.getText();
					String strUpQ = txtUpperLimQ.getText();
					
					generateKeysWithLimit(strLowP, strUpP, strLowQ, strUpQ, 10000);
					
				}
				
			}
			
		});
		
		
		// create label for separating info
		Label lblSepInfoSetParam = new Label(grpParam, SWT.SEPARATOR | SWT.VERTICAL);
		lblSepInfoSetParam.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true));
		
		// create info for setting parameters
		Text txtInfoSetParam = new Text(grpParam, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		GridData txtInfoSetParamData = new GridData(SWT.FILL, SWT.FILL, true, true);
		setSizeInfoText(txtInfoSetParam, txtInfoSetParamData, SWT.DEFAULT, SWT.DEFAULT);
		txtInfoSetParam.setLayoutData(txtInfoSetParamData);
		txtInfoSetParam.setText("You have three possibilities to generate the pŕivate key (p,q) and public key N:\n"
				+ "1) Enter p and q in the corresponding fields, select \"Generate private and public key manually\" and click on \"Start\".\n"
				+ "2) Enter a lower and an upper limit for both p and q. "
				+ "You are allowed to enter decimal numbers as well as numbers "
				+ "of the form \"2^(decimal number)\". Select \"Generate private and public key\" and click on \"Start\".\n"
				+ "3) Select \"Use private and public key generated in Algorithm tab\" and click on \"Start\".");
		txtInfoSetParam.setBackground(ColorService.LIGHTGRAY);
		
		
		
		// create label for prime number p
		Label lblGenP = new Label(genPComp, SWT.NONE);
		lblGenP.setText("Prime number p");
		GridData GenPdata = new GridData();
		GenPdata.horizontalSpan = 2;
		lblGenP.setLayoutData(GenPdata);
		
		// create label and textbox for lower limit prime p
		Label lblLowLimP = new Label(genPComp, SWT.NONE);
		lblLowLimP.setText("Lower limit");
		txtLowLimP = new Text(genPComp, SWT.BORDER);
		txtLowLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		txtLowLimP.addModifyListener(mlLimit);
		
		
		
		// create label and textbox for upper limit prime p
		Label lblUpperLim = new Label(genPComp, SWT.NONE);
		lblUpperLim.setText("Upper limit");
		txtUpperLimP = new Text(genPComp, SWT.BORDER);
		txtUpperLimP.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		txtUpperLimP.addModifyListener(mlLimit);
		
		
		
		// create label for prime q
		Label lblGenQ = new Label(genQComp, SWT.NONE);
		lblGenQ.setText("Prime number q");
		GridData GenQdata = new GridData();
		GenQdata.horizontalSpan = 2;
		lblGenQ.setLayoutData(GenPdata);
		
		// create label and textbox for lower limit prime q
		Label lblLowLimQ = new Label(genQComp, SWT.NONE);
		lblLowLimQ.setText("Lower limit");
		txtLowLimQ = new Text(genQComp, SWT.BORDER);
		txtLowLimQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
		
		txtLowLimQ.addModifyListener(mlLimit);
		
		
		
		// create label and textbox for upper limit prime q
		Label lblUpperLimQ = new Label(genQComp, SWT.NONE);
		lblUpperLimQ.setText("Upper limit");
		txtUpperLimQ = new Text(genQComp, SWT.BORDER);
		txtUpperLimQ.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		
	
		
		txtUpperLimQ.addModifyListener(mlLimit);	

	}

	


	


	private void createPossiblePlaintextContent(Composite parent) {
		Group grpMessages = new Group(parent, SWT.NONE);
		GridData grpMessagesData = new GridData(SWT.FILL, SWT.FILL, true, false);
		grpMessages.setLayoutData(grpMessagesData);
		grpMessages.setLayout(new GridLayout(3, false));
		grpMessages.setText("Possible messages");

		Composite compPlaintexts = new Composite(grpMessages, SWT.BORDER);
		GridData compPlaintextsData = new GridData(SWT.FILL, SWT.FILL, true, true);
		compPlaintextsData.minimumWidth = 200;
		compPlaintextsData.minimumHeight = 400;
		compPlaintextsData.widthHint = compPlaintexts.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		compPlaintextsData.heightHint = compPlaintexts.computeSize(SWT.DEFAULT, SWT.DEFAULT).y;
		
		
		compPlaintexts.setLayoutData(compPlaintextsData);
		compPlaintexts.setLayout(new GridLayout(5, false));
		
		
		
		Text[][] plaintexts = new Text[4][5];
		
		// columns
		for(int i = 1; i <= 5; i++) {
			Label lblCipherColumn = new Label(compPlaintexts, SWT.NONE);
			lblCipherColumn.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false));
			lblCipherColumn.setText("c[" + i + "]");
		}
		
		
		
		for(int i = 0; i < plaintexts.length; i++) {
			for(int j = 0; j < plaintexts[i].length; j++) {
				plaintexts[i][j] = new Text(compPlaintexts, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
				plaintexts[i][j].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			}
		}
		
		
		
		
		
		for(int i = 0; i < plaintexts.length; i++) {
			for(int j = 0; j < plaintexts[i].length; j++) {
				plaintexts[i][j].addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseDown(MouseEvent e) {
						Text src = (Text) e.getSource();
						
						src.setBackground(ColorService.BLUE);
						
						for(int i = 0; i < plaintexts.length; i++) {
							for(int j = 0; j < plaintexts[i].length; j++) {
								if(!plaintexts[i][j].equals(src)) {
									plaintexts[i][j].setBackground(ColorService.WHITE);
								}
							}
						}
					}
					
				});
			}
		}
		
		
		
		// plaintexts
		/*for(int i = 0; i < plaintexts.length; i++) {
			//plaintexts[i] = new Text(compPlaintexts, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
			plaintexts[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		}*/
		
		// create textbox for displaying content of chosen plaintexts[i]
		Text txtChosenPlain = new Text(grpMessages, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		txtChosenPlain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		txtChosenPlain.setText("this is a test");
		
	
		
		txtChosenPlain.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				Text src = (Text) e.getSource();
				System.out.println(src.getText());
				
			}
			
			
		});
		
		Button writeToEditor = new Button(grpMessages, SWT.PUSH);
		writeToEditor.setText("Write to file");

	}


	
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			RabinPrototyp shell = new RabinPrototyp(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public RabinPrototyp(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new GridLayout(1, false));
		
		sc = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		sc.setLayout(new GridLayout(1, false));
		sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		rootComposite = new Composite(sc, SWT.BORDER);
		rootComposite.setLayout(new GridLayout(1, false));
		rootComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sc.setContent(rootComposite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		

		
		createSetParamContent(rootComposite);
		
		createPossiblePlaintextContent(rootComposite);
		
		sc.setMinSize(rootComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
