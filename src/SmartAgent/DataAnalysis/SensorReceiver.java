package SmartAgent.DataAnalysis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SensorReceiver extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
	public SensorReceiver(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
	}
	@Override
	public void action() {
		System.out.println("sensor receiver is running...");
		AID aid;
		// TODO Auto-generated method stub
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		ACLMessage msg = myAgent.receive(mt);
		String recver = msg.getInReplyTo();
	
		if(msg != null) {
			
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			if(recver.equals("human")) {
				sd.setType("SmartLight_Agent");
				template.addServices(sd);
				try {
					aid = DFService.search(myAgent, template)[0].getName();
					myAgent.addBehaviour(new OneShotBehaviour() {
						@Override
						public void action() {
							System.out.println("human detected");
							request.addReceiver(aid);
							request.setContent(msg.getContent());
							request.setLanguage("low");
							myAgent.send(request);
							}
						});
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					
				
				
				
				
			}else if(recver.equals("climate")) {
				myAgent.addBehaviour(new OneShotBehaviour() {
					@Override
					public void action() {
						// TODO Auto-generated method stub
						System.out.println("climate detected");
					
					}
				});
			}else {
				myAgent.addBehaviour(new OneShotBehaviour() {
					@Override
					public void action() {
					// TODO Auto-generated method stub
						System.out.println("weather detected");
					}
				});
			}
		}
		else {
			block();
		}
		
		
		
	}
}
