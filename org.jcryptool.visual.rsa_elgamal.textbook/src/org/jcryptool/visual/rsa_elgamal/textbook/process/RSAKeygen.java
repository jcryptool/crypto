package org.jcryptool.visual.rsa_elgamal.textbook.process;

import java.math.BigInteger;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.jcryptool.visual.rsa_elgamal.textbook.IKeyOrigin;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.AbstractInteractiveProcess;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.IProcess;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.IState;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.ProcessException;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation.UnmetExpectation;
import org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa.RSAKeys;
import org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa.RSAMath;
import org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa.RSAPrivateKey;
import org.jcryptool.visual.rsa_elgamal.textbook.impl.rsa.RSAPublicKey;

import com.diffplug.common.rx.RxBox;
import com.diffplug.common.rx.RxGetter;
import com.google.common.collect.Lists;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.subjects.BehaviorSubject;

/**
 * This keygen generates a key pair, requiring a choice of e (public part), generating n and d
 * Other forms are possible, e.g. randomly choosing numbers; this one however does
 * nothing of the sort (deterministic).
 * 
 * This particular choice of key generation derives n from p and q, and derives d from e
 * 
 * @author simon
 *
 */
public class RSAKeygen extends AbstractInteractiveProcess<RSAKeygen.RSAKeygenState> implements IKeyOrigin, RxGetter<RSAKeygen.RSAKeygenState> {

	public static class RSAKeygenState implements IState {
		
		// these are public setters
		public RxBox<BigInteger> p = RxBox.of(BigInteger.valueOf(0));
		public RxBox<BigInteger> q = RxBox.of(BigInteger.valueOf(0));
		public RxBox<BigInteger> e = RxBox.of(BigInteger.valueOf(0));
		
		// these are just secondary outputs
		public BigInteger phi = BigInteger.valueOf(0);
		public BigInteger d = BigInteger.valueOf(0);
		public BigInteger n = BigInteger.valueOf(0);
		
		public List<Observable<?>> getObservables() {
			return new LinkedList<Observable<?>>() {{
				add(p.asObservable());
				add(q.asObservable());
				add(e.asObservable());
			}};
			
		}
		
		public RSAKeys getKeys() {
			return new RSAKeys(new RSAPrivateKey(n, d), new RSAPublicKey(n, e.get()));
		}
	}

	public static IExpectation<RSAKeygenState> expectation_primeP = IExpectation.PredicateExpectation("p is prime", state -> state.p.get().isProbablePrime(9999));
	public static IExpectation<RSAKeygenState> expectation_primeQ = IExpectation.PredicateExpectation("q is prime", state -> state.q.get().isProbablePrime(9999));
	public static IExpectation<RSAKeygenState> expectation_n_gt_p = IExpectation.PredicateExpectation("n > p", state -> state.n.compareTo(state.p.get()) > 0);
	public static IExpectation<RSAKeygenState> expectation_n_gt_q = IExpectation.PredicateExpectation("n > q", state -> state.n.compareTo(state.q.get()) > 0);
	
	public List<IExpectation<RSAKeygenState>> getExpectations() {
		return Lists.newArrayList(
				expectation_primeP, 
				expectation_primeQ, 
				expectation_n_gt_p, 
				expectation_n_gt_q
				);
	}

	protected void stateUpdate(Observable<?> origin, Object value) {
		// TODO: process expectations; if failed
		
		for (IExpectation<RSAKeygenState> expectation: this.getExpectations()) {
			Optional<UnmetExpectation<RSAKeygenState, IExpectation<RSAKeygenState>>> checkResult = expectation.check(state);
			if (checkResult.isPresent()) {
				this.onExpectationUnmet(origin, value, checkResult.get());
				return;
			}
		}
		
		state.phi = RSAMath.phi(state.p.get(), state.q.get());
		state.d   = RSAMath.d(state.phi, state.e.get());
		state.n   = RSAMath.n(state.p.get(), state.q.get());

		this.subject.onNext(state);
	}

	private void onExpectationUnmet(Observable<?> origin, Object value,
			UnmetExpectation<RSAKeygenState, IExpectation<RSAKeygenState>> ue) {
		ProcessExpectationUnmet throwable = new ProcessExpectationUnmet(ue, this, origin, value);
		this.subject.onError(throwable);
	}

	private RSAKeygenState state;
	public RSAKeygenState getState() {
		return state;
	}
	
	public BehaviorSubject<RSAKeygenState> subject;
	
	public static RSAKeygen createDefault() {
		RSAKeygenState state = new RSAKeygenState();
		state.e.set(BigInteger.valueOf(2));
		state.p.set(BigInteger.valueOf(3));
		state.q.set(BigInteger.valueOf(5));
		return new RSAKeygen(state);
	}
	
	public RSAKeygen(RSAKeygenState initState) {
		this.state = initState;
		this.subject = BehaviorSubject.createDefault(this.state);
		
		// at this point, the state must be consistent or else, errors will get fired
		this.state.getObservables().forEach(o -> o.subscribe(val -> stateUpdate(o, val))); 
	}
	
	@Override
	public Observable<RSAKeygenState> asObservable() {
		return this.subject;
	}

	@Override
	public RSAKeygenState get() {
		return this.state;
	}

	public static RSAKeygen from(RSAKeygenState state) {
		return new RSAKeygen(state);
	}
	
}
