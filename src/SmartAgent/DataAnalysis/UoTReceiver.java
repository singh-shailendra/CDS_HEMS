package SmartAgent.DataAnalysis;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UoTReceiver extends CyclicBehaviour{
	MessageTemplate mt = 
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST). 
			MatchPerformative(ACLMessage.REQUEST).
			MatchOntology("UoT");
	
	public UoTReceiver(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
		System.out.println("UoT receiver is running ....");
	}
	@Override
	public void action() {
		
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		if(msg!=null) {
			System.out.println("UoT receive");
		}
		else {
			block();
		}
	}
}
