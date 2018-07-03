package SmartAgent.UoT;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
public class UoT_Agent extends Agent{
private Boolean flag = true;
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		eventTrigger();
	}
	
	public void eventTrigger() {
		new Thread(()-> {
			while(flag) {
				if(Math.random()>0.8) {
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
		});
	}
	
	@Override
	protected void takeDown() {
			// TODO Auto-generated method stub
		this.flag = false;
	}
}
