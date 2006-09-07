/*
 * Copyright (c) 2006 Jitesh Doshi
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.doshiland.fx4web.conversation;

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
