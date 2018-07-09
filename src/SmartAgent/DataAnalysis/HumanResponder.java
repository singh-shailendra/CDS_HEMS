package SmartAgent.DataAnalysis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HumanResponder extends CyclicBehaviour {

	public HumanResponder(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
		// this.request = request;
		// this.msg = msg;
		// this.aid = aid;
	}

	@Override
	public void action() {
		// switch (step) {
		// case 0:
		// System.out.println(" ");
		// System.out.println("sending operation to light " + aid.getName());
		// if (Data_Analysis_Agent.predition.isOverbudget(Predition.light,
		// Predition.mid, true)) {
		// request.setEncoding("overbudget");
		//
		// } else {
		// request.setEncoding("underbudget");
		// }
		// request.addReceiver(aid);
		// request.setContent(msg.getContent());
		// request.setLanguage(Predition.mid);
		// myAgent.send(request);
		// request.clearAllReceiver();
		// step ++;
		// break;
		// case 1:
		// System.out.println("test");
		MessageTemplate mt = MessageTemplate.MatchOntology("SmartLight");
		ACLMessage reply = myAgent.receive(mt);
		// System.out.println("step1");
		if (reply != null) {
			
			if (!Data_Analysis_Agent.appliances.get(reply.getSender().getName()).equals(reply.getLanguage())) {
				System.out.println(reply.getSender().getLocalName() + "-operation confirmed");
				Predition p = Data_Analysis_Agent.predition;
				p.setCurrCost(p.getUnit(Predition.light, (String)Data_Analysis_Agent.appliances.get(reply.getSender().getName())), false);
				if (reply.getContent().equals("on")) {
					
					ACLMessage request = reply.createReply();
					request.setPerformative(ACLMessage.REQUEST);
					request.setOntology("budget");
					Data_Analysis_Agent.appliances.put(reply.getSender().getName(), reply.getLanguage());
					if (Data_Analysis_Agent.predition.isOverbudget(Predition.light, reply.getLanguage(), true)) {
						request.setEncoding("overbudget");

					} else {
						request.setEncoding("underbudget");
					}
					myAgent.send(request);
				} else {
					Data_Analysis_Agent.appliances.put(reply.getSender().getName(), Predition.off);
					System.out.println("The current budget is "+ p.getCurrCost()+ "$");

				}
			}
			System.out.println(reply.getSender().getName() + ":"
					+ Data_Analysis_Agent.appliances.get(reply.getSender().getName()));
			// System.out.println(Data_Analysis_Agent.appliances.size());

			// } else {r
			// request.setEncoding("underbudget");
			// }
			// step++;
			// break;
		} else {
			block();
		}
		//
		// }

	}

	// @Override
	// public boolean done() {
	// // TODO Auto-generated method stub
	// return step == 2;
	// }
}
