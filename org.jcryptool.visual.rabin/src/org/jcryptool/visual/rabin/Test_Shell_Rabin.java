//package org.jcryptool.visual.rabin;
//
//import java.io.UnsupportedEncodingException;
//import java.math.BigInteger;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.LinkedList;
//import java.util.stream.Collectors;
//
//import javax.swing.text.html.HTMLDocument.HTMLReader.CharacterAction;
//
//import org.eclipse.swt.SWT;
//import org.eclipse.swt.widgets.Display;
//import org.eclipse.swt.widgets.Shell;
//import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.custom.ScrolledComposite;
//import org.eclipse.swt.layout.GridLayout;
//import org.eclipse.swt.widgets.Label;
//import org.eclipse.swt.layout.GridData;
//import org.eclipse.swt.widgets.Button;
//import org.eclipse.swt.widgets.Text;
//import org.eclipse.swt.events.SelectionAdapter;
//import org.eclipse.swt.events.SelectionEvent;
//import org.eclipse.swt.widgets.List;
//import org.eclipse.swt.widgets.Combo;
//
//public class Test_Shell_Rabin extends Shell {
//	private Text plaintext;
//	private Text cyphertext;
//	private Text pt2;
//	private BigInteger m;
//	private BigInteger c;
//	private String pt;
//	
//	private static String byteToString(byte b) {
//		String hexString = Integer.toString(Byte.toUnsignedInt(b), 16);
//		return hexString.length() == 1 ? "0"+hexString : hexString;
//	}
//
//	public static String bytesToString(byte[] content) {
//		LinkedList<String> result = new LinkedList<String>();
//		for (byte b : content) {
//			String byteToString = byteToString(b);
//			result.add(byteToString);
//		}
//		String collect = result.stream().collect(Collectors.joining(""));
//		return collect;
//	}
//	/**
//	 * Launch the application.
//	 * @param args
//	 */
//	public static void main(String args[]) {
//		Display display = Display.getDefault();
//		Test_Shell_Rabin shell = new Test_Shell_Rabin(display);
//		shell.open();
//		shell.layout();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
//
//	}
//
//	/**
//	 * Create the shell.
//	 * @param display
//	 */
//	public Test_Shell_Rabin(Display display) {
//		super(display, SWT.SHELL_TRIM);
//		setLayout(new GridLayout(6, false));
//		
//		Composite composite = new Composite(this, SWT.NONE);
//		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		composite.setLayout(new GridLayout(1, false));
//		
//		plaintext = new Text(composite, SWT.BORDER | SWT.MULTI);
//		plaintext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		
//		BigInteger[] keys = Rabin.generateKey(2048);
//		BigInteger n = keys[0];
//		BigInteger p = keys[1];
//		BigInteger q = keys[2];
//		
//		//Combo combo = new Combo(this, SWT.NONE);
//		//combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
//		//BigInteger m = null;
//		//BigInteger c = null;
//		
//		Button encrypt = new Button(this, SWT.NONE);
//		encrypt.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				//String text = plaintext.getText();
//				pt = plaintext.getText();
//				//System.out.println(Charset.defaultCharset().displayName());
//				//byte[] encoded_input = pt.getBytes(StandardCharsets.UTF_8);
//				//System.out.println(new String(encoded_input, StandardCharsets.UTF_8));
//				//for(byte b : encoded_input) {
//					//System.out.print(b + " ");
//				//}
//				//Character first = text.charAt(0);
//				//byte[] first_bytes = first.toString().getBytes();
//				//String first_as_str = first.toString();
//				//byte[] first_bytes = first_as_str.getBytes();
//				/*try {
//					first_bytes = first.toString().getBytes("UTF8");
//				} catch (UnsupportedEncodingException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}*/
//				//cyphertext.setText("0x" + Integer.toHexString(first_bytes[0]));
//				//cyphertext.setText(HexEditorDebugLogic.bytesToString(first_bytes));
//				//new String(first_bytes, Charset.forName("utf-8"));
//				//cyphertext.setText(new String(first_bytes, Charset.forName("utf-8")));
//				//int ascii = (int) first;
//				//Rabin rabin = new Rabin();
//				//cyphertext.setText(Integer.Byte.toUnsignedInt(first_bytes[0]) + "");
//				//cyphertext.setText(bytesToString(first_bytes));
//				//String pt = plaintext.getText();
//				/*
//				 * BigInteger[] keys = Rabin.generateKey(); BigInteger n = keys[0]; BigInteger p
//				 * = keys[1]; BigInteger q = keys[2];
//				 */
//				System.out.println(bytesToString(pt.getBytes()));
//				//m = new BigInteger(pt.getBytes());
//				m = new BigInteger(bytesToString(pt.getBytes()), 16);
//				System.out.println(m.toString(16));
//				//BigInteger m2 = new BigInteger("c3bc", 16);
//				//System.out.println(m2.toString(16));
//				c = Rabin.encrypt(m, n);
//				cyphertext.setText(c.toString());
//				
//			}
//			
//		});
//		encrypt.setText("encrypt");
//	
//		Composite composite_1 = new Composite(this, SWT.NONE);
//		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		composite_1.setLayout(new GridLayout(1, false));
//		
//		cyphertext = new Text(composite_1, SWT.BORDER | SWT.MULTI);
//		cyphertext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		
//		Button decrypt = new Button(this, SWT.NONE);
//	
//		
//			
//		decrypt.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				String finalmessage = null;
//				BigInteger[] m1 = Rabin.decrypt(c);
//				for (int i = 0; i < m1.length; i++) {
//					BigInteger b = m1[i];
//					//String dec = new String(b.toString(16).getBytes(), Charset.defaultCharset());
//					//String dec = new String(b.toByteArray());
//					//if(dec.equals(plaintext.getText())) {
//						//finalmessage = dec;
//					//}
//					//if(b.equals(m)) {
//						//finalmessage = pt;
//					//}
//					byte[] b_as_byte = b.toByteArray();
//					
//					if(b_as_byte[0] == 0) {
//					    byte[] tmp = new byte[b_as_byte.length - 1];
//					    System.arraycopy(b_as_byte, 1, tmp, 0, tmp.length);
//					    b_as_byte = tmp;
//					}
//					
//					String b_to_string = bytesToString(b_as_byte);
//					System.out.println(i + ": " + b_to_string);
//					 String dec = new String(b_as_byte, StandardCharsets.UTF_8);
//				     if (dec.equals(pt)) {
//				    	 finalmessage = dec;
//				     }
//				}
//				pt2.setText(finalmessage);
//			}
//		});
//		decrypt.setText("decrypt");
//		
//		Composite composite_2 = new Composite(this, SWT.NONE);
//		composite_2.setLayout(new GridLayout(1, false));
//		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		
//		pt2 = new Text(composite_2, SWT.BORDER | SWT.MULTI);
//		pt2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
//		createContents();
//	}
//
//	/**
//	 * Create contents of the shell.
//	 */
//	protected void createContents() {
//		setText("SWT Application");
//		setSize(450, 300);
//
//	}
//
//	@Override
//	protected void checkSubclass() {
//		// Disable the check that prevents subclassing of SWT components
//	}
//}
