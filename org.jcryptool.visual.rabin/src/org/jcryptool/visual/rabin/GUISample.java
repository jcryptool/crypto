package org.jcryptool.visual.rabin;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class GUISample extends Shell {


	public GUISample(Display display) {
		super(display);	
		this.setLayout(new GridLayout(1, false));
		
		Composite compMain = new Composite(this, SWT.BORDER);
		compMain.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		compMain.setLayout(new GridLayout(3, false));
		
		Group grpPlaintext = new Group(compMain, SWT.NONE);
		grpPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		grpPlaintext.setLayout(new GridLayout(1, false));
		((GridData) grpPlaintext.getLayoutData()).heightHint = 200;
		grpPlaintext.setText("Plaintext");
		
		Text txtPlaintext = new Text(grpPlaintext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		txtPlaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite compHoldButtons = new Composite(compMain, SWT.BORDER);
		compHoldButtons.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, true));
		compHoldButtons.setLayout(new GridLayout(1, false));
		
		Button btnEncrypt = new Button(compHoldButtons, SWT.PUSH);
		btnEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		btnEncrypt.setText("Encrypt");
		
		Button btnDecrypt = new Button(compHoldButtons, SWT.PUSH);
		btnDecrypt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		btnDecrypt.setText("Decrypt");
		
		Group grpCiphertext = new Group(compMain, SWT.NONE);
		grpCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		grpCiphertext.setLayout(new GridLayout(1, false));
		((GridData) grpCiphertext.getLayoutData()).heightHint = 200;
		grpCiphertext.setText("Ciphertext");
		
		Text txtCiphertext = new Text(grpCiphertext, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
		txtCiphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
	
	protected void createContents() {
		setText("SWT Application");
		//setSize(300, 200);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private static String byteToString(byte b) {
		String hexString = Integer.toString(Byte.toUnsignedInt(b), 16);
		return hexString.length() == 1 ? "0" + hexString : hexString;
	}

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Display display = Display.getDefault();
		GUISample shell = new GUISample(display);
		shell.open();
		shell.layout();
		//shell.pack();
		
		int a = 120;
		String b = byteToString((byte) a);
		System.out.println(b);
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
