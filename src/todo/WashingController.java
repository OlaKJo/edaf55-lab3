package todo;

import done.*;

public class WashingController implements ButtonListener {
	TemperatureController tempController;
	WaterController waterController;
	SpinController spinController;
	private AbstractWashingMachine mach;
	private double speed;
	private WashingProgram prog;
	
	public WashingController(AbstractWashingMachine mach, double speed) {
		tempController = new TemperatureController(mach, speed);
		tempController.start();
		waterController = new WaterController(mach, speed);
		waterController.start();
		spinController = new SpinController(mach, speed);
		spinController.start();
		this.mach = mach;
		this.speed = speed;
	}

	public void processButton(int theButton) {

		switch (theButton) {
		case 0:
			if (prog != null) {
				prog.interrupt();
			}
			prog = new WashingProgram0(mach, speed, tempController, waterController, spinController);
			prog.start();
			break;
		case 1:
			prog = new WashingProgram1(mach, speed, tempController, waterController, spinController);
			prog.start();
			break;
		case 2:
			prog = new WashingProgram2(mach, speed, tempController, waterController, spinController);
			prog.start();
			break;
		case 3:
			prog = new WashingProgram3(mach, speed, tempController, waterController, spinController);
			prog.start();
			break;
		default:
			break;
		}
	}
}
