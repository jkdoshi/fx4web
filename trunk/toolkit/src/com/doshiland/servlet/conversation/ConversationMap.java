/*
 * COPYRIGHT NOTICE:
 * 
 * This software is copyright (c) Jitesh Doshi <jitesh@doshiland.com> 2006. It
 * is made available free for use without any warranties. You are given
 * permission to use it as long as this copyright notice is preserved unmodified.
 * See http://www.doshiland.com/copyright for a complete license.
 */

package com.doshiland.servlet.conversation;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An extended Map that holds all conversations, keyed by the conversation id.
 * It also holds the Set of "shared" objects (objects that are attached to the
 * session, and not conversation).
 * 
 * @author <a href="mailto:jitesh@doshiland.com">Jitesh Doshi</a>
 */
@SuppressWarnings("serial")
public class ConversationMap extends HashMap<String, Conversation> {
	private static final Log log = LogFactory.getLog(ConversationMap.class);

    /**
     * The key used to store the ConversationMap instance in the HTTP session.
     */
    public static final String CONV_MAP_KEY = "_conversationMap";

    /**
     * Set of session scoped objects that are shared and not specific to a
     * conversation.
     */
    private Set<String> sharedObjects = new HashSet<String>();

    private HttpSession session;

    private ConversationMap(HttpSession session) {
        this.session = session;
    }

    /**
     * Extract the ConversationMap instance from the given session. Creates a
     * new one and attaches it to the session if not found.
     * 
     * @param session
     *            HTTP session that stores the ConversationMap
     * @return ConversationMap instance, containing all the Conversation
     *         instances, keyed by conversation id
     */
    public static ConversationMap getInstance(HttpSession session) {
        ConversationMap conversationMap = (ConversationMap) session
            .getAttribute(CONV_MAP_KEY);
        if (conversationMap == null) {
            log.debug("Creating");
            // first conversation
            conversationMap = new ConversationMap(session);
            session.setAttribute(CONV_MAP_KEY, conversationMap);
        }
        return conversationMap;
    }

    /**
     * Move all objects from a conversation into the session.
     * 
     * @param cid
     *            id of the conversation to load
     */
    public void loadConversation(String cid) {
        if (session == null || cid == null || cid.length() == 0) {
            return;
        }
        log.debug("Loading conversation: " + cid);
        Conversation conversation = get(cid);
        updateSharedObjects();
        // copy from conversation to session
        Set<Map.Entry<String, Object>> entries = conversation.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            log.debug("Loading object into conversation: " + entry.getKey());
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Remove all the non-shared objects from the current session and move them
     * into the given Conversation.
     * 
     * @param cid
     *            id of the Conversation to unload
     */
    public void unloadConversation(String cid) {
        if (session == null || cid == null || cid.length() == 0) {
            return;
        }
        Map<String, Object> conversation = get(cid);
        conversation.clear();
        // what's in session, is conversation-specific (unless shared)
        Enumeration nameEnum = session.getAttributeNames();
        while (nameEnum.hasMoreElements()) {
            String name = (String) nameEnum.nextElement();
            if (!sharedObjects.contains(name)) {
                log.debug("Unloading object from conversation: " + name);
                conversation.put(name, session.getAttribute(name));
                session.removeAttribute(name);
            }
        }
        removeExpired();
    }

    /**
     * Overridden so that non-existent conversations are automatically created
     * on-demand.
     */
    @Override
    public Conversation get(Object key) {
        Conversation conversation = super.get(key);
        if (conversation == null) {
            log.debug("Creating conversation id: " + key);
            // new conversation
            conversation = new Conversation();
            put((String) key, conversation);
        } else {
            log.debug("Loading conversation id: " + key);
        }
        conversation.touch();
        return conversation;
    }

    /**
     * Update the list of "shared" objects in session. Shared objects are those
     * that are attached to a session outside the scope of any conversation.
     */
    private void updateSharedObjects() {
        // what's already in session, is shared
        Enumeration nameEnum = session.getAttributeNames();
        while (nameEnum.hasMoreElements()) {
            String name = (String) nameEnum.nextElement();
            if (!sharedObjects.contains(name)) {
                log.debug("New shared object found: " + name);
                sharedObjects.add(name);
            }
        }
    }

    /**
     * Check for expired conversations and remove them from ConversationMap.
     */
    private void removeExpired() {
        long timeout = session.getMaxInactiveInterval() * 1000;
        Iterator<Entry<String, Conversation>> itr = entrySet().iterator();
        while (itr.hasNext()) {
            Entry<String, Conversation> entry = itr.next();
            if (entry.getValue().isExpired(timeout)) {
                log.info("Expiring conversation id: " + entry.getKey());
                itr.remove();
            }
        }
    }
}
