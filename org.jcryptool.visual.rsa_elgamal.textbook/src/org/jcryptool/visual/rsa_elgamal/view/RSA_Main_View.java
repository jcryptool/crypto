package org.jcryptool.visual.rsa_elgamal.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.jcryptool.visual.rsa_elgamal.textbook.fun.ImmutableFoobarValue;

public class RSA_Main_View extends ViewPart {

	public RSA_Main_View() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite wrapper = new Composite(parent, SWT.NONE);
		wrapper.setLayout(new GridLayout());
		
		ImmutableFoobarValue content = ImmutableFoobarValue.of(43, "Helloworld");
		
		Label label = new Label(wrapper, SWT.NONE);
		label.setText(content.toString());
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
