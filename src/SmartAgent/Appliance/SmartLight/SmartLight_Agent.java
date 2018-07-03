package SmartAgent.Appliance.SmartLight;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SmartLight_Agent extends Agent {
	private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
	private static final Integer low = 0;
	private static final Integer mid = 1;
	private static final Integer high = 2;
	
	@Override
	public void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("Smart_Light_Agent"); 
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
		
		
	
		try {
			DFService.register(this, dfd);
			Behaviour recv = new ExecuteReceiver(this);
			addBehaviour(recv);
		
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
