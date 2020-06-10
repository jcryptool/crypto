package org.jcryptool.visual.rsa_elgamal.view;

import java.math.BigInteger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.immutables.value.Value.*;


import com.diffplug.common.swt.Layouts;

public class ExampleView extends ViewPart {

	public ExampleView() {
	}
	
	@Immutable
	public static abstract class ExampleViewState {
	    @Parameter public abstract String enteredText();
		@Parameter public abstract BigInteger enteredNumber();
		@Derived   public String result() {
			return this.enteredText() + this.enteredNumber();
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		Layouts.setGrid(root).margin(0);
		Layouts.setGridData(root).grabAll();
		
		Label lbl = new Label(root, SWT.NONE);
		Layouts.setGridData(lbl).grabHorizontal();
		lbl.setText("Hello brave new world, with durian-swt :)");
	}
	
	public static void main(String[] args) {
		ImmutableExampleViewState testState = ImmutableExampleViewState.builder()
				.enteredNumber(BigInteger.valueOf(10001))
				.enteredText("MyKey")
				.build();
		
		ImmutableExampleViewState shortState = 
				ImmutableExampleViewState.of("In short...", BigInteger.ONE);
		
		System.out.println(String.format("%s || %s", testState, shortState));
		ImmutableExampleViewState mutated = shortState.withEnteredNumber(BigInteger.ZERO);
		System.out.println();
		System.out.println("a mutation has been made so that mutated = " + mutated.toString());
		System.out.println();
		System.out.println(String.format("%s || %s", testState, shortState));
		System.out.println("Still the same object: --> " + mutated.toString());
	}

	@Override public void setFocus() { }
}
