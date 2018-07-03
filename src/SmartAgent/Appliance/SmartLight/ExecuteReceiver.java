package SmartAgent.Appliance.SmartLight;

import java.util.Random;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class ExecuteReceiver extends AchieveREResponder{
	private static MessageTemplate template = MessageTemplate.and(
	  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
	  		MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
	
	public ExecuteReceiver(Agent a) {
		this(a, template);
	}
	public ExecuteReceiver(Agent a, MessageTemplate mt) {
		super(a, mt);
		// TODO Auto-generated constructor stub
	}
	
	protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
		System.out.println("Agent "+myAgent.getLocalName()+": REQUEST received from "+request.getSender().getName()+". Action is "+request.getContent());
		if (checkAction()) {
			System.out.println("Agent "+myAgent.getLocalName()+": Agree");
			ACLMessage agree = request.createReply();
			agree.setPerformative(ACLMessage.AGREE);
			return agree;
		}
		else {
			// We refuse to perform the action
			System.out.println("Agent "+myAgent.getLocalName()+": Refuse");
			throw new RefuseException("check-failed");
		}
	}
	
	protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
		if (performAction()) {
			if(request.getContent().equals("on")) {
				System.out.println("light is successfully turned on");
				System.out.println("light is running on " +request.getLanguage()+" mode");
			}
			else {
				System.out.println("light is successfully turned off");
			}
			ACLMessage inform = request.createReply();
			inform.setPerformative(ACLMessage.INFORM);
			return inform;
		}
		else {
			System.out.println("Agent "+myAgent.getLocalName()+": Action failed");
			throw new FailureException("unexpected-error");
		}

	}
	
	private Boolean checkAction() {
		return Math.random()>0.1;
	}
	
	private Boolean performAction() {
		return Math.random()>0.1;
	}
	
	
}
