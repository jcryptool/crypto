package org.jcryptool.visual.rsa_elgamal.textbook.generic;

import java.util.Optional;

import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation.UnmetExpectation;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation.UnmetExpectationException;
import org.jcryptool.visual.rsa_elgamal.textbook.process.RSAKeygen;

import io.reactivex.Observable;

public interface IProcess<T extends IState> {
	
	public default void validate() throws ProcessException {
		return;
	}
	
	/**
	 * Finishes the interactive process, returning its result.
	 * Assumes that it was preceded by a successful run of 
	 * this{@link #validate()}.
	 * 
	 * @return the result
	 */
	public T getState();
	
	public static class ProcessExpectationUnmet extends UnmetExpectationException {

		private static final long serialVersionUID = -4924058696066768892L;
		public IProcess<? extends IState> process;
		public Observable<?> origin;
		public Object value;

		public ProcessExpectationUnmet(UnmetExpectation<? extends IState, ? extends IExpectation<?>> ue, 
				IProcess<? extends IState> process, Observable<?> origin, Object value) {
			super(ue);
			this.process = process;
			this.origin = origin;
			this.value = value;
		}
		
	}

}
