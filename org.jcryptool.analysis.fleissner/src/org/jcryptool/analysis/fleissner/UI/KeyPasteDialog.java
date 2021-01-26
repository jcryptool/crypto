package org.jcryptool.analysis.fleissner.UI;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.jcryptool.analysis.fleissner.key.Grille;
import org.jcryptool.analysis.fleissner.key.KeySchablone;
import org.jcryptool.analysis.fleissner.logic.FleissnerGrille;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

public class KeyPasteDialog extends Dialog {
	public Text text;
	public Label lblNewLabel;
	public Label lblNewLabel_1;
	public Composite errorComposite;
	public Label lblHint;
	protected Object lastKey;
	protected String lastError;
	private GridData errorCompData;
	private Composite container;
	KeySchablone key;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public KeyPasteDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		container = (Composite) super.createDialogArea(parent);
		GridLayout gl_container = new GridLayout();
		gl_container.numColumns = 2;
		container.setLayout(gl_container);
		
		lblNewLabel_1 = new Label(container, SWT.WRAP);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1);
		gd_lblNewLabel_1.widthHint = 680;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText(Messages.KeyPasteDialog_0);
		
		lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText(Messages.KeyPasteDialog_1);
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				Object result = parseKey(text.getText());
				if (result instanceof String) {
					String string = (String) result;
					KeyPasteDialog.this.lastKey = null;
					KeyPasteDialog.this.lastError = string;
				} else {
					int[] key = (int[]) result;
					KeyPasteDialog.this.lastError = null;
					KeyPasteDialog.this.lastKey = key;
				}
				updateDialogState();
			}
		});
		
		errorComposite = new Composite(container, SWT.NONE);
		errorCompData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		errorComposite.setLayoutData(errorCompData);
		errorComposite.setLayout(new GridLayout(1, false));
		
		lblHint = new Label(errorComposite, SWT.WRAP);
		GridData gd_lblNewLabel_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_lblNewLabel_2.widthHint = 400;
		lblHint.setLayoutData(gd_lblNewLabel_2);
		
		getShell().setText(Messages.KeyPasteDialog_2);
		return container;
	}
	
	@Override
	protected Point getInitialLocation(Point initialSize) {
		return container.getDisplay().getCursorLocation();
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(700, 260);
//		getShell().layout();
	}
	
	protected void updateDialogState() {
		if (this.lastError != null) {
			this.setError(Optional.of(this.lastError));
		} else {
			this.setError(Optional.empty());
		}
		this.getOKButton().setEnabled(this.lastKey != null);

	}

	// if message is not a string, show no error
	private void setError(Optional<String> message) {
		errorCompData.exclude = message.isEmpty();
		errorComposite.setVisible(message.isPresent());
		errorComposite.requestLayout();
		errorComposite.requestLayout();
		lblHint.requestLayout();
		lblHint.setText(message.orElse("")); //$NON-NLS-1$
//		container.getShell().pack();
	}
	
	@Override
	protected Control createButtonBar(Composite parent) {
		Control result = super.createButtonBar(parent);
		getOKButton().setEnabled(false);
		return result;
	}
	
	
	private Object parseKey(String entered) {
		if (! entered.matches("^[0-9 ]*$")) { //$NON-NLS-1$
			return Messages.KeyPasteDialog_5;
		}
		List<String> split = Arrays.asList(entered.split("\\s+")).stream().filter(s -> s.length() > 0).collect(Collectors.toList()); //$NON-NLS-1$
		List<Integer> numbers = split.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
		Set<Integer> numberSet = new HashSet<Integer>();
		numberSet.addAll(numbers);
		if (numberSet.size() != numbers.size()) {
			Set<Integer> numberSetDoubles = new HashSet<Integer>();
			int prevLen = 0;
			for(Integer num: numbers) {
				numberSetDoubles.add(num);
				if (numberSetDoubles.size() == prevLen) {
					return String.format(Messages.KeyPasteDialog_XZ3, num);
				}
				prevLen = numberSetDoubles.size();
			}
		}
		int root = (int) Math.round(Math.sqrt(split.size()));
		if (root < 1) {
			return ""; //$NON-NLS-1$
		}
		if (root*root != split.size()) {
			return String.format(Messages.KeyPasteDialog_8, split.size(), split.size());
		}
		int size = root*2;
		int[] intarray = new int[numbers.size()];
		for (int j = 0; j < numbers.size(); j++) {
			int i = numbers.get(j);
			intarray[j] = i;
		}
		Grille grille = new Grille();
		this.key = new KeySchablone(size);
		for(int nr: numbers) {
			int idx = nr-1;
			if (idx < 0) {
				return Messages.KeyPasteDialog_5;
			}
			int row = Math.floorDiv(idx, size);
			int column = idx % size;
			if (row >= size) {
				return String.format(Messages.KeyPasteDialog_9, nr, size);
			}
			key.set(row, column);
		}
		if (! key.isValid()) {
			return String.format(Messages.KeyPasteDialog_10);
		}
		grille.setKey(key);
		String logicKey = grille.translateKeyToLogic();
		String[] logicSplit = logicKey.split(","); //$NON-NLS-1$
		List<Integer> resultList = Arrays.asList(logicSplit).stream().map(Integer::parseInt).collect(Collectors.toList());
		int[] result = new int[resultList.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = resultList.get(i);
		}
		return result;
	}
	
	

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}


}
