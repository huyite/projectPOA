package automaton;

public class StateImpl implements State {
	private boolean initial, terminal;
	

	public StateImpl(boolean initial, boolean terminal) {
		this.initial = initial;
		this.terminal = terminal;
	}

	public boolean initial() {
		return initial;
	}

	public boolean terminal() {
		return terminal;
	}
   
	public String toString() {
		return "state " + super.toString();
	}

	

	public void setInitial(boolean t) {
		// TODO Auto-generated method stub
		this.initial=t;
		
	}

	public void setTerminal(boolean t) {
		// TODO Auto-generated method stub
		this.terminal=t;
	}
	
	
}
