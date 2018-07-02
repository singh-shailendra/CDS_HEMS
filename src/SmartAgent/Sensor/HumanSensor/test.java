package SmartAgent.Sensor.HumanSensor;

import jade.core.behaviours.CyclicBehaviour;

public class test extends CyclicBehaviour{
	@Override
	public void action() {
		
		try {
			System.out.println("test");
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
