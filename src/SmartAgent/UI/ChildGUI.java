package SmartAgent.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Util.AIDGetter;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;

public class ChildGUI extends JFrame {
	private Agent myAgent;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ChildGUI frame = new ChildGUI();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public ChildGUI(Agent myAgent, AID aid) {
		this.myAgent = myAgent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("low");
		btnNewButton.setName("low");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg(aid, btnNewButton);
			}
		});
		btnNewButton.setBounds(15, 12, 183, 43);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("mid");
		btnNewButton_1.setName("mid");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg(aid, btnNewButton_1);
			}
		});
		btnNewButton_1.setBounds(224, 12, 183, 43);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("high");
		btnNewButton_2.setName("high");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg(aid, btnNewButton_2);
			}
		});
		btnNewButton_2.setBounds(15, 89, 183, 43);
		contentPane.add(btnNewButton_2);

		JButton btnOff = new JButton("off");
		btnOff.setName("off");

		btnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendMsg(aid, btnOff);
			}
		});
		btnOff.setBounds(224, 89, 183, 43);
		contentPane.add(btnOff);
	}

	public void sendMsg(AID aid, JButton jb) {
		ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
		request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		request.setOntology("da");
		request.addReceiver(aid);
		request.setContent(jb.getName().equals("off") ? "off" : "on");
		request.setLanguage(jb.getName());
		myAgent.send(request);
		System.out.println("sending operation change to " + jb.getName() + " mode");
	}
}
