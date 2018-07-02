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
	

	public InfoSender(Agent a, Boolean pre) {
		super(a);
		this.pre = pre;
	}

	@Override
	public void action() {
		System.out.println("sending message" + pre.toString());
		// TODO Auto-generated method stub
		
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
//		sd.setType("Data_Analysis_Agent");
		sd.setType("Human_Sensor_Agent");
		template.addServices(sd);
		
		try {
			AID aid = DFService.search(myAgent, template)[0].getName();
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(aid);
			msg.setOntology("test");
			msg.setLanguage("english");
			msg.setContent(pre.toString());
			myAgent.send(msg);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
		
		
	}

}
