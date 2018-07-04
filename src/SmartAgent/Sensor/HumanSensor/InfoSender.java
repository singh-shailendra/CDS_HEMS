package SmartAgent.Sensor.HumanSensor;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class InfoSender extends OneShotBehaviour {
	private Integer state = 0;
	private Boolean pre;
	private DFAgentDescription template = new DFAgentDescription();
	private ServiceDescription sd = new ServiceDescription();
	private AID aid;

	public InfoSender(Agent a, Boolean pre) {
		super(a);

//		recvSet();
		this.pre = pre;
	}

	public void recvSet() {

		try {
			sd.setType("Data_Analysis_Agent");
			template.addServices(sd);
			this.aid = DFService.search(myAgent, template)[0].getName();
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void action() {

		// TODO Auto-generated method stub

		// sd.setType("Data_Analysis_Agent");

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("da", AID.ISLOCALNAME));
		msg.setOntology("sensor");
		msg.setInReplyTo("human");
		// msg.setLanguage("mid");
		msg.setContent(pre ? "on" : "off");
		myAgent.send(msg);

		// MessageTemplate mt =
		// MessageTemplate.MatchPerformative(ACLMessage.CFP);

	}

}
