package SmartAgent.Appliance.SmartLight;

import Util.AIDGetter;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Executor extends CyclicBehaviour {
	private static MessageTemplate mt = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST)
			.MatchPerformative(ACLMessage.REQUEST).MatchOntology("da");

	public Executor(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		myAgent.send(arg0);
		if (msg != null) {
//			ACLMessage reply = msg.createReply();
			ACLMessage reply = new ACLMessage(ACLMessage.REQUEST);
			reply.addReceiver(new AIDGetter().getAID(myAgent, "Data_Analysis_Agent"));
			reply.setOntology("SmartLight");
			reply.setLanguage(msg.getLanguage());
			if (msg.getContent().equals("on")) {
				System.out.println(myAgent.getLocalName() + " light is successfully turned on");
				System.out.println(myAgent.getLocalName() + " light is running on " + msg.getLanguage() + " mode");
				
				reply.setContent("on");
				
				
//				if (msg.getEncoding().equals("overbudget")) {
//					
//					myAgent.addBehaviour(new Optimizer(myAgent));
//		
//				}
			} else {
				System.out.println(myAgent.getLocalName() + " light is successfully turned off");
				reply.setContent("off");
			}
			SmartLight_Agent.mode = msg.getLanguage();
			myAgent.send(reply);
		} else {
			block();
		}
	}
}
