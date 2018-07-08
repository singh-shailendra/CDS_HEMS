package SmartAgent.UI;

import javax.swing.JButton;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class UI_Agent extends Agent {
	public UI_Agent() {
		// TODO Auto-generated constructor stub
	}

	public AID[] getAIDs(String name) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		AID[] aids = null;
		sd.setName(name);
		template.addServices(sd);

		try {
			DFAgentDescription[] result = DFService.search(this, template);
			aids = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				aids[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return aids;
	}

	@Override
	public void setup() {
		// eventTrigger();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("UI_Agent");
		sd.setName("JADE-UI-Agent");
		sd.setOwnership("Project_Group_5");
		System.out.println(getAID().getName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			System.out.println(getName() + " registed");

			GUI gui = new GUI(this);
			gui.setVisible(true);
			addBehaviour(new CyclicBehaviour() {
				@Override
				public void action() {
					// TODO Auto-generated method stub
					MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST).MatchOntology("agent");
					ACLMessage msg = myAgent.receive(mt);
					if (msg != null) {
						gui.getContentPane().removeAll();
						AID[] aids = getAIDs("JADE-Agent");
						for (AID aid : aids) {
							JButton jb = new JButton(aid.getLocalName());
							jb.setSize(80, 80);
							jb.addActionListener((e) -> {
								new ChildGUI(myAgent, aid).setVisible(true);
							});
							gui.getContentPane().add(jb);

						}
						gui.repaint();

					} else {
						block();
					}

				}
			});

		} catch (FIPAException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub

	}

}
