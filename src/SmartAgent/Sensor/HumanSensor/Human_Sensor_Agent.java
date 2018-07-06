package SmartAgent.Sensor.HumanSensor;

import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Human_Sensor_Agent extends Agent {
	private Boolean threadFlag = true;
	private Agent myAgent = this;
	private Boolean presence = false;
	private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();

	@Override
	public void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Human_Sensor_Agent");
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
			SensorSetup();
//			 Behaviour recv = new InfoReceiver(this);
//			 addBehaviour(tbf.wrap(recv));
			// addBehaviour(recv);

		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void takeDown() {
		try {
			DFService.deregister(this);
			tbf.interrupt();
			this.threadFlag = false;
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void SensorSetup() {
		/**
		 * sensor simulation trigger every 5 sec
		 */
		new Thread(() -> {
			// Thread.currentThread().setName("sensor");
			// while(threadFlag) {
			// System.out.println("sensor running...");
			try {

				Thread.currentThread().sleep(10);
				presence = (presence == false ? true : false);
				this.infoTrigger();
				this.threadFlag = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
			}

			// }
		}).start();
	}

	protected void infoTrigger() {
		System.out.println("human detected");
		addBehaviour(new InfoSender(this, presence));

	}

}
