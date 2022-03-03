package org.jcryptool.analysis.substitution.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jcryptool.analysis.substitution.views.SubstitutionAnalysisView;

public class RestartHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		if (HandlerUtil.getActivePart(event) instanceof SubstitutionAnalysisView) {
			SubstitutionAnalysisView view = ((SubstitutionAnalysisView) HandlerUtil.getActivePart(event));
			view.resetAnalysis();
		}

		return null;
	}

}
