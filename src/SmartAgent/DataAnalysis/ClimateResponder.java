package SmartAgent.DataAnalysis;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ClimateResponder extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
										.MatchOntology("AirConditioner");
										
	public ClimateResponder(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		if(msg != null) {
			Data_Analysis_Agent.predition.isOverbudget(Predition.airconditioner, Predition.humid, true);
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			myAgent.send(reply);
		}
		else {
			block();
		}
	}

}
