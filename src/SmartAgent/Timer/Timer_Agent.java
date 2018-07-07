package SmartAgent.Timer;

import java.util.Random;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class Timer_Agent extends Agent {

	  protected void setup() {
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("Timer_Agent"); 
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
	    System.out.println("Agent "+ getLocalName() +" started.");
		try {
			DFService.register(this, dfd);
			//Every 6 seconds tick
		    addBehaviour(new TickerBehaviour(this, 6000) {
		        protected void onTick() {
		        	Random r = new Random();
					int Low = 1;
					int High = 10;
					int Result = r.nextInt(High-Low) + Low;
					if (Result % 2 == 0) {
						//send an ACL message to Motion_Sensor_Agent
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.addReceiver(new AID("Temperature_Sensor_Agent", AID.ISLOCALNAME));
						msg.setContent("temperature");
						myAgent.send(msg);
					}else {
						ACLMessage msg = myAgent.receive();
						if(msg != null){
							String content = msg.getContent();
							System.out.println("Temperature: "+ content);
						}
					}
					System.out.println("Agent "+ myAgent.getLocalName() + ": tick="+getTickCount());
		        } 
		      });
		    // Add the WakerBehaviour (wakeup-time 60 secs)
		    addBehaviour(new WakerBehaviour(this, 60000) {
		      protected void handleElapsedTimeout() {
		        System.out.println("Agent "+ myAgent.getLocalName() +": It's wakeup-time. Bye...");
		        myAgent.doDelete();
		      } 
		    });
		} catch (FIPAException e) {
			doDelete();
		}


	  } 
	
}
//-gui -port 12343 Temperature_Sensor_Agent:Temperature_Sensor_Agent;Timer:Timer_Agent
//-gui -port 12343 Time_Agent:Timer_Agent;Temperature_Sensor_Agent:Temperature_Sensor_Agent;Airconditioner_Agent:Airconditioner_Agent;Motion_Sensor_Agent:Motion_Sensor_Agent