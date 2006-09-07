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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet Filter that implements server-side "conversations". Conversations are
 * contexts bigger than a "request" context but smaller than a "session"
 * context. A "conversation" can span requests, but not sessions. A session can
 * have many conversations active at the same time. Conversations, just like
 * sessions, also expire if they are not used for a timeout period.
 * <p>
 * See the <a href="package-summary.html">package summary</a> documentation for
 * detailed documentation.
 * </p>
 * 
 * @author <a href="mailto:jitesh@doshiland.com">Jitesh Doshi</a>
 */
public class ConversationFilter implements Filter {
    /**
     * The default name used for the request parameter indicating the currently
     * active conversation id.
     */
    public static final String CID_KEY = "_cid";

    private static final Log log = LogFactory.getLog(ConversationFilter.class);

    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("Initializing filter");
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String cid = getConversationID(request);
        HttpSession session = request.getSession();
        ConversationMap conversationMap = ConversationMap.getInstance(session);
        // move all objects from conversation to session
        conversationMap.loadConversation(cid);
        try {
            chain.doFilter(req, resp);
        } finally {
            // move non-shared objects from session to conversation
            conversationMap.unloadConversation(cid);
        }
    }

    public static String getConversationID(ServletRequest request) {
        return request.getParameter(CID_KEY);
    }

    public void destroy() {
        log.debug("Destroying filter");
    }
}
