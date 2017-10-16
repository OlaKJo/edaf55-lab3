package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes

	AbstractWashingMachine mach;
	private int currentMode;
	private TemperatureEvent ev;
	private boolean sentAck;

	public TemperatureController(AbstractWashingMachine mach, double speed) {
		super((long) (25000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		TemperatureEvent currentEvent = (TemperatureEvent) mailbox.tryFetch();
		if (currentEvent != null) {
			currentMode = currentEvent.getMode();
			sentAck = false;
			ev = currentEvent;
		}

		if (ev == null) {
			return;
		}

		switch (currentMode) {
		case TemperatureEvent.TEMP_IDLE:
			mach.setHeating(false);
			break;
		case TemperatureEvent.TEMP_SET:
			if (mach.getTemperature() > (ev.getTemperature() - 1.5)) {
				mach.setHeating(false);
				if (!sentAck) {
					((RTThread) ev.getSource()).putEvent(new AckEvent(this));
					sentAck = true;
				}
			} else {
				if (mach.getWaterLevel() > 0) {
					mach.setHeating(true);
				} else {
					mach.setHeating(false);
				}
			}
			break;

		default:
			System.out.println("default first loop!!");

			break;
		}

	}
}
