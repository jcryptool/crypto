package org.jcryptool.crypto.classic.model.ngram;

import java.util.function.Supplier;

public class NgramException extends RuntimeException {

	public NgramException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NgramException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NgramException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NgramException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NgramException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public static void check(boolean b, String description) {
		check(() -> b, description);
	}
	public static void check(Supplier<Boolean> _check, String description) {
		if (! _check.get()) {
			throw new NgramException(description);
		}
	}

}
