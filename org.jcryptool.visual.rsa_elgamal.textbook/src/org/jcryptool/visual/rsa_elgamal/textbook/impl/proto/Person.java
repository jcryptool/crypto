package org.jcryptool.visual.rsa_elgamal.textbook.impl.proto;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jcryptool.visual.rsa_elgamal.textbook.IPrivateKey;
import org.jcryptool.visual.rsa_elgamal.textbook.IPublicKey;

import com.diffplug.common.base.Box;

public class Person {
	public Box<IPublicKey> publicKey;
	public Box<IPrivateKey> privateKey;
	public Box<WritingDesk> desktop;
	public Box<Inbox> inbox;
}
