package org.jcryptool.visual.signalencryption.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jcryptool.visual.signalencryption.ui.SignalEncryptionView;

public class RestartHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        if (HandlerUtil.getActivePart(event) instanceof SignalEncryptionView) {
            SignalEncryptionView view = ((SignalEncryptionView) HandlerUtil.getActivePart(event));

            view.resetView();
        }
        return null;
    }
}