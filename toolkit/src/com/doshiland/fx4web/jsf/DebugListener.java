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
