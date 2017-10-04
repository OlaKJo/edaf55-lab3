package todo;

import done.AbstractWashingMachine;
import se.lth.cs.realtime.PeriodicThread;
import se.lth.cs.realtime.RTThread;

public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes

	AbstractWashingMachine mach;
	private int currentMode;
	private boolean sentAck;
	private WaterEvent ev;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		WaterEvent currentEvent = (WaterEvent) mailbox.tryFetch();
		if (currentEvent != null) {
			currentMode = currentEvent.getMode();
			sentAck = false;
			ev = currentEvent;
		}

		if (ev == null) {
			return;
		}

		switch (currentMode) {
		case WaterEvent.WATER_DRAIN:
			currentMode = WaterEvent.WATER_DRAIN;
			// ensure that fill pump off when draining
			mach.setFill(false);
			mach.setDrain(true);
			if (mach.getWaterLevel() == 0 && !sentAck) {
				((RTThread) ev.getSource()).putEvent(new AckEvent(this));
				sentAck = true;
			}
			break;
		case WaterEvent.WATER_FILL:
			// ensure that drain pump off when filling
			mach.setDrain(false);
			mach.setFill(true);
			if (mach.getWaterLevel() > 0.5 && !sentAck) {
				((RTThread) ev.getSource()).putEvent(new AckEvent(this));
				sentAck = true;
			}
			break;
		case WaterEvent.WATER_IDLE:
			mach.setFill(false);
			mach.setDrain(false);
			break;

		default:
			System.out.println("default first loop!!");
			break;
		}

	}
}
