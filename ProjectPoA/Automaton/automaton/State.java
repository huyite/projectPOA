package automaton;


public interface State {
	public boolean initial();

	public boolean terminal();
	
	public void setInitial(boolean t);
	
	public void setTerminal(boolean t);
	
	

	
	
	
}
