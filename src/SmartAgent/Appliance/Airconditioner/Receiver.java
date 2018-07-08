package SmartAgent.Appliance.Airconditioner;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Receiver extends CyclicBehaviour {
	private static MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP)
										.MatchConversationId("optimization");
			

	public Receiver(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			ACLMessage reply = msg.createReply();
			reply.setConversationId("optimization");
//			Random r = new Random();
//			int Low = 1;
//			int High = 999;
//			int Result = r.nextInt(High-Low) + Low;
//			if (Result % 2 == 0){
			if(Airconditioner_Agent.mode.equals("humid")) {
				//humid mode
				System.out.println("The air conditioner on humid mode is not running on non-peak time, shutting down");
				reply.setContent("agree");
				Airconditioner_Agent.mode = "off";
				
			}
//			else if((Result % 3 == 0)) {
//				//mid
//				reply.setContent("refuse");
//			}
//			else if((Result % 5 == 0)) {
//				//high
//				reply.setContent("refuse");
//			}
//			else {
//				//low
//				reply.setContent("refuse");
//			}
			else {
				reply.setContent("refuse");
			}
//			reply.setPerformative(ACLMessage.CFP);
			myAgent.send(reply);
		} else {
			block();
		}
	}
}
