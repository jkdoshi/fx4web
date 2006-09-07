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

package com.doshiland.fx4web.jsf;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class DebugListener implements javax.faces.event.PhaseListener {
    private final static Log log = LogFactory.getLog(DebugListener.class);

    public void afterPhase(PhaseEvent phaseEvent) {
        PhaseId id = phaseEvent.getPhaseId();
        HttpServletRequest request = (HttpServletRequest) phaseEvent
            .getFacesContext().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        log.debug(uri + " : " + id + " : END");
    }

    public void beforePhase(PhaseEvent phaseEvent) {
        PhaseId id = phaseEvent.getPhaseId();
        HttpServletRequest request = (HttpServletRequest) phaseEvent
            .getFacesContext().getExternalContext().getRequest();
        String uri = request.getRequestURI();
        log.debug(uri + " : " + id + " : BEGIN");
    }

    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
