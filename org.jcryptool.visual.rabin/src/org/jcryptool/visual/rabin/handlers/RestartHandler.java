package org.jcryptool.visual.rabin.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jcryptool.visual.rabin.ui.RabinMainView;


public class RestartHandler extends AbstractHandler {

	public RestartHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		  if (HandlerUtil.getActivePart(event) instanceof RabinMainView) {
	        	RabinMainView view = ((RabinMainView) HandlerUtil.getActivePart(event));
	                
	                view.reset();
	      }

	      return null;
	}

}
