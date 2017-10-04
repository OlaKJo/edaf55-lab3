package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

public class SpinController extends PeriodicThread {

	AbstractWashingMachine mach;
	private int currentMode;
	private SpinEvent ev;
	private long startTime;
	private int currentDir = AbstractWashingMachine.SPIN_LEFT;

	public SpinController(AbstractWashingMachine mach, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {

		ev = (SpinEvent) mailbox.tryFetch();
		if (ev != null) {
			currentMode = ev.getMode();
		}

		switch (currentMode) {
		case SpinEvent.SPIN_OFF:
			mach.setSpin(AbstractWashingMachine.SPIN_OFF);
			break;
		case SpinEvent.SPIN_SLOW:
			long currentTime = System.currentTimeMillis();
			if (currentTime > (startTime + 60000)) {
				startTime = currentTime;
				currentDir = currentDir % 2 + 1;
				mach.setSpin(currentDir);
			}
			break;
		case SpinEvent.SPIN_FAST:
			if (mach.getWaterLevel() == 0) {
				mach.setSpin(AbstractWashingMachine.SPIN_FAST);
			}
			break;
		default:
			break;
		}
	}
}
