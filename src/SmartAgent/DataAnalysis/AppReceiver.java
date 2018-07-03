package SmartAgent.DataAnalysis;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AppReceiver extends CyclicBehaviour{
	
	public AppReceiver(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
		
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive();
		if(msg != null) {
			
		}else {
			block();
		}
	}
}
