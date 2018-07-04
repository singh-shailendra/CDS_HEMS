package SmartAgent.Appliance.SmartLight;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Optimizer extends Behaviour {
	private int state = 0;
	private MessageTemplate mt = MessageTemplate.MatchConversationId("optimization");
	public Optimizer(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
	}
	public AID getAID(String type) {
		AID aid = null;
		try {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType(type);
			template.addServices(sd);
			aid = DFService.search(myAgent, template)[0].getName();
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aid;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		switch (state) {
		case 0:
			ACLMessage msg = new ACLMessage(ACLMessage.CFP);
			msg.addReceiver(this.getAID("Airconditioner_Agent"));
			msg.setConversationId("optimization");
			myAgent.send(msg);
			state++;
			break;
		case 1:
			ACLMessage recv = myAgent.receive(mt);
			if(recv!=null) {
				if(recv.getContent().equals("ok")) {
					state = 4;
				}
			}
			else {
				state++;
			}
			break;
		case 2:
			ACLMessage dr = new ACLMessage(ACLMessage.CFP);
			dr.addReceiver(this.getAID("DR_Agent"));
			dr.setConversationId("optimization");
			myAgent.send(dr);
			state++;
			break;
		case 3:
			ACLMessage drReply = myAgent.receive(mt);
			if(drReply!=null) {
				if(drReply.getContent().equals("ok")) {
					state = 4;
				}
				else {
					state = 5;
				}
			}
			break;
		
		case 4:
			System.out.println("budget controlled");
			
		
		case 5:
			System.out.println("still overbudget");
		}
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return (state == 5 || state == 4);
	}
}
