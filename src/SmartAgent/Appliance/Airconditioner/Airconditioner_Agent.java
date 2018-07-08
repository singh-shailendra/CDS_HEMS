package SmartAgent.Appliance.Airconditioner;

import java.util.Random;
import jade.core.*;
import jade.core.behaviours.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Airconditioner_Agent  extends Agent {
	private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

	public static String mode = "mid";

	@Override
	public void setup() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Airconditioner_Agent");
		sd.setName("JADE-Airconditioner-Agent");
		sd.setOwnership("Project_Group_5");
		System.out.println(getAID().getName());
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
			System.out.println(getName() + " registed");
			addBehaviour(tbf.wrap(new Receiver(this)));
			addBehaviour(new OneShotBehaviour() {
				@Override
				public void action() {
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					msg.setOntology("appliance");
					msg.addReceiver(new AID((String) "Data_Analysis_Agent", AID.ISLOCALNAME));
//					msg.setContent("mid");
					System.out.println("Agent "+ myAgent.getLocalName() + ": operating mode: mid" );
					send(msg);
				}
			});
			addBehaviour(new TickerBehaviour(this, 6000) {
			    protected void onTick() {
					// TODO Auto-generated method stub
						Random r = new Random();
						int Low = 1;
						int High = 999;
						int Result = r.nextInt(High-Low) + Low;
						ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
						msg.setOntology("appliance");
						msg.addReceiver(new AID((String) "Data_Analysis_Agent", AID.ISLOCALNAME));
						if (Result % 2 == 0){
							//humid mode
							msg.setContent("humid");
							System.out.println("Agent "+ myAgent.getLocalName() + ": operating mode: humid" );
						}
						else if((Result % 3 == 0)) {
							//mid
							msg.setContent("mid");
							System.out.println("Agent "+ myAgent.getLocalName() + ": operating mode: mid" );
						}
						else if((Result % 5 == 0)) {
							//high
							msg.setContent("mid");
							System.out.println("Agent "+ myAgent.getLocalName() + ": operating mode: high" );
						}
						else {
							//low
							msg.setContent("mid");
							System.out.println("Agent "+ myAgent.getLocalName() + ": operating mode: low" );
						}
						myAgent.send(msg);
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
