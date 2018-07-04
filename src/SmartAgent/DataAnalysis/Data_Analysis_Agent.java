package SmartAgent.DataAnalysis;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ThreadedBehaviourFactory;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Data_Analysis_Agent extends Agent{
	
	private ThreadedBehaviourFactory tbf = new ThreadedBehaviourFactory();
	public AID [] lights = {
			new AID("sl1",AID.ISLOCALNAME),
			new AID("sl2",AID.ISLOCALNAME)
	};
	public AID ac = new AID("ac", AID.ISLOCALNAME);
	@Override
	public void setup() {
		
		// Registration with the DF 
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();   
		sd.setType("Data_Analysis_Agent"); 
		sd.setName(getName());
		sd.setOwnership("Project_Group_5");
		dfd.setName(getAID());
		dfd.addServices(sd);
		addBehaviour(tbf.wrap(new SensorReceiver(this))); //responder for sensor
		
//		addBehaviour(tbf.wrap(new AppReceiver(this)));
//		
//		addBehaviour(tbf.wrap(new UoTReceiver(this)));
		
		try {
			DFService.register(this, dfd);
			
		
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void takeDown() {
		// TODO Auto-generated method stub
		try {
			DFService.deregister(this);
			tbf.interrupt();
		} catch (FIPAException e) {
			// TODO: handle exception
		}
	}
}
