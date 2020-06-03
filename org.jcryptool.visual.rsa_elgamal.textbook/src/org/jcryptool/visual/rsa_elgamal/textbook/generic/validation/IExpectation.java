package org.jcryptool.visual.rsa_elgamal.textbook.generic.validation;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.jcryptool.visual.rsa_elgamal.textbook.generic.IProcess;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.IState;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.ProcessException;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation.UnmetExpectationException;
import org.jcryptool.visual.rsa_elgamal.textbook.process.RSAKeygen;
import org.jcryptool.visual.rsa_elgamal.textbook.process.RSAKeygen.RSAKeygenState;

import com.google.common.base.Predicate;

@FunctionalInterface
public interface IExpectation<PT extends IState> {
//  extends Function<PT, Optional<IProblem>>

	public class UnmetExpectationException extends Throwable {
		private static final long serialVersionUID = 3728490243809553572L;
		public UnmetExpectation<? extends IState, ? extends IExpectation<?>> ue;

		public UnmetExpectationException(UnmetExpectation<? extends IState, ? extends IExpectation<?>> ue) {
			super(makeMessage(ue), ue._throwable.orElse(null));
			this.ue = ue;
		}
		
		private static String makeMessage(UnmetExpectation<? extends IState, ? extends IExpectation<?>> ue2) {
			return String.format("Expectation was not fulfilled: %s", ue2.expectation.toString());
		}

	}
	
	public Optional<UnmetExpectation<PT, IExpectation<PT>>> check(PT process);
	
	public static class UnmetExpectation<Pt extends IState, E extends IExpectation<Pt>> {
		private static final long serialVersionUID = 2162391844098679031L;

		public final E expectation;
		public final Pt value;

		public final Optional<Throwable> _throwable;
		
		protected UnmetExpectation(Pt value, E expectation, Optional<Throwable> t) {
			super();
			this.value = value;
			this.expectation = expectation;
			this._throwable = t;
		}

		public static <Pt extends IState, E extends IExpectation<Pt>> UnmetExpectation<Pt, E> Of(Pt state, E expectation) {
			return Of(state, expectation, null);
		}
		public static <Pt extends IState, E extends IExpectation<Pt>> UnmetExpectation<Pt, E> Of(Pt state, E expectation, Throwable t) {
			return new UnmetExpectation<Pt, E>(state, expectation, Optional.ofNullable(t));
		}
	}

	public default UnmetExpectation<PT, IExpectation<PT>> unmetOn(PT state) {
 		return UnmetExpectation.Of(state, this);
	}
	
	public static <Pt extends IState> IExpectation<Pt> PredicateExpectation(Predicate<Pt> predicate) {
		return PredicateExpectation("<unnamed>", predicate);
	}

	public static <Pt extends IState> IExpectation<Pt> PredicateExpectation(String string, Predicate<Pt> predicate) {
		return new IExpectation<Pt>() {
			@Override
			public Optional<UnmetExpectation<Pt, IExpectation<Pt>>> check(Pt state) {
				boolean applies = predicate.apply(state);
				if (! applies) {
					return Optional.of(this.unmetOn(state));
				}
				return Optional.empty();
			}
			public String toString() {
				return string;
			}
			
		};
	}
}
