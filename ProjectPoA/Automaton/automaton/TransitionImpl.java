package automaton;
import graph.editor.Vertex;
public class TransitionImpl<T> implements Transition<T> {
	private final Vertex source;

	private final Vertex target;

	private final T label;
    
    private int id=0;
	public TransitionImpl(Vertex source, Vertex target, T label) {
		this.source = source;
		this.target = target;
		this.label = label;
	
	}
  
public int getID()
  {
	  return this.id;
  }
  public void setID(int id)
  {
	  this.id=id;
  }
	public Vertex target() {
		return this.target;
	}

	public T label() {
		return label;
	}

	public Vertex source() {
		return source;
	}

	public String toString() {
		return "transition : " + source + ", " + '"' + label + '"' + ", "
				+ target;
	}
}
