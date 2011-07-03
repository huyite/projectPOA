package automaton;

import automaton.Transition;

public class NotDeterministiTransitionException extends Exception {

	private static final long serialVersionUID = 1L;

	private Transition<?> t1, t2;

	public NotDeterministiTransitionException(Transition<?> t1, Transition<?> t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	
	public Transition<?>[] transitions() {
		return new Transition<?>[] {t1, t2};
	}
	
	public String getMessage() {
		return "Duplicated transition " + t1 + ", " + t2;
	}

}
