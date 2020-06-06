package org.jcryptool.visual.rsa_elgamal.textbook.process;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jcryptool.visual.rsa_elgamal.textbook.IPublicKey;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.IGenericProcess;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.IProcess;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.IState;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.ProcessException;
import org.jcryptool.visual.rsa_elgamal.textbook.impl.Utils;

import com.diffplug.common.base.Box;
import com.diffplug.common.base.Errors;
import com.diffplug.common.rx.RxBox;

public class EncryptSequenceProcess<PKT extends IPublicKey> implements IGenericProcess<EncryptSequenceProcess.State> {
	
	public static class State implements IState {
		public RxBox<List<BigInteger>> input;
		public RxBox<List<BigInteger>> output;
	}
	
	public final IPublicKey publicKey;
	public RxBox<List<BigInteger>> message;
	public State state;
	
	public EncryptSequenceProcess(IPublicKey pub) {
		this.publicKey = pub;
		this.state = new State();
		this.state.input = RxBox.of(new LinkedList<BigInteger>());
		this.state.output = RxBox.of(new LinkedList<BigInteger>());
	}
	
	@Override
	public void validate() throws ProcessException {
		IGenericProcess.super.validate();
	}

	@Override
	public State getState() {
		return this.state;
// 		return Utils.elementwise(this.publicKey::encrypt, this.message.get());
	}
	
}