package SmartAgent.DistributedResource;

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DR_Agent extends Agent {
	private MessageTemplate mt = MessageTemplate.MatchConversationId("optimization")
												.MatchPerformative(ACLMessage.CFP);
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("DR_Agent");
		sd.setName("JADE-DR-Agent");
		sd.setOwnership("Project_Group_5");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			System.out.println(getName() + " registed");
		} catch (FIPAException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				// TODO Auto-generated method stub
				ACLMessage msg = myAgent.receive(mt);
				if(msg!=null) {
					ACLMessage reply = msg.createReply();
					reply.setConversationId("optimization");
//					reply.setContent(new Random().nextBoolean()?"ok":"no");
					reply.setContent("ok");
					myAgent.send(reply);
				}
				else {
					block();
				}
			}
		});
	}

	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub

	}
}
