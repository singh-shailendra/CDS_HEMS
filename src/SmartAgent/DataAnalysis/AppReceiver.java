package SmartAgent.DataAnalysis;

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
								"registered in"+
							Data_Analysis_Agent.appliances.get(msg.getSender().getName())
								+"mode");
			
		}else {
			block();
		}
	}
}
