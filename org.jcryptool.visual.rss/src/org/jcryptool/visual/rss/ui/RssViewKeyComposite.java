package org.jcryptool.visual.rss.ui;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.jcryptool.visual.rss.Activator;
import org.jcryptool.visual.rss.Descriptions;
import org.jcryptool.visual.rss.algorithm.KeyInformation;
import org.jcryptool.visual.rss.algorithm.RssAlgorithmController;

import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GLRSSPublicKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.grss.GSRSSPublicKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPrivateKey;
import de.unipassau.wolfgangpopp.xmlrss.wpprovider.mersa.MersaPublicKey;

/**
 * The composite with button, text fields etc. with the function to show the set key.
 *  
 * @author Leon Shell, Lukas Krodinger
 */
public class RssViewKeyComposite extends RssRightSideComposite {
	
	private final Button saveKeyButton;
	
	private Table table;
	private TableColumn column_parameter;
	private TableColumn column_value;
	

    public RssViewKeyComposite(RssBodyComposite body, final RssAlgorithmController rac) {
        super(body, SWT.NONE);
        prepareGroupView(this, Descriptions.ViewKey);
        prepareAboutComposite();

        Composite inner = new Composite(leftComposite, SWT.NONE);
        inner.setLayout(new GridLayout());
        inner.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

        KeyInformation info = rac.getInformation();
        
        table = new Table(inner, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_table.widthHint = 400;
		gd_table.heightHint = 300;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		column_parameter = new TableColumn(table, SWT.NONE);
		column_parameter.setText(Descriptions.Parameter);

		column_value = new TableColumn(table, SWT.NONE);
		column_value.setText(Descriptions.Value);
		
		if (info.getScheme() != null) {
			TableItem t1 = new TableItem(table, SWT.NONE);
			t1.setText(new String[] { Descriptions.KeyType, info.getScheme().getAlgorithmType().toString()});
		}
		
		if (info.getKeyLength() != null) {
			TableItem t2 = new TableItem(table, SWT.NONE);
			t2.setText(new String[] { Descriptions.KeyLength, info.getKeyLength().getKl() + ""});
		}
		 
        if (info.getKeyPair() != null) {          
            KeyPair keyPair = info.getKeyPair();
            
            if(keyPair.getPublic() instanceof MersaPublicKey) {
            	MersaPublicKey publicKey = (MersaPublicKey) keyPair.getPublic();
            	MersaPrivateKey privateKey = (MersaPrivateKey) keyPair.getPrivate();
                 	
              	for(int i = 0; i < privateKey.getNumberOfExponents(); i++) {
              		TableItem ts = new TableItem(table, SWT.NONE);
              		ts.setText(new String[] { "Private Exponent " + i, privateKey.getSecretExponents().get(i).toString()});
            	}
            	
            	for(int i = 0; i < publicKey.getNumberOfExponents(); i++) {
            		TableItem tp = new TableItem(table, SWT.NONE);
            		tp.setText(new String[] { "Public Exponent " + i, publicKey.getPublicExponents().get(i).toString()});
            	} 
        		
            } else {
            	TableItem t3 = new TableItem(table, SWT.NONE);
        		t3.setText(new String[] { Descriptions.PrivateKey, getPrivateKeyAsString(keyPair)});

          		TableItem t4 = new TableItem(table, SWT.NONE);
        		t4.setText(new String[] { Descriptions.PublicKey, getPublicKeyAsString(keyPair)});
            }
        }
        
        column_parameter.pack();
        column_value.pack();
        
        // For copying to clipboard
        table.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// copy the selected value to the clipboard
				String selectedValue = table.getItem(table.getSelectionIndex()).getText(1);
				final Clipboard cb = new Clipboard(inner.getDisplay());
				TextTransfer textTransfer = TextTransfer.getInstance();
				cb.setContents(new Object[] { selectedValue }, new Transfer[] { textTransfer });
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

        
        // Single row for save and load button
        Group saveLoad = new Group(leftComposite, SWT.NONE);
        saveLoad.setText(Descriptions.LoadSaveKey);
        saveLoad.setLayout(new RowLayout(SWT.HORIZONTAL));       
        
        // Button to save key
        saveKeyButton = new Button(saveLoad, SWT.PUSH);
        saveKeyButton.setText(Descriptions.Save);
        saveKeyButton.setImage(Activator.getImageDescriptor("icons/outline_file_upload_black_24dp.png").createImage(true));
        saveKeyButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
            	
            	// Open a dialog to get location for key file storage.
				FileDialog storeDialog = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
				storeDialog.setFilterExtensions(new String[] { "*.xml", "*" });
				storeDialog.setFilterNames(new String[] { "XML Files", "All Files (*)" });
				storeDialog.setFileName("key.xml");
				storeDialog.setOverwrite(true);
				String storePath = storeDialog.open();
            	
				// storePath might be null in case the dialog was closed
				if(storePath != null && !storePath.equals("")) {
					
					// Save the key 
					try {
						rac.saveKey(storePath);
					} catch (FileNotFoundException e1) {
						showErrorDialog(Descriptions.FailedToStoreKey, Descriptions.ErrorStoringKey);			
					}            
				}
            }
        });
        

        /*
         * KeyInformation info = rac.getInformation();
        if (info.getAlgorithmType() != null) {
            Label l = new Label(inner, SWT.NONE);
            l.setText(Descriptions.KeyType + ": ");
            Label l2 = new Label(inner, SWT.NONE);
            l2.setText(info.getAlgorithmType().toString());
        }
        if (info.getKeyLength() != null) {
            Label l2 = new Label(inner, SWT.NONE);
            l2.setText(Descriptions.KeyLength + ": ");
            Label l = new Label(inner, SWT.NONE);
            l.setText(info.getKeyLength().getKl() + "");
        }
        if (info.getKeyPair() != null) {
            Label l1 = new Label(inner, SWT.READ_ONLY);
            l1.setText(Descriptions.PrivateKey);
           
            ScrolledComposite  sc1 = new ScrolledComposite(inner, SWT.V_SCROLL);
            Text t1 = new Text(sc1, SWT.READ_ONLY | SWT.WRAP ); // | SWT.MULTI 
            t1.setSize( 300, 300 );
            sc1.setExpandHorizontal(true);
            sc1.setExpandVertical(false);
            sc1.setContent( t1 );
            sc1.setMinSize(200, 200);
            
            KeyPair keyPair = info.getKeyPair();

            String text1 = getPrivateKeyAsString(keyPair);
            t1.setText(getSplittedString(text1, 50));
            
            Label l2 = new Label(inner, SWT.READ_ONLY);
            l2.setText(Descriptions.PublicKey);
            
            ScrolledComposite sc2 = new ScrolledComposite(inner, SWT.V_SCROLL);
            Text t2 = new Text(sc2, SWT.READ_ONLY | SWT.WRAP); //| SWT.MULTI  SWT.READ_ONLY 
            t2.setSize( 300, 300 );
            sc2.setExpandHorizontal(true);
            sc2.setExpandVertical(false);
            sc2.setContent( t2 );
            sc2.setMinSize(200, 200);
            
            String text2 = getPublicKeyAsString(keyPair);
            t2.setText(getSplittedString(text2, 50));
        }
        
         */

        Label placeholder = new Label(leftComposite, SWT.SEPARATOR | SWT.HORIZONTAL); GridData gd = new GridData(SWT.FILL, SWT.CENTER, true, false); gd.minimumHeight = 5; gd.verticalIndent = 3; placeholder.setLayoutData(gd);
        Button returnButton = new Button(leftComposite, SWT.PUSH);
        returnButton.setText(Descriptions.ReturnButton);
        returnButton.setImage(Activator.getImageDescriptor("icons/outline_navigate_before_black_24dp.png").createImage(true));
        returnButton.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                switch (e.type) {
                case SWT.Selection:
                    body.returnToCurrentState();
                }
            }
        });
        
    }


	/**
	 * Gets the encoded publicKey as a human readable String.
	 * The object structure for the key is as follows:
	 * 1. The keyPair contains a publicKey
	 * 2. There are multiple options:
	 * - If the publicKey is a GLRSSPrivateKey it contains an GSRSSPublicKey.
	 * - If the publicKey is (now) a GSRSSPublicKey, it contains the dSignKey.
	 * - If the publicKey is a PSRSSPublicKey, it contains the key as BigInteger.
	 * - If the publicKey is a MersaPublicKey, it contains a list of exponents.
	 * 
	 * @param keyPair The keyPair to obtain the publicKey from.
	 * @return The encoded publicKey.
	 */
	public static String getPublicKeyAsString(KeyPair keyPair) {
		PublicKey publicKey = keyPair.getPublic();
		String publicKeyString = null;
		byte[] encodedPublicKey;
		
		if (publicKey instanceof GLRSSPublicKey) {
			GLRSSPublicKey glrssPublicKey = (GLRSSPublicKey) publicKey;
			publicKey = glrssPublicKey.getGsrssKey();
		} 
		
		if (publicKey instanceof GSRSSPublicKey) {
			GSRSSPublicKey gsrssPublicKey = (GSRSSPublicKey) publicKey;
			publicKey = gsrssPublicKey.getDSigKey();
			encodedPublicKey = publicKey.getEncoded();
			publicKeyString = new String(Base64.getEncoder().encode(encodedPublicKey));
		}
		
        /*if (publicKey instanceof PSRSSPublicKey) {
        	PSRSSPublicKey psrssPrivateKey = (PSRSSPublicKey) publicKey;
        	encodedPublicKey = psrssPrivateKey.getKey().toByteArray();
        }*/
        
        if (publicKey instanceof MersaPublicKey) {
        	MersaPublicKey mersaPublicKey = (MersaPublicKey) publicKey;
        	List<BigInteger> publicKeys = mersaPublicKey.getPublicExponents();
        	
        	StringBuilder sb = new StringBuilder();
        	for(int i = 0; i < publicKeys.size(); i++) {
        		sb.append("Exponent " + i + ":");
        		sb.append(publicKeys.get(i));
        		sb.append("\n");
        	}
        	publicKeyString = sb.toString();
        }
			   
        if (publicKeyString == null) {
        	encodedPublicKey = publicKey.getEncoded();
			publicKeyString = new String(Base64.getEncoder().encode(encodedPublicKey));
        }
        
        
        if (publicKeyString == null) {
        	throw new RuntimeException("Extracting public key not possible");
        }
        
	
		return publicKeyString;
	}
	
    /**
     * Gets the encoded privateKey as a human readable String.
     * The object structure for the key is (for whatever reason) as follows:
	 * - If the privateKey is a GLRSSPrivateKey it contains an GSRSSPrivateKey.
	 * - If the privateKey is (now) a GSRSSPrivateKey, it contains the dSignKey.
	 * - If the privateKey is a PSRSSPrivateKey, it contains the key as BigInteger.
	 * - If the privateKey is a MersaPrivateKey, it contains a list of exponents.

	 * 
     * @param keyPair The keyPair to obtain the privateKey from.
     * @return The encoded privateKey.
     */
    public static String getPrivateKeyAsString(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] encodedPrivateKey = null;
        String privateKeyAsString = null;
        
        if (privateKey instanceof GLRSSPrivateKey) {
        	GLRSSPrivateKey glrssPrivateKey = (GLRSSPrivateKey) privateKey;
        	privateKey = glrssPrivateKey.getGsrssKey();
        } 
        
        if (privateKey instanceof GSRSSPrivateKey) {
        	GSRSSPrivateKey gsrssPrivateKey = (GSRSSPrivateKey) privateKey;
        	privateKey = gsrssPrivateKey.getDSigKey();
        	encodedPrivateKey = privateKey.getEncoded();
        	privateKeyAsString = new String(Base64.getEncoder().encode(encodedPrivateKey));
        } 
        
        /*if (privateKey instanceof PSRSSPrivateKey) {
        	PSRSSPrivateKey psrssPrivateKey = (PSRSSPrivateKey) privateKey;
        	encodedPrivateKey = psrssPrivateKey.getKey().toByteArray();
        }  */ 
        
        if (encodedPrivateKey == null) {
        	encodedPrivateKey = privateKey.getEncoded();
        	privateKeyAsString = new String(Base64.getEncoder().encode(encodedPrivateKey));
        }
        
        if (privateKey instanceof MersaPrivateKey) {
        	MersaPrivateKey mersaPrivateKey = (MersaPrivateKey) privateKey;
        	List<BigInteger> privateKeys = mersaPrivateKey.getSecretExponents();
        	
        	StringBuilder sb = new StringBuilder();
        	for(int i = 0; i < privateKeys.size(); i++) {
        		sb.append("Exponent " + i + ":");
        		sb.append(privateKeys.get(i));
        		sb.append("\n");
        	}
       	 
        	privateKeyAsString = sb.toString();
        }
        
        if (encodedPrivateKey == null) {
        	throw new RuntimeException("Extracting private key not possible");
        }
        
        return privateKeyAsString;
	}

	@Override
    void prepareAboutComposite() {
        description.setText(Descriptions.TextViewKey);
    }

}
