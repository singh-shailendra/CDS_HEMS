package SmartAgent.UI;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class UI_Agent extends Agent{
		private boolean flag = true;
		private Agent myAgent = this;
		public UI_Agent() {
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void setup() {
			eventTrigger();

		}
		
		public void eventTrigger() {
			new Thread(()-> {
				while(flag) {
					if(Math.random()>0.8) {
						Integer r = new Random().nextInt(3)+1;
						this.addBehaviour(new OneShotBehaviour() {
							
							@Override
							public void action() {
								ACLMessage msg = new ACLMessage();
								msg.addReceiver(new AID("SmartLight_Agent",AID.ISLOCALNAME));
								msg.setPerformative(ACLMessage.REQUEST);
								msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
								
								msg.setContent(r.toString());
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
