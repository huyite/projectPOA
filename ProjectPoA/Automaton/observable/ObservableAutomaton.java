package observable;

import java.util.Observable;
import java.util.Observer;

import automaton.DeterministicAutomaton;
import automaton.NotDeterministicInitialStateException;
import automaton.NotDeterministiTransitionException;
import automaton.Transition;
import automaton.UnknownInitialStateException;
import graph.editor.Vertex;
public class ObservableAutomaton<T> extends DeterministicAutomaton<T> {

	public ObservableAutomaton(Transition<T>[] transitions) throws NotDeterministiTransitionException, NotDeterministicInitialStateException, UnknownInitialStateException {
		super(transitions);
	}
	
	private Observable observable = new Observable() {
		public void notifyObservers(Object arg) {
			setChanged();
			super.notifyObservers(arg);
		}	
	};
	
	protected Vertex changeCurrentState(Transition<T> t) {
		observable.notifyObservers(t);
		return super.changeCurrentState(t);
	}
	
	public void addObserver(Observer o) {
		observable.addObserver(o);
	}
	
}
