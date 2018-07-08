package SmartAgent.Appliance.SmartLight;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class OptimizerTrigger extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
										.MatchOntology("budget");
	public OptimizerTrigger(Agent a) {
		super(a);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		if(msg != null) {
			if(msg.getEncoding().equals("overbudget")) {
				myAgent.addBehaviour(new Optimizer(myAgent));
			}
		}
		else {
			block();
		}
	}

}
