/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980813 Created
 * PP 990924 Revised
 */

package todo;

import se.lth.cs.realtime.event.*;

public class TimeEvent extends RTEvent {
	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------- CONSTRUCTOR

	
	public TimeEvent(Object source, int mode, double time) {
		super(source);

		myMode   = mode;
		myTime  = time;
	}

	// -------------------------------------------------------- PUBLIC METHODS

	public int getMode() {
		return myMode;
	}

	public double getTime() {
		return myTime;
	}

	// ------------------------------------------------------ PUBLIC CONSTANTS

	public static final int TIME_OFF = 0;

	public static final int TIME_ON  = 1;


	// -------------------------------------------- PRIVATE INSTANCE VARIABLES

	private int myMode;

	private double myTime;
}
