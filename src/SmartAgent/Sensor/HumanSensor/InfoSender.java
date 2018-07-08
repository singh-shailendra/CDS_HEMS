package SmartAgent.Sensor.HumanSensor;

import SmartAgent.DataAnalysis.Predition;
import Util.AIDGetter;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class InfoSender extends OneShotBehaviour {
	private Integer state = 0;
	private Boolean pre;

	private AID aid;

	public InfoSender(Agent a, Boolean pre) {
		super(a);

		// recvSet();
		this.pre = pre;
	}

	public AID getAID() {
		AID aid = null;
		try {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("Data_Analysis_Agent");
			template.addServices(sd);
			aid = DFService.search(myAgent, template)[0].getName();
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aid;
	}

	@Override
	public void action() {

		// TODO Auto-generated method stub

		// sd.setType("Data_Analysis_Agent");

//		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
//		msg.addReceiver(getAID());
//		msg.setOntology("sensor");
//		msg.setInReplyTo("human");
//		msg.setContent(pre ? "on" : "off");
//		myAgent.send(msg);
		AID[] aids = new AIDGetter().getAIDs(myAgent, "SmartLight_Agent");
		for (AID aid : aids) {
			try {
				Thread.currentThread().sleep(3000);
				ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
				request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
				request.setOntology("da");
				request.addReceiver(aid);
				request.setContent(pre ? "on" : "off");
				request.setLanguage("mid");
				myAgent.send(request);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
