package SmartAgent.Appliance;
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
public class airconditioner_agent  extends Agent {

	private class AirconditionerBehaviour extends Behaviour {

		private boolean finished = false;
		
		public AirconditionerBehaviour(Agent a) {
			super(a);
		}
		public void action() {
			//Implement the behaviour here
		}
		
		public  boolean done() {  
			return finished;  
			}
	} 


	protected void setup() {
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("Airconditioner_Agent"); 
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			AirconditionerBehaviour Airconditioner = new  AirconditionerBehaviour(this);
			addBehaviour(Airconditioner);
		} catch (FIPAException e) {
			doDelete();
		}
	}
}
