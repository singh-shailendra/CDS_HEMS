package SmartAgent.DataAnalysis;

import java.util.HashMap;
import java.util.Map;

public class Predition {

	public static final Integer total = 400;
	
	public static final String off = "off";
	
	public static final String low = "low";
	
	public static final String mid = "mid";
	
	public static final String high = "high";
	
	public static final String humid = "humid";
	
	public static final String airconditioner = "air";
	
	public static final String light = "light";
	
	public static Map consump = new HashMap<String, Map<String, Integer>>();
	
	public static Integer period = 2;
	
	private Integer curCost = 0;

	public Predition() {
		// TODO Auto-generated constructor stub
		Map light = new HashMap<String, Integer>();
		light.put("low", 40);
		light.put("mid", 50);
		light.put("high", 60);
		light.put("off", 0);
		
		Map air = new HashMap<String, Integer>();
		air.put("low", 80);
		air.put("mid", 100);
		air.put("high", 120);
		air.put("humid", 100);
		air.put("off", 0);
		
		consump.put(this.light, light);
		consump.put(this.airconditioner, air);
	}

	public Integer getCurrCost() {
		return this.curCost;
	}
	
	public  void setCurrCost(Integer unit, Boolean add) {
		if(add) {
			this.curCost += unit * period;
		}
		else {
			this.curCost += (-1)* unit * period;
		}
	}
	public Integer getUnit(String app, String mode) {
		Map<String, Integer> _app = (Map<String,Integer>)consump.get(app);
		return (Integer)_app.get(mode);
	}
	public Boolean isOverbudget(String app, String mode, Boolean add) {
//		Map<String, Integer> _app = (Map<String,Integer>)consump.get(app);
//		Integer unit = (Integer)_app.get(mode);
		Integer unit = this.getUnit(app, mode);
		synchronized(this.curCost){
			setCurrCost(unit, add);
		}
		System.out.println("Current price is "+ period + "$");
		System.out.println("Current cost is "+this.curCost);
		System.out.println(this.curCost>this.total?"overbudget!":"underbudget!");
		return this.curCost > this.total;
	}
}
