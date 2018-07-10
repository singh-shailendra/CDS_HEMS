package SmartAgent.Appliance.SmartLight;

import Util.AIDGetter;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class SmartLight_Agent extends Agent {
	private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

	public static String mode = "mid";

	@Override
	public void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("SmartLight_Agent");
		sd.setName("JADE-Agent");
		sd.setOwnership("Project_Group_5");
		System.out.println(getAID().getName());
		dfd.addServices(sd);
		new AID("da", AID.ISLOCALNAME);
		try {
			DFService.register(this, dfd);
			System.out.println(getName() + " registed");
			addBehaviour(tbf.wrap(new Executor(this)));
			addBehaviour(tbf.wrap(new UIResponder(this)));
			addBehaviour(tbf.wrap(new OptimizerTrigger(this)));
			addBehaviour(new OneShotBehaviour() {
				@Override
				public void action() {
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setOntology("appliance");
					msg.addReceiver(new AIDGetter().getAID(this.myAgent, "Data_Analysis_Agent"));
					send(msg);
				}
			});
			
		} catch (FIPAException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		try {
			tbf.interrupt();
			DFService.deregister(this);

		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}
