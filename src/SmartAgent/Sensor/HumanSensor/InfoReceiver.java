package SmartAgent.Sensor.HumanSensor;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.SimpleAchieveREInitiator;

public class InfoReceiver extends CyclicBehaviour{
	public InfoReceiver(Agent a) {
		super(a);
	}
	
	@Override
	public void action() {
		ACLMessage msg = myAgent.receive();

		if(msg!=null) {
			System.out.println("get");
		}
		else {
			System.out.println("blocking");
			block();
		}
		
	}
	
	
}
