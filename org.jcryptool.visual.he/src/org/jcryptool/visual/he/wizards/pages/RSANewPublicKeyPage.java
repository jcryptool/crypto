// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2017 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.he.wizards.pages;

import static org.jcryptool.visual.library.Lib.isPrime;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.visual.he.Messages;
import org.jcryptool.visual.he.rsa.RSAData;
import org.jcryptool.visual.library.BigSquareRoot;
import org.jcryptool.visual.library.Constants;

/**
 * Wizardpage for entering the parameters of a new Public Key.
 * 
 * @author Michael Gaber
 */
public class RSANewPublicKeyPage extends WizardPage implements ModifyListener, VerifyListener {

    /**
     * Runnable for setting the calculated N in {@link RSANewPublicKeyPage#modifyText(ModifyEvent)}
     * 
     * @author Michael Gaber
     */
    private class CalcRunnable implements Runnable {

        /** the N to set. */
        private BigInteger n;

        public void run() {
            calcNField.setText(n.toString());
        }

        /**
         * @param n the n to set
         */
        public void setN(BigInteger n) {
            this.n = n;
        }
    }

    /** unique pagename to get this page from inside a wizard. */
    private static final String PAGENAME = "New Public Key Page"; //$NON-NLS-1$

    /** title of this page, displayed in the head of the wizard. */
    private static final String TITLE = Messages.RSANewPublicKeyPage_choose_params;

    /** {@link CalcRunnable} for using later. */
    private final CalcRunnable calcRunnable = new CalcRunnable();

    /**
     * getter for the pagename constant for easy access.
     * 
     * @return the pagename
     */
    public static String getPagename() {
        return PAGENAME;
    }

    /** data-object storing all relevant information about the algorithm. */
    private final RSAData data;

    /** field for entering the encryption-exponent. */
    private Text eField;

    /** field for entering the RSA-modulo. */
    private Text nField;

    /** selection whether this key should be saved. */
    private Button saveButton;

    /** Field for the calculated replacement-n. */
    private Text calcNField;

    /**
     * Constructor for a new wizardpage getting the data object.
     * 
     * @param data the data object
     */
    public RSANewPublicKeyPage(RSAData data) {
        super(PAGENAME, TITLE, null);
        this.data = data;
        this.setDescription(Messages.RSANewPublicKeyPage_enter_params_text);
        setPageComplete(false);
    }

    /**
     * Set up the UI stuff.
     * 
     * @param parent the parent composite
     */
    public final void createControl(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        // do stuff like layout et al
        int ncol = 6;
        GridLayout gl = new GridLayout(ncol, false);
        composite.setLayout(gl);
        GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false, ncol, 1);
        GridData gd1 = new GridData(SWT.FILL, SWT.CENTER, true, false, ncol, 1);
        GridData gd2 = new GridData(SWT.FILL, SWT.CENTER, true, false, ncol / 2 + 1, 1);
        GridData gd3 = new GridData(SWT.FILL, SWT.CENTER, true, false, ncol / 2 - 1, 1);

        Label label = new Label(composite, SWT.NONE);
        label.setLayoutData(gd2);
        label.setText(Messages.RSANewPublicKeyPage_choose_rsa_mod);

        label = new Label(composite, SWT.NONE);
        label.setLayoutData(gd3);
        label.setText(Messages.RSANewPublicKeyPage_suggestion);

        // field for entering N
        nField = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
        nField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        nField.addModifyListener(this);
        nField.addVerifyListener(this);
        nField.setFocus();

        // new label for <-
        label = new Label(composite, SWT.CENTER);
        label.setText("<-");
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // button for moving the value from calcN to N
        final Button moveButton = new Button(composite, SWT.PUSH);
        moveButton.setText(Messages.RSANewPublicKeyPage_use);
        moveButton.setToolTipText(Messages.RSANewPublicKeyPage_use_popup);
        moveButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
        moveButton.setEnabled(true);
        moveButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                nField.setText(calcNField.getText());
            }
        });

        // new label for <-
        label = new Label(composite, SWT.CENTER);
        label.setText("<-");
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // field for calculated n value
        calcNField = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER | SWT.READ_ONLY);
        calcNField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        calcNField.setText("323");
        calcNField.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                moveButton.setEnabled(!((Text) e.widget).getText().equals("")); //$NON-NLS-1$
            }
        });

        // new label for <-
        label = new Label(composite, SWT.CENTER);
        label.setText("");
        label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        // label
        label = new Label(composite, SWT.NONE);
        label.setLayoutData(gd);
        label.setText(Messages.RSANewPublicKeyPage_enter_e);

        // field for entering e
        eField = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
        eField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
        eField.addModifyListener(this);
        eField.addVerifyListener(this);

        // Separator
        new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(gd1);

        // should this key be saved?
        saveButton = new Button(composite, SWT.CHECK);
        saveButton.setText(Messages.RSANewPublicKeyPage_save_pubkey);
        saveButton.setToolTipText(Messages.RSANewPublicKeyPage_save_pubkey_popup);
        saveButton.setSelection(data.isStandalone());
        saveButton.setEnabled(!data.isStandalone());
        saveButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
                // won't be called
            }

            public void widgetSelected(SelectionEvent e) {
                getContainer().updateButtons();
            }
        });

        // fill in old data
        if (data.getE() != null) {
            eField.setText(data.getE().toString());
        }
        if (data.getN() != null) {
            nField.setText(data.getN().toString());
        }

        // finishing touch
        setControl(composite);
    }

    @Override
    public final IWizardPage getNextPage() {
        if (wantSave()) {
            return super.getNextPage();
        } else {
            return null;
        }
    }

    public final void modifyText(ModifyEvent evt) {
        final BigInteger n;
        BigInteger e;
        boolean isTwoComposite = false;
        boolean tooSmall = false;
        boolean isEmpty = false;
        try {
        	isEmpty = nField.getText().isEmpty();
            n = new BigInteger(nField.getText());
            if (n.compareTo(Constants.TWOFIVESIX) <= 0) {
                tooSmall = true;
            }
            if (!isTwoComposite(n)) {
                suggestN(n);
            } else {
                isTwoComposite = true;
            }     
            if (tooSmall && isTwoComposite && !isEmpty) {
            	setErrorMessage(Messages.RSANewPublicKeyPage_error_n_lt_256);
            } else if (tooSmall && !isTwoComposite && !isEmpty) {
            	setErrorMessage(Messages.RSANewPublicKeyPage_error_n_lt_256 
            			+ " " + Messages.RSANewPublicKeyPage_error_n_not_p_by_q);
            } else if (!tooSmall && !isTwoComposite && !isEmpty) {
            	setErrorMessage(null);
            	setMessage(Messages.RSANewPublicKeyPage_error_n_not_p_by_q, (isPrime(n) ? ERROR : WARNING));
            } else {
            	setErrorMessage(null);
            	setMessage(null, WARNING);
            }
            data.setN(n);
        } catch (NumberFormatException e1) {
        	setErrorMessage(null);
        	setMessage(null, WARNING);
            setPageComplete(false);
            data.setN(null);
        }
        try {
            e = new BigInteger(eField.getText());
            data.setE(e);
            boolean eok = e.compareTo(data.getN()) < 0;
            boolean eok2 = e.compareTo(BigSquareRoot.get(data.getN()).toBigInteger().subtract(BigInteger.ONE).pow(2)) < 0;
            boolean eok3 = e.compareTo(BigInteger.ONE) >= 0;
            // check for valid e and n values (n >256, e<floor(sqrt(n)-1)^2
            setPageComplete(!tooSmall && isTwoComposite && eok && eok2 && eok3);
            if (!eok || !eok2 || !eok3) {
                String error = getErrorMessage();
                if (error == null) {
                    error = ""; //$NON-NLS-1$
                } else {
                    error += "\n"; //$NON-NLS-1$
                }
                setErrorMessage(error + Messages.RSANewPublicKeyPage_error_e_lt_1);
            }
        } catch (NumberFormatException e1) {
            setPageComplete(false);
        } catch (NullPointerException e2) {
            setPageComplete(false);
        }
    }

    /**
     * @param n
     */
    private void suggestN(final BigInteger n) {
        new Thread() {
            @Override
            public void run() {
            	BigInteger possibleN = BigInteger.ZERO;
            	BigDecimal root = BigSquareRoot.get(n);
            	BigInteger possibleP = root.toBigInteger().nextProbablePrime();
            	BigInteger possibleQ = possibleP.add(BigInteger.ONE).nextProbablePrime();
            	while (possibleN.compareTo(Constants.TWOFIVESIX) < 0) {
                 	possibleQ = possibleQ.add(BigInteger.ONE).nextProbablePrime();
                	possibleN = possibleP.multiply(possibleQ);
            	}
            	calcRunnable.setN(possibleN);
            	Display d = Display.getDefault();
            	d.asyncExec(calcRunnable);
            }
        }.start();
    }

    /**
     * checks whether a number is a composite of exactly two prime numbers and is not a square.
     * 
     * @param number the number to check
     * @return <code>true</code> if and only if the number is
     *         <ol>
     *         <li>a composite of two prime factors</li>
     *         <li>these two prime factors are different</li>
     *         </ol>
     */
    private boolean isTwoComposite(BigInteger number) {
        if (isPrime(number)) {
            return false;
        }
        if (!number.testBit(0)) {
            return isPrime(number.divide(new BigInteger("2"))); //$NON-NLS-1$
        }
        BigInteger j;
        BigInteger[] k;
        for (int i = 3; i <= BigSquareRoot.get(number).intValue(); i += 2) {
            j = new BigInteger("" + i); //$NON-NLS-1$
            k = number.divideAndRemainder(j);
            if (k[1].equals(BigInteger.ZERO)) {
                return !j.equals(k[0]) && isPrime(j) && isPrime(k[0]);
            }
        }
        return false;
    }

    public final void verifyText(VerifyEvent e) {
        switch (e.keyCode) {
            case SWT.DEL:
            case SWT.BS:
                return;
            default:
                break;
        }
        // allow only digits and spaces as input
        if (!(e.text).matches(TextWizardPage.DIGIT)) {
            e.doit = false;
        }
    }

    /**
     * getter for the status of the save button to be accessed externally.
     * 
     * @return whether the user wants to save the key
     */
    public final boolean wantSave() {
        return saveButton.getSelection();
    }
}
