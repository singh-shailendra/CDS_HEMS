package SmartAgent.DataAnalysis;

import java.util.HashMap;
import java.util.Map;

import jade.core.Agent;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Data_Analysis_Agent extends Agent {
	/**
	 * 
	 */
	private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

	public static Predition predition = new Predition();
	
	public static Map appliances = new HashMap<String, String>();
	

	@Override
	public void setup() {

		// Registration with the DF
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Data_Analysis_Agent");
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
		addBehaviour(tbf.wrap(new HumanResponder(this))); // responder for sensor
		addBehaviour(tbf.wrap(new BudgetUpdater(this))); //responder for budget update
		addBehaviour(tbf.wrap(new AppReceiver(this))); 
		addBehaviour(tbf.wrap(new ClimateResponder(this)));
		// addBehaviour(tbf.wrap(new UoTReceiver(this)));

		try {
			DFService.register(this, dfd);

		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		try {
			DFService.deregister(this);
			tbf.interrupt();
		} catch (FIPAException e) {
			// TODO: handle exception
		}
	}
}
