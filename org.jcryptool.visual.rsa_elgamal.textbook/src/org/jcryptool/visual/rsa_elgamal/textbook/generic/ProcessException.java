package org.jcryptool.visual.rsa_elgamal.textbook.generic;

import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation;
import org.jcryptool.visual.rsa_elgamal.textbook.generic.validation.IExpectation.UnmetExpectation;

public class ProcessException extends Exception {
	private static final long serialVersionUID = 6869312274112032157L;

	public UnmetExpectation<? extends IState, ? extends IExpectation<?>> primaryCause;
	
	public ProcessException(UnmetExpectation<? extends IState, ? extends IExpectation<?>> primaryCause) {
		super();
		this.primaryCause = primaryCause;
	}

//	public String getPrimaryId() {
//		return this.subIds.get(0);
//	}
//	public ProcessException(IProcess<?, ? extends ProcessException> process, String reason) {
//		this(process, reason, new LinkedList<String>());
//	}
//	public ProcessException(IProcess<?, ? extends ProcessException> process, String reason, String subId) {
//		this(process, reason, new LinkedList<String>() {{ add(subId); }});
//	}
//	public ProcessException(IProcess<?, ? extends ProcessException> process, String reason, Collection<String> subIds) {
//		super(String.format("Invalid%s because %s: %s", whatAsString(subIds), reason));
//		this.reason = reason;
//		if (subIds.size() == 0) {
//			throw new RuntimeException("subids are empty!");
//		}
//		this.subIds = new LinkedList<String>(subIds);
//		this.subIds.add(this.getClass().getName() + "::" + this.getMessage());
//	}
//
//	private static String whatAsString(Collection<String> subIds) {
//		return String.join(", ", subIds);
//	}

}
