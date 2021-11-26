package com.indocosmo.pos.process.sync;

import java.util.UUID;

/*
 * Abstract class for synchronizing data.
 *  
 * @author Ramesh S
 * @since 12th July 2012
 */

abstract class ImplDataSynchronization implements Runnable {

	private String threadNumber = null; // Holds thread number
	protected int retryCount; // Variable to track retry count for synchronization

	// Constructor to synch multiple rows
	public ImplDataSynchronization() {
		// Updating values for the thread
		this.threadNumber = UUID.randomUUID().toString();
		this.retryCount = 0;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	// Prints the name of the Thread
	public final String toString() {
		return this.threadNumber;
	}
}
