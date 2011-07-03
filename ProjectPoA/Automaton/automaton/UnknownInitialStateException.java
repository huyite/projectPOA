package automaton;

public class UnknownInitialStateException extends Exception {

	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return "No initial state";
	}
}
