package SmartAgent.Appliance.Airconditioner;

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
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;

import java.util.Date;
import java.util.Vector;


/**
 * This agent implements function of a temperature sensor. 
 * It Wait for ACLMessages, if  a REQUEST message is received containing the string 
 * "temperature" within the content then it replies with an INFORM message whose 
 * content will have current temperature 
 */
public class Airconditioner_Agent_V1  extends Agent {

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
			  	System.out.println("Agent: "+getLocalName()+" waiting for requests...");
			  	MessageTemplate template = MessageTemplate.and(
		  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
		  		MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );
			  		
				addBehaviour(new AchieveREResponder(this, template) {
					protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
						System.out.println("Agent: "+getLocalName()+": REQUEST received from "+request.getSender().getName()+". Action is "+request.getContent());
						Random r = new Random();
						int Low = 1;
						int High = 999;
						int Result = r.nextInt(High-Low) + Low;
						if (Result % 3 == 0){
							// We agree to perform the action. Note that in the FIPA-Request
							// protocol the AGREE message is optional. Return null if you
							// don't want to send it.
							System.out.println("Agent: "+getLocalName()+": Agree!");
							ACLMessage agree = request.createReply();
							agree.setPerformative(ACLMessage.AGREE);
							return agree;
						}
						else {
							// We refuse to perform the action
							System.out.println("Agent: "+getLocalName()+": Refuse!");
							throw new RefuseException("check-failed");
						}
					}
						
					protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
						Random r = new Random();
						int Low = 1;
						int High = 999;
						int Result = r.nextInt(High-Low) + Low;
						if (Result % 3 == 0){
							System.out.println("Agent: "+getLocalName()+": Action successfully performed!");
							ACLMessage inform = request.createReply();
							inform.setPerformative(ACLMessage.INFORM);
							return inform;
						}
						else {
							System.out.println("Agent: "+getLocalName()+": Action failed!");
							throw new FailureException("unexpected-error");
						}	
					}
				} 
			);
		} catch (FIPAException e) {
			doDelete();
		}
	}
}
