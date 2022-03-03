//-----BEGIN DISCLAIMER-----
/*******************************************************************************
* Copyright (c) 2012, 2021 JCrypTool Team and Contributors
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*******************************************************************************/
//-----END DISCLAIMER-----
package org.jcryptool.crypto.ui.textblockloader;

import java.util.List;

public abstract class ConversionNumbersToBlocks {

	public abstract List<Integer> convert(List<Integer> i);
	public abstract List<Integer> revert(List<Integer> i);
	public abstract Integer getMaxBlockValue();
	
}
