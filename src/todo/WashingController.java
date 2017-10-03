package todo;

import done.*;

public class WashingController implements ButtonListener {	
	TemperatureController tempController;
	WaterController waterController;
	SpinController spinController;
	TimeController timeController;
	private AbstractWashingMachine mach;
	private double speed;
	
    public WashingController(AbstractWashingMachine mach, double speed) {
    	tempController = new TemperatureController(mach, speed);
    	tempController.start();
    	waterController = new WaterController(mach, speed);
    	waterController.start();
    	spinController = new SpinController(mach, speed);
    	spinController.start();
    	timeController = new TimeController(mach, speed);
    	timeController.start();
    	this.mach = mach;
    	this.speed = speed;
    }

    public void processButton(int theButton) {
		if(theButton == 1) {
			System.out.println("Button 1");
			WashingProgram prog = new WashingProgram1(mach, speed, tempController, waterController, spinController, timeController);
			prog.start();
		}
    }
}
