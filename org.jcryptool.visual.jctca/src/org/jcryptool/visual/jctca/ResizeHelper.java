//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2013, 2020 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.visual.jctca;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ResizeHelper {
	
//	private static 

    public static void resize_image(Label label, Composite comp_image) {
        Image image = label.getImage();
        int imageWidth = image.getBounds().width;
        int imageHeight = image.getBounds().height;
        double imageRatio = (double) imageWidth / (double) imageHeight;
        int labelWidth = label.getBounds().width;
        int labelHeight = label.getBounds().height;
        double labelRatio = (double) labelWidth / (double) labelHeight;
        
        int width_scaled;
        int height_scaled;
        
        // Die Bilder sind immer breiter als höher.
        if (labelRatio < imageRatio) {
            width_scaled = comp_image.getClientArea().width;
            height_scaled = (int) ((double) width_scaled / imageRatio);
//            height_scaled = (int) (height - ((width - width_scaled) / ratio));
        } else {
            height_scaled = comp_image.getClientArea().height;
            width_scaled = (int) ((double) height_scaled * imageRatio);
//            width_scaled = (int) (width - (height - height_scaled) * ratio);
        }

        label.setImage(new Image(label.getDisplay(), image.getImageData().scaledTo(width_scaled, height_scaled)));
    }
}
