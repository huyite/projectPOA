package machine;
import graph.editor.Vertex;
import automaton.DeterministicAutomaton;
import automaton.NotDeterministicInitialStateException;
import automaton.NotDeterministiTransitionException;
import automaton.Transition;
import automaton.UnknownInitialStateException;

public class FiniteStateMachine<T> extends DeterministicAutomaton<T> {

	public FiniteStateMachine(TransitionWithAction<T>[] transitions)
			throws NotDeterministiTransitionException,
			NotDeterministicInitialStateException, UnknownInitialStateException {
		super(transitions);
	}

	protected Vertex changeCurrentState(Transition<T> t) {
		return ((TransitionWithAction<T>) t).cross();
	}
}
