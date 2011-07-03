package automaton;
import graph.editor.Vertex;
public interface Transition<T> {

	public Vertex source();

	public Vertex target();

	public T label();
	
	public int getID();
    
	public void setID(int id);
}
