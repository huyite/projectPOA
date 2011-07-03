package machine;
import graph.editor.Vertex;
import automaton.Transition;

public class TransitionWithAction<T> implements Transition<T> {

	private final Transition<? extends T> transition;
	private final Action<? super T> action;

	public TransitionWithAction(Transition<? extends T> t, Action<? super T> a) {
		this.transition = t;
		this.action = a;
	}

	public T label() {
		return transition.label();
	}

	public Vertex source() {
		return transition.source();
	}
	
	public Vertex target() {
		return transition.target();
	}
	

	public Vertex cross() {
		action.execute(transition.label());
		return transition.target();
	}

	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setID(int id) {
		// TODO Auto-generated method stub
		
	}

}
