package todo;


import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;


public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes
	
	AbstractWashingMachine mach;
	private int currentMode;
	private boolean busy;
	private WaterEvent ev;

	public WaterController(AbstractWashingMachine mach, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.mach = mach;
	}

	public void perform() {
		System.out.println("Performing water stuff");
		
		if (!busy) {
			System.out.println("Not busy");
			ev = (WaterEvent)mailbox.doFetch();
			
			currentMode = ev.getMode();
			
			switch (currentMode) {
			case WaterEvent.WATER_DRAIN:
				currentMode = WaterEvent.WATER_DRAIN;
				mach.setDrain(true);
				busy = true;
				break;
			case WaterEvent.WATER_FILL:
				mach.setFill(true);
				busy = true;
				break;
			case WaterEvent.WATER_IDLE:
				mach.setFill(false);
				mach.setDrain(false);
				break;
				
			default:
				System.out.println("default first loop!!");
				
				break;
			}
		} else {
			System.out.println("Busy");
			switch (currentMode) {
			case WaterEvent.WATER_DRAIN:
				if (mach.getWaterLevel() == 0) {
					((RTThread)ev.getSource()).putEvent(new RTEvent(this));
					busy = false;
				}
				break;
			case WaterEvent.WATER_FILL:
				if (mach.getWaterLevel() >= 0.5) {
					((RTThread)ev.getSource()).putEvent(new RTEvent(this));
					busy = false;
				}
				break;
			case WaterEvent.WATER_IDLE:	
				System.out.println("WATER_IDLE!!");
				break;
				
			default:
				System.out.println("default second loop!!");
				break;
			}
			
		}
			
	}
}
