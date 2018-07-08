package SmartAgent.Appliance.SmartLight;

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
		if (msg != null) {
			ACLMessage reply = msg.createReply();
			reply.setOntology("SmartLight");
			reply.setLanguage(SmartLight_Agent.mode);
			if (msg.getContent().equals("on")) {
				System.out.println(myAgent.getLocalName() + " light is successfully turned on");
				System.out.println(myAgent.getLocalName() + " light is running on " + msg.getLanguage() + " mode");

				reply.setContent("on");
				
				SmartLight_Agent.mode = msg.getLanguage();
				if (msg.getEncoding().equals("overbudget")) {
					myAgent.addBehaviour(new Optimizer(myAgent));
					// System.out.println("optimizer");
				}
			} else {
				System.out.println(myAgent.getLocalName() + " light is successfully turned off");
				reply.setContent("off");
			}
			myAgent.send(reply);
		} else {
			block();
		}
	}
}
