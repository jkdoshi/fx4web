/*
 * COPYRIGHT NOTICE:
 * 
 * This software is copyright (c) Jitesh Doshi <jitesh@doshiland.com> 2006. It
 * is made available free for use without any warranties. You are given
 * permission to use it as long as this copyright notice is preserved unmodified.
 * See http://www.doshiland.com/copyright for a complete license.
 */

package com.doshiland.servlet.conversation;

import java.util.HashMap;

/**
 * A conversation is simply an extended map that keeps track of when it was last
 * accessed (for expiration purposes).
 * 
 * @author <a href="mailto:jitesh@doshiland.com">Jitesh Doshi</a>
 */
@SuppressWarnings("serial")
public class Conversation extends HashMap<String, Object> {
	private long lastAccessTimestamp;

	/**
	 * Updates the last access timestamp of this object to the current system
	 * time. This helps in enforcing conversation timeouts.
	 * 
	 * @see #isExpired(long)
	 */
	public void touch() {
		lastAccessTimestamp = System.currentTimeMillis();
	}

	/**
	 * Tells if the given timeout has been exceeded since the object was last
	 * touched.
	 * 
	 * @param timeout
	 *            timeout period in milliseconds
	 * @return true if a period longer than the timeout has passed since the
	 *         object was last touched.
	 * @see #touch()
	 */
	public boolean isExpired(long timeout) {
		return System.currentTimeMillis() > lastAccessTimestamp + timeout;
	}
}
