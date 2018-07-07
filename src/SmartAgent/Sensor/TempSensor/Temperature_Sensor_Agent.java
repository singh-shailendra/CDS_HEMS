package SmartAgent.Sensor.TempSensor;

import java.util.Random;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;

/**
 * This agent implements function of a temperature sensor. 
 * It Wait for ACLMessages, if  a REQUEST message is received containing the string 
 * "temperature" within the content then it replies with an INFORM message whose 
 * content will have current temperature 
 */
public class Temperature_Sensor_Agent extends Agent {

	private class WaitAndReplyBehaviour extends CyclicBehaviour {

		public WaitAndReplyBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			ACLMessage  msg = myAgent.receive();
			if(msg != null){
				ACLMessage reply = msg.createReply();

				if(msg.getPerformative()== ACLMessage.REQUEST){
					String content = msg.getContent();
					if ((content != null) && (content.indexOf("temperature") != -1)){
						reply.setPerformative(ACLMessage.INFORM);
						Random r = new Random();
						int Low = -30;
						int High = 40;
						int Result = r.nextInt(High-Low) + Low;
						reply.setContent(Integer.toString(Result));
					}
					else{
						reply.setPerformative(ACLMessage.REFUSE);
						reply.setContent("( UnexpectedContent ("+content+"))");
					}

				}
				else {
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
					reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+") )");   
				}
				send(reply);
			}
			else {
				block();
			}
		}
	} 


	protected void setup() {
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("Temperature_Sensor_Agent"); 
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			WaitAndReplyBehaviour ProvideTemperature = new  WaitAndReplyBehaviour(this);
			addBehaviour(ProvideTemperature);
		} catch (FIPAException e) {
			doDelete();
		}
	}
}
