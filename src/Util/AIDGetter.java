package Util;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AIDGetter {
	public AIDGetter() {
		// TODO Auto-generated constructor stub
	}
	public AID getAID(Agent myAgent, String type) {
		AID aid = null;
		try {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType(type);
			template.addServices(sd);
			aid = DFService.search(myAgent, template)[0].getName();
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aid;
	}
	
	public AID[] getAIDs(Agent myAgent, String type) {
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

}
