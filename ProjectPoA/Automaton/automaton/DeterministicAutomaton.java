package automaton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import graph.editor.Vertex;
public class DeterministicAutomaton<T> {

	private Vertex initialState = null;

	/*
	 * In the map transitions, at each state s we associate a map m where the
	 * values are the transitions having s as source and the corresponding key
	 * the labels of the transitions.
	 * 
	 */
	private final Map<Vertex, Map<T, Transition<T>>> transitions;

	public DeterministicAutomaton(Transition<T>... transitions)
			throws NotDeterministiTransitionException,
			NotDeterministicInitialStateException, UnknownInitialStateException {
		this.transitions = new HashMap<Vertex, Map<T, Transition<T>>>();
		for (Transition<T> t : transitions) {
			addState(t.source());
			addState(t.target());
			Map<T, Transition<T>> map = this.transitions.get(t.source());
			if (map.containsKey(t.label())) {
				throw new NotDeterministiTransitionException(t, map.get(t
						.label()));
			} else {
				map.put(t.label(), t);
			}
		}
		if (initialState == null) {
			throw new UnknownInitialStateException();
		}
	}

	protected final void addState(Vertex s)
			throws NotDeterministicInitialStateException {
		if (!transitions.containsKey(s)) {
			if (s.getState().initial()) {
				if (initialState == null) {
					initialState = s;
				}else{throw new NotDeterministicInitialStateException(s,
						initialState);}
				
			}
			transitions.put(s, new HashMap<T, Transition<T>>());
		}
	}

	public Vertex initialState() {
		return initialState;
	}

	/**
	 * @exception NoSuchElementException
	 *                if the <tt>source</tt> does not belong to the automaton.
	 */
	public Transition<T> transition(Vertex source, T label) {
		if (!transitions.containsKey(source)) {
			throw new NoSuchElementException();
		}
		return transitions.get(source).get(label);
	}

	public boolean recognize(T[] word) {
		return recognize(Arrays.asList(word).iterator());
	}

	public boolean recognize(Iterator<T> word) {
		Vertex s = initialState;
		while (word.hasNext()) {
			Transition<T> t = transition(s, word.next());
			if (t == null) {
				return false;
			} else {
				s = changeCurrentState(t);
			}
		}
		return s.getState().terminal();
	}
	
	protected Vertex changeCurrentState(Transition<T> t) {
		return t.target();
	}
}
