package SmartAgent.UoT;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
public class UoT_Agent extends Agent{
private Boolean flag = true;
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("UoT_Agent");
		sd.setName("JADE-UoT-Agent");
		sd.setOwnership("Project_Group_5");
		System.out.println(getAID().getName());
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
			System.out.println(getName() + " registed");
			eventTrigger();
		}
		catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public void eventTrigger() {
		new Thread(()-> {
			while(flag) {
				try {
					Thread.currentThread().sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(Math.random()>0.5) {
					System.out.println("UoT change");
					this.addBehaviour(new OneShotBehaviour() {
						
						@Override
						public void action() {
							ACLMessage msg = new ACLMessage();
							msg.addReceiver(new AID("Data_Analysis_Agent",AID.ISLOCALNAME));
							msg.setPerformative(ACLMessage.REQUEST);
							msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
							msg.setOntology("UoT");
							msg.setContent("UoT information");
							myAgent.send(msg);
						}
					});
				}
			}
		}).start();
	}
	
	@Override
	protected void takeDown() {
			// TODO Auto-generated method stub
		this.flag = false;
	}
}
