package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes

	AbstractWashingMachine mach;
	private int currentMode;
	private boolean busy;
	private TemperatureEvent ev;
	private boolean sentAck;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (25000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {

		

		if (!busy) {

			ev = (TemperatureEvent) mailbox.doFetch();
			currentMode = ev.getMode();
			sentAck = false;
			
			switch (currentMode) {
			case TemperatureEvent.TEMP_IDLE:
				System.out.println("ERROR");
				mach.setHeating(false);
				break;
			case TemperatureEvent.TEMP_SET:
				mach.setHeating(true);
				busy = true;
				break;
			default:
				break;
			}
		} else {
			
			switch (currentMode) {
			case TemperatureEvent.TEMP_IDLE:
				mach.setHeating(false);
				busy = false;
				break;
			case TemperatureEvent.TEMP_SET:
				double goalTemp = ev.getTemperature();
				if(mach.getTemperature() > (goalTemp - 1.75)) {
					mach.setHeating(false);
					if(!sentAck) {
						((RTThread)ev.getSource()).putEvent(new RTEvent(this));
						sentAck = true;
					}
				} else {
					mach.setHeating(true);
				}
					
				break;
			default:
				break;
			}
		}
	}
}
