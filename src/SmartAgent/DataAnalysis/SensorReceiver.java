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

public class SensorReceiver extends CyclicBehaviour {
	private MessageTemplate mt = MessageTemplate.MatchPerformative(
			ACLMessage.INFORM).MatchOntology("sensor");
	Data_Analysis_Agent a;
	public AID [] lights = {
			new AID("sl1",AID.ISLOCALNAME),
			new AID("sl2",AID.ISLOCALNAME)
	};
	public AID ac = new AID("ac", AID.ISLOCALNAME);
	public SensorReceiver(Data_Analysis_Agent a) {
		
		// TODO Auto-generated constructor stub
		a = a;
		
		
		System.out.println("sensor receiver is running...");
	}
  
	public AID[] getAID(String type) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		AID[] aids = null;
		sd.setType(type);
		template.addServices(sd);
		

		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			aids = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				aids[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return aids;
	}

	@Override
	public void action() {
		
		// TODO Auto-generated method stub

		ACLMessage msg = myAgent.receive(mt);

		if (msg != null) {
			String recver = msg.getInReplyTo();
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			request.setOntology("da");
			
			if (recver.equals("human")) {
//				AID[] aids = this.getAID("SmartLight_Agent");
				a.addBehaviour(new OneShotBehaviour() {
					@Override
					public void action() {
						System.out.println("sending operation to light");
						for (AID aid : a.lights) {
							
							request.addReceiver(aid);
							request.setContent(msg.getContent());
							request.setLanguage("mid");
							myAgent.send(request);
						}
					}
				});

			} else if (recver.equals("climate")) {
				myAgent.addBehaviour(new OneShotBehaviour() {
					@Override
					public void action() {
						// TODO Auto-generated method stub
						System.out.println("climate detected");

					}
				});
			} else {
				myAgent.addBehaviour(new OneShotBehaviour() {
					@Override
					public void action() {
						// TODO Auto-generated method stub
						System.out.println("weather detected");
					}
				});
			}
		} else {
			block();
		}

	}
}
