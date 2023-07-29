package org.jcryptool.visual.signalencryption.graphics;

import org.eclipse.swt.graphics.GC;

/**
 * Basic superclass for drawable elements which we gonna need in this plugin.
 * Elements need to be set invisible, which you can do via
 * {@link #setVisible(boolean))}.
 * <p/>
 * Components are intended to be used on a {@link ComponentDrawComposite}
 */
public interface Component {

    boolean isVisible();

    void setVisible(boolean visible);

    void draw(GC gc);

    void dispose();

}
