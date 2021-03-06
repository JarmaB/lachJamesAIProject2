package aima.gui.applications.search.nurse;

import java.awt.Color;

import aima.core.search.nursecsp.Assignment;
import aima.core.search.nursecsp.BacktrackingStrategy;
import aima.core.search.nursecsp.CSP;
import aima.core.search.nursecsp.CSPStateListener;
import aima.core.search.nursecsp.Domain;
import aima.core.search.nursecsp.ImprovedBacktrackingStrategy;
import aima.core.search.nursecsp.NurseCSP;
import aima.core.search.nursecsp.MinConflictsStrategy;
import aima.core.search.nursecsp.SolutionStrategy;
import aima.core.util.datastructure.FIFOQueue;
import aima.gui.framework.AgentAppController;
import aima.gui.framework.AgentAppEnvironmentView;
import aima.gui.framework.AgentAppFrame;
import aima.gui.framework.MessageLogger;
import aima.gui.framework.SimpleAgentApp;
import aima.gui.framework.SimulationThread;
import aima.gui.applications.search.nurse.NurseGUI;

/**
 * Application which demonstrates basic constraint algorithms based on map
 * coloring problems. It shows the constraint graph, lets the user select a
 * solution strategy, and allows then to follow the progress step by step. For
 * pragmatic reasons, the implementation uses the agent framework, even though
 * there is no agent and only a dummy environment.
 * 
 * @author Ruediger Lunde
 */
public class NurseRosteringApp extends SimpleAgentApp {

	/** Returns an <code>CSPView</code> instance. */
	@Override
	public AgentAppEnvironmentView createEnvironmentView() {
		return new CSPView();
	}

	/** Returns a <code>MapColoringFrame</code> instance. 
	 * @wbp.parser.entryPoint*/
	@Override
	public AgentAppFrame createFrame() {
		return new NurseRosteringFrame();
	}

	/** Returns a <code>MapColoringController</code> instance. */
	@Override
	public AgentAppController createController() {
		return new NurseRosteringController();
	}

	
	// ///////////////////////////////////////////////////////////////
	// main method

	/**
	 * Starts the application.
	 */
	public static void main(String args[]) {
		//new NurseRosteringApp().startApplication();
		new NurseGUI();
	}

	
	// ///////////////////////////////////////////////////////////////
	// some inner classes

	/**
	 * Adds some selectors to the base class and adjusts its size.
	 */
	protected static class NurseRosteringFrame extends AgentAppFrame {
		private static final long serialVersionUID = 1L;
		public static String ENV_SEL = "EnvSelection"; // roster period
		public static String STRATEGY_SEL = "SearchSelection";

		public NurseRosteringFrame() {
			setTitle("Nurse Rostering CSP Application");
			setSelectors(new String[] { ENV_SEL, STRATEGY_SEL }, new String[] {
					"Select Environment", "Select Solution Strategy" });
			setSelectorItems(ENV_SEL, new String[] { "Roster Period",
					"7 days",
					"14 days"}, 0);
			setSelectorItems(STRATEGY_SEL, new String[] { "Backtracking",
					"Backtracking + MRV & DEG",
					"Backtracking + Forward Checking",
					"Backtracking + Forward Checking + MRV",
					"Backtracking + Forward Checking + LCV",
					"Backtracking + AC3",
					"Backtracking + AC3 + MRV & DEG + LCV",
					"Min-Conflicts (50)" }, 0);
			setEnvView(new CSPView());
			setSize(800, 600);
		}
	}

	/**
	 * Defines how to react on standard simulation button events.
	 */
	protected static class NurseRosteringController extends AgentAppController {

		protected CSPEnvironment env;
		protected SolutionStrategy strategy;
		protected FIFOQueue<CSPEnvironment.StateChangeAction> actions;
		protected int actionCount;

		protected NurseRosteringController() {
			env = new CSPEnvironment();
			actions = new FIFOQueue<CSPEnvironment.StateChangeAction>();
		}

		protected CSPView getCSPView() {
			return (CSPView) frame.getEnvView();
		}

		/** Prepares next simulation. */
		@Override
		public void clear() {
			prepare(null);
		}

		/**
		 * Creates a CSP and updates the environment as well as its view.
		 */
		@Override
		public void prepare(String changedSelector) {
			AgentAppFrame.SelectionState selState = frame.getSelection();
			CSP csp = null;
			CSPView view = getCSPView();
			switch (selState.getValue(NurseRosteringFrame.ENV_SEL)) {
			case 0: // period 7days
				csp = new NurseCSP(7);
				break;
			case 1: // period 14days
				csp = new NurseCSP(14);
				csp.setDomain(NurseCSP.NSW, new Domain(new Object[]{NurseCSP.BLUE}));
				break;
			}
			view.clearMappings();
			view.setPositionMapping(NurseCSP.WA, 5, 10);
			view.setPositionMapping(NurseCSP.NT, 15, 3);
			view.setPositionMapping(NurseCSP.SA, 20, 15);
			view.setPositionMapping(NurseCSP.Q, 30, 5);
			view.setPositionMapping(NurseCSP.NSW, 35, 15);
			view.setPositionMapping(NurseCSP.V, 30, 23);
			view.setPositionMapping(NurseCSP.T, 33, 30);
			view.setColorMapping(NurseCSP.RED, Color.RED);
			view.setColorMapping(NurseCSP.GREEN, Color.GREEN);
			view.setColorMapping(NurseCSP.BLUE, Color.BLUE);
			
			actions.clear();
			actionCount = 0;
			env.init(csp);
			view.setEnvironment(env);
		}

		/** Checks whether simulation can be started. */
		@Override
		public boolean isPrepared() {
			return env.getCSP() != null
					&& (!actions.isEmpty() || env.getAssignment() == null);
		}

		/** Starts simulation. */
		@Override
		public void run(MessageLogger logger) {
			logger.log("<simulation-log>");
			prepareActions();
			try {
				while (!actions.isEmpty() && !frame.simulationPaused()) {
					env.executeAction(null, actions.pop());
					actionCount++;
					Thread.sleep(200);
				}
				logger.log("Number of Steps: " + actionCount);
				// logger.log(getStatistics());
			} catch (InterruptedException e) {
				// nothing to do here.
			}
			logger.log("</simulation-log>\n");
		}

		/** Performs a simulation step. */
		@Override
		public void step(MessageLogger logger) {
			prepareActions();
			if (!actions.isEmpty()) {
				env.executeAction(null, actions.pop());
				actionCount++;
				if (actions.isEmpty())
					logger.log("Number of Steps: " + actionCount);
			}
		}

		/**
		 * Starts the selected constraint solver and fills the action list if
		 * necessary.
		 */
		protected void prepareActions() {
			ImprovedBacktrackingStrategy iStrategy = null;
			if (actions.isEmpty()) {
				SolutionStrategy strategy = null;
				switch (frame.getSelection().getValue(
						NurseRosteringFrame.STRATEGY_SEL)) {
				case 0:
					strategy = new BacktrackingStrategy();
					break;
				case 1: // MRV + DEG
					strategy = new ImprovedBacktrackingStrategy
					(true, true, false, false);
					break;
				case 2: // FC
					iStrategy = new ImprovedBacktrackingStrategy();
					iStrategy.setInference(ImprovedBacktrackingStrategy
									.Inference.FORWARD_CHECKING);
					break;
				case 3: // MRV + FC 
					iStrategy = new ImprovedBacktrackingStrategy
					(true, false, false, false);
					iStrategy.setInference(ImprovedBacktrackingStrategy
									.Inference.FORWARD_CHECKING);
					break;
				case 4: // FC + LCV
					iStrategy = new ImprovedBacktrackingStrategy
					(false, false, false, true);
					iStrategy.setInference(ImprovedBacktrackingStrategy
									.Inference.FORWARD_CHECKING);
					break;
				case 5: // AC3
					strategy = new ImprovedBacktrackingStrategy
					(false, false, true, false);
					break;
				case 6: // MRV + DEG + AC3 + LCV
					strategy = new ImprovedBacktrackingStrategy
					(true, true, true, true);
					break;
				case 7:
					strategy = new MinConflictsStrategy(50);
					break;
				}
				if (iStrategy != null)
					strategy = iStrategy;
				
				try {
					strategy.addCSPStateListener(new CSPStateListener() {
						@Override
						public void stateChanged(Assignment assignment, CSP csp) {
							actions.add(new CSPEnvironment.StateChangeAction(
									assignment, csp));
						}
						@Override
						public void stateChanged(CSP csp) {
							actions.add(new CSPEnvironment.StateChangeAction(
									csp));
						}
					});
					strategy.solve(env.getCSP().copyDomains());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/** Updates the status of the frame after simulation has finished. */
		public void update(SimulationThread simulationThread) {
			if (simulationThread.isCanceled()) {
				frame.setStatus("Task canceled.");
			} else if (frame.simulationPaused()) {
				frame.setStatus("Task paused.");
			} else {
				frame.setStatus("Task completed.");
			}
		}
	}
}
