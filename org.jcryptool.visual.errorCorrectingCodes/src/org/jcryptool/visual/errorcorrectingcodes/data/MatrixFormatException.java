// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2021 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.visual.errorcorrectingcodes.data;

public class MatrixFormatException extends RuntimeException {
    
    public MatrixFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatrixFormatException(String message) {
        super(message);
    }
    
}
