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
import org.jcryptool.visual.rsa_elgamal.textbook.generic.ProcessException;
import org.jcryptool.visual.rsa_elgamal.textbook.impl.Utils;

import com.diffplug.common.base.Box;
import com.diffplug.common.base.Errors;
import com.diffplug.common.rx.RxBox;

public class EncryptSequenceProcess<PKT extends IPublicKey> implements IGenericProcess<List<BigInteger>> {
	
	public final IPublicKey publicKey;
	public RxBox<List<BigInteger>> message;
	
	public EncryptSequenceProcess(IPublicKey pub) {
		this.publicKey = pub;
		
		this.message = RxBox.of(new LinkedList<BigInteger>());
	}
	
	@Override
	public void validate() throws ProcessException {
		IGenericProcess.super.validate();
	}

	public List<BigInteger> finish() {
		return Utils.elementwise(this.publicKey::encrypt, this.message.get());
	}
	
}