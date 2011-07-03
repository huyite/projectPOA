package automaton;
import graph.editor.Vertex;
public class NotDeterministicInitialStateException extends Exception {

	private static final long serialVersionUID = 1L;

	private Vertex e1, e2;
	
	public NotDeterministicInitialStateException(Vertex e1, Vertex e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	

	public Vertex [] initialStates() {
		return new Vertex[] {e1, e2};
	}
	
	public String getMessage() {
		return "Two initial states " + e1 + " and " + e2;
	}
}
