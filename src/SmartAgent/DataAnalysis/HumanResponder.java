package SmartAgent.DataAnalysis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class HumanResponder extends OneShotBehaviour{
	private ACLMessage request;
	private ACLMessage msg;
	private AID aid;
	public HumanResponder(Agent a, AID aid, ACLMessage msg, ACLMessage request) {
		// TODO Auto-generated constructor stub
		super(a);
		this.request = request;
		this.msg = msg;
		this.aid = aid;
	}
	@Override
	public void action() {
		System.out.println("sending operation to light "+aid.getName());
		if(Data_Analysis_Agent.predition.isOverbudget(Predition.light, Predition.mid, true)) {
			request.setEncoding("overbudget");
			
		}
		else {
			request.setEncoding("underbudget");
		}
		request.addReceiver(aid);
		request.setContent(msg.getContent());
		request.setLanguage(Predition.mid);
		myAgent.send(request);
		request.clearAllReceiver();

	}
}
