/* *****************************************************************************
 * Copyright (c) 2011, 2021 JCrypTool Team and Contributors
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 * ****************************************************************************/
package org.jcryptool.analysis.vigenere.exceptions;

/**
 * This exception is thrown whenever the input of user cannot be processed.
 * 
 * @author Ronny Wolf
 * @version 0.0.1, 2010/07/07
 */
public class IllegalInputException extends Exception {

    /**
     * The required identification number for serialization.
     */
    private static final long serialVersionUID = 3780201911397472599L;

    /**
     * Constructs an <code>IllegalInputException</code>.
     */
    public IllegalInputException() {
        super();
    }

    /**
     * Constructs an <code>IllegalInputException</code> with the specified,
     * detailed message.
     * 
     * @param message
     *            the detail message.
     */
    public IllegalInputException(final String message) {
        super(message);
    }
}
