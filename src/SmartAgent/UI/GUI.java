package SmartAgent.UI;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class GUI extends JFrame {
	private Agent myAgent;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public AID[] getAIDs(String name) {
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		AID[] aids = null;
		sd.setName(name);
		template.addServices(sd);

		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			aids = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				aids[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return aids;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					GUI frame = new GUI();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI(Agent myAgent) {
		this.myAgent = myAgent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		AID[] aids = getAIDs("JADE-Agent");
		for (AID aid : aids) {
			JButton jb = new JButton(aid.getLocalName());
			jb.setSize(80, 80);
			jb.addActionListener((e) -> {
				new ChildGUI(myAgent, aid).setVisible(true);
			});
			this.getContentPane().add(jb);

		}

		
	}
	
	

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
