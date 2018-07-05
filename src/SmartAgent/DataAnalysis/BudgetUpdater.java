package SmartAgent.DataAnalysis;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BudgetUpdater extends CyclicBehaviour{
	MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
										.MatchConversationId("budgetUpdate");
	public BudgetUpdater(Agent a) {
		// TODO Auto-generated constructor stub
		super(a);
		System.out.println("budgetUpdater is running...");
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage msg = myAgent.receive(mt);
		String app = null;
		if(msg!=null) {
			Boolean add = msg.getEncoding().equals("false")?false:true;
			if(msg.getContent().equals("light")) {
				app = Predition.light;
			}
			else {
				app = Predition.airconditioner;
			}
			Predition p = Data_Analysis_Agent.predition;
			p.setCurrCost(p.getUnit(app, msg.getLanguage()), add);
			System.out.println("budgetUpdate success!current cost is: "+p.getCurrCost()+"$");
		}
		else {
			block();
		}
	}
}
