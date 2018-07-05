package SmartAgent.DataAnalysis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HumanResponder extends Behaviour {
	private ACLMessage request;
	private ACLMessage msg;
	private AID aid;
	private Integer step = 0;

	public HumanResponder(Agent a, AID aid, ACLMessage msg, ACLMessage request) {
		// TODO Auto-generated constructor stub
		super(a);
		this.request = request;
		this.msg = msg;
		this.aid = aid;
	}

	@Override
	public void action() {
		switch (step) {
		case 0:
			System.out.println(" ");
			System.out.println("sending operation to light " + aid.getName());
			if (Data_Analysis_Agent.predition.isOverbudget(Predition.light, Predition.mid, true)) {
				request.setEncoding("overbudget");

			} else {
				request.setEncoding("underbudget");
			}
			request.addReceiver(aid);
			request.setContent(msg.getContent());
			request.setLanguage(Predition.mid);
			myAgent.send(request);
			request.clearAllReceiver();
			step ++;
			break;
		case 1:
			MessageTemplate mt = MessageTemplate.MatchOntology("SmartLight");
			ACLMessage reply = myAgent.receive(mt);
//			System.out.println("step1");
			if(reply!=null) {
				System.out.println(aid.getName()+"-operation confirmed");
				step++;
				break;
			}
			
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return step == 2;
	}
}
