package SmartAgent.Sensor.MotionSensor;

import java.util.Random;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;

import java.util.Date;
import java.util.Vector;

public class Motion_Sensor_Agent  extends Agent {
	
	protected void setup() {
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("Motion_Sensor_Agent"); 
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			// Fill the REQUEST message
	  		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	  		msg.addReceiver(new AID((String) "Airconditioner_Agent", AID.ISLOCALNAME));
	  		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			msg.setContent("cool-mode");
			
			addBehaviour(new AchieveREInitiator(this, msg) {
				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent "+ inform.getSender().getName() +" successfully performed the requested action");
				}
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+ refuse.getSender().getName() +" refused to perform the requested action");
				}
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the receiver
						// does not exist
						System.out.println("Responder does not exist");
					}
					else {
						System.out.println("Agent "+failure.getSender().getName()+" failed to perform the requested action");
					}
				}
			} );
		} catch (FIPAException e) {
			doDelete();
		}
		
		
	}
	
}
