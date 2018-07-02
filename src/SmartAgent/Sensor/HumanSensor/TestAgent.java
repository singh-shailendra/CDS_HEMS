package SmartAgent.Sensor.HumanSensor;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class TestAgent extends Agent {
	@Override
	public void setup() {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Human_Sensor_agent");
		template.addServices(sd);
		try {
			AID a = DFService.search(this, template)[0].getName();
			System.out.println(a.getName());
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(a);
			msg.setOntology("test");
			msg.setLanguage("english");
			msg.setContent("hi");
			this.send(msg);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
