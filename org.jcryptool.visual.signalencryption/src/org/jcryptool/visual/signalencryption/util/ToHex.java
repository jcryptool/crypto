package org.jcryptool.visual.signalencryption.util;
import java.util.Arrays;

import com.google.common.io.BaseEncoding;

public class ToHex {

    /**
     * Small helper method to shorten lines.
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, 0, bytes.length);
    }

    public static String toHex(byte[] bytes, int offset, int length) {
    	return BaseEncoding.base16().upperCase().withSeparator(" ", 4)
    			.encode(Arrays.copyOfRange(bytes, offset, offset + length));
    }

}
