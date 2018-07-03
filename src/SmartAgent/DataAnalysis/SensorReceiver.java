package SmartAgent.DataAnalysis;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SensorReceiver extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	public SensorReceiver(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		
	}
}
