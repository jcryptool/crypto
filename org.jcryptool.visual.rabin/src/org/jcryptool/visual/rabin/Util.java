package org.jcryptool.visual.rabin;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class Util {

	public Util() {
		// TODO Auto-generated constructor stub
	}
	
	public static ClickListener listenToClick(Consumer<SelectionEvent> lambda) {
		return new ClickListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lambda.accept(e);
			}
		};
	}
	
	public static interface ClickListener extends SelectionListener {
		
		

		@Override
		public void widgetSelected(SelectionEvent e);

		@Override
		default void widgetDefaultSelected(SelectionEvent e) {
			// never needed
		}
		 
		
	}
	
	public static void main(String[] args) {
		ClickListener myClickListener = 
				(SelectionEvent evt) -> { System.out.println("Hi" + evt.getSource()); };
	}

}
