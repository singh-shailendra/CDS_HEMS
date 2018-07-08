package SmartAgent.Appliance.SmartLight;

import Util.AIDGetter;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UIResponder extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
									 	.MatchOntology("UI");
	public UIResponder(Agent a) {
		super(a);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		if(msg!=null) {
			
			ACLMessage fb = new ACLMessage(ACLMessage.REQUEST);
			fb.addReceiver(new AIDGetter().getAID(myAgent, "Data_Analysis_Agent"));
			
			SmartLight_Agent.mode = msg.getContent();
			myAgent.send(fb);
		}else {
			block();
		}
	}
}
