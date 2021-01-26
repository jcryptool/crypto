// -----BEGIN DISCLAIMER-----
/*******************************************************************************
 * Copyright (c) 2019, 2020 JCrypTool Team and Contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
// -----END DISCLAIMER-----
package org.jcryptool.analysis.fleissner.key;

import java.util.LinkedList;
import java.util.List;

/**
 * Ver- und entschlüsselt einen Text mit der Grille-Verschlüsselung
 *
 * @author Patricia
 * basis used from 'Grille'-Plug-in
 *
 */
public class Grille {
    private KeySchablone key;

    /**
     * 
     * @return Den Schlüssel als String in Form von Koordinaten
     */
    public int[] translateKeyToArray() {
        List<Integer> result = new LinkedList<>();
        for (int y=0; y<key.getSize();y++) {
            for (int x=0; x<key.getSize();x++) {
                if (key.get(y, x)=='1') {
                    result.add(x);
                    result.add(y);
                }
            }
        }
        int[] resultArr = new int[result.size()];
        for (int i = 0; i < resultArr.length; i++) {
			resultArr[i] = result.get(i);
		}
        return resultArr;
    }

    /**
     * 
     * @return Den Schlüssel als String in Form von Koordinaten
     */
    public String translateKeyToLogic() {
        
        String newKey = "";
        for (int y=0; y<key.getSize();y++) {
            for (int x=0; x<key.getSize();x++) {
                if (key.get(y, x)=='1') {
                    newKey+=x+","+y+",";
                }
            }
        }
        return newKey;
    }

    public void setKey(KeySchablone key) {
        this.key = key;
    }

    public KeySchablone getKey() {
        return key;
    }
}
