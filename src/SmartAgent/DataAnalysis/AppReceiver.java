package SmartAgent.DataAnalysis;

import Util.AIDGetter;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AppReceiver extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
										.MatchOntology("appliance");
	public AppReceiver(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
		System.out.println("App receiver is running..");
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		if(msg != null) {
			Data_Analysis_Agent.appliances.put(msg.getSender().getName(), Predition.off);
			
			System.out.println("appliances: "+msg.getSender().getName()+
								" registered on "+
							Data_Analysis_Agent.appliances.get(msg.getSender().getName())
								+" mode");
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			myAgent.send(reply);
//			ACLMessage send = new ACLMessage(ACLMessage.REQUEST);
//			msg.addReceiver(new AIDGetter().getAID(myAgent, "UI_Agent"));
//			msg.setOntology("agent");
//			myAgent.send(msg);
		}else {
			block();
		}
	}
}
