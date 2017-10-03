/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980812 Created
 * PP 990924 Revised
 */

package todo;

import done.AbstractWashingMachine;

/**
 * Program 3 of washing machine. Does the following:
 * <UL>
 * <LI>Switches off heating
 * <LI>Switches off spin
 * <LI>Pumps out water
 * <LI>Unlocks the hatch.
 * </UL>
 */
class WashingProgram1 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param mach
	 *            The washing machine to control
	 * @param speed
	 *            Simulation speed
	 * @param tempController
	 *            The TemperatureController to use
	 * @param waterController
	 *            The WaterController to use
	 * @param spinController
	 *            The SpinController to use
	 */
	public WashingProgram1(AbstractWashingMachine mach, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController, TimeController timeController) {
		super(mach, speed, tempController, waterController, spinController, timeController);
	}

	// ---------------------------------------------------------- PUBLIC METHODS

	/**
	 * This method contains the actual code for the washing program. Executed
	 * when the start() method is called.
	 */
	protected void wash() throws InterruptedException {

		/*
		 * Program 1 Color wash: Lock the hatch, let water into the machine,
		 * heat to 60C, keep the temperature for 30 minutes, drain, rinse 5
		 * times 2 minutes in cold water, centrifuge for 5 minutes and unlock
		 * the hatch
		 */

		// Lock
		myMachine.setLock(true);

		if (!myMachine.isLocked()) {
			// ERROR
		}

		// Fill
		fillAndIdle(10);


		// Heat to 60 C
		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, 60.0));
		System.out.println("waiting for ack");
		mailbox.doFetch();
		System.out.println("received ack");


		// turn on spin
		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
//
//		// turn on timer
//		myTimeController.putEvent(new TimeEvent(this, TimeEvent.TIME_ON, 30.0));
//		mailbox.doFetch();
//		
//		myTimeController.putEvent(new TimeEvent(this, TimeEvent.TIME_OFF, 0.0));
//		
//
//		// Switch of temp regulation
//		myTempController.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
//
//		// Drain and Idle
//		drainAndIdle();		
//		
//
//		for (int i = 0; i < 5; i++) {
//
//			// Fill
//			myWaterController.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 10.0));
//			mailbox.doFetch();
//
//			// turn on timer
//			myTimeController.putEvent(new TimeEvent(this, TimeEvent.TIME_ON, 2.0));
//			mailbox.doFetch();
//
//			drainAndIdle();
//		}
//		
//		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
//		
//		myTimeController.putEvent(new TimeEvent(this, TimeEvent.TIME_ON, 5));
//		mailbox.doFetch();
//		
//		myTimeController.putEvent(new TimeEvent(this, TimeEvent.TIME_OFF, 0.0));
//		
//		mySpinController.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
//		
//		// Unlock
//		myMachine.setLock(false);
	}
}
