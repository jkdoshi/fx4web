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

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;

@SuppressWarnings("serial")
public class ComponentValidationListener implements PhaseListener {
    /**
     * Name of the application context parameter, if set to true, causes
     * client-ids to be replaced with their corresponding labels (if any) in
     * faces messages.
     */
    public static final String PARAM_IDREPLACE = "fx4web.replaceIdWithLabel";

    private static final String MAPPING_PREFIX = "/com/doshiland/fx4web/resource/";

    private static final String ATTR_STYLECLASS = "styleClass";

    private static final String STYLECLASS_INVALID = "fx4web-invalid";

    private static final String STYLECLASS_REQUIRED = "fx4web-required";

    private static final Log log = LogFactory.getLog(ComponentValidationListener.class);
    
    public void afterPhase(PhaseEvent event) {
        // do nothing
    }

    @SuppressWarnings("unchecked")
    public void beforePhase(PhaseEvent event) {
        FacesContext facesContext = event.getFacesContext();
        UIViewRoot root = facesContext.getViewRoot();
        AddResource addResource = AddResourceFactory.getInstance(facesContext);
        addReferences(facesContext, addResource);
        visitComponent(facesContext, root);
        String jsTxt = genJavaScriptCodeForMessages(facesContext);
        if (jsTxt != null) {
            addResource.addInlineScriptAtPosition(facesContext,
                AddResource.HEADER_BEGIN, jsTxt);
        }
    }

    private static String genJavaScriptCodeForMessages(FacesContext facesContext) {
        if (facesContext.getMessages().hasNext()) {
            StringBuffer jsTxt = new StringBuffer(
                "Event.observe(window, 'load', function() {FX4Web.showMessages([");
            appendMessagesForClientId(facesContext, null, jsTxt);
            // then add messages for each clientId that has them
            Iterator<String> itrClientIds = facesContext
                .getClientIdsWithMessages();
            while (itrClientIds.hasNext()) {
                String clientId = itrClientIds.next();
                appendMessagesForClientId(facesContext, clientId, jsTxt);
            }
            // now finally remove the extr "," from the end
            if (jsTxt.length() > 0 && jsTxt.charAt(jsTxt.length() - 1) == ',') {
                jsTxt.deleteCharAt(jsTxt.length() - 1);
            }
            jsTxt.append("], {title: 'Application Messages'});});");
            return jsTxt.toString();
        } else {
            return null;
        }
    }

    private static void addReferences(FacesContext facesContext,
            AddResource addResource) {
        addJavaScriptRef(facesContext, addResource, "prototype.js");
        addJavaScriptRef(facesContext, addResource, "fx4web.js");
        addStyleSheetRef(facesContext, addResource, "fx4web.css");
    }

    private static void addStyleSheetRef(FacesContext facesContext,
            AddResource addResource, String uri) {
        addResource.addStyleSheet(facesContext, AddResource.HEADER_BEGIN,
            MAPPING_PREFIX + uri);
    }

    private static void addJavaScriptRef(FacesContext facesContext,
            AddResource addResource, String uri) {
        addResource.addJavaScriptAtPosition(facesContext,
            AddResource.HEADER_BEGIN, MAPPING_PREFIX + uri);
    }

    private static void appendMessagesForClientId(FacesContext facesContext,
            String clientId, StringBuffer jsTxt) {
        Iterator<FacesMessage> messages = facesContext.getMessages(clientId);
        boolean replacesIds = isReplaceId(facesContext);
        while (messages.hasNext()) {
            FacesMessage message = messages.next();
            if (clientId == null) {
                jsTxt.append("\n{severity:'");
            } else {
                if (replacesIds) {
                    replaceIdWithLabel(clientId, message, facesContext);
                }
                jsTxt.append("\n{clientId:'");
                jsTxt.append(clientId);
                jsTxt.append("', severity:'");
            }
            jsTxt.append(message.getSeverity());
            jsTxt.append("', summary:'");
            jsTxt.append(message.getSummary());
            jsTxt.append("', detail:'");
            jsTxt.append(message.getDetail());
            jsTxt.append("'},");
        }
    }

    private static boolean isReplaceId(FacesContext facesContext) {
        ServletContext webContext = (ServletContext) facesContext.getExternalContext().getContext();
        String val = webContext.getInitParameter(PARAM_IDREPLACE);
        return "true".equalsIgnoreCase(val);
    }

    private static void replaceIdWithLabel(String clientId,
            FacesMessage message, FacesContext facesContext) {
        UIComponent label = findLabelFor(facesContext.getViewRoot(), clientId);
        if (label != null) {
            int idx = clientId.lastIndexOf(':');
            if(idx >= 0) {
                clientId = clientId.substring(idx+1);
            }
            log.debug("Replacing ID with Label for: " + clientId);
            String text = (String) label.getAttributes().get("value");
            String detail = message.getDetail();
            String summary = message.getSummary();
            message.setSummary(summary.replaceAll(clientId, text));
            message.setDetail(detail.replaceAll(clientId, text));
        } else {
            log.debug("Unabled to find a label for ID: " + clientId);
        }
    }

    private static UIComponent findLabelFor(UIComponent root, String clientId) {
        for (UIComponent c : (List<UIComponent>) root.getChildren()) {
            if ("javax.faces.Label".equals(c.getRendererType())) {
                String labelFor = (String) c.getAttributes().get("for");
                if (clientId.equals(labelFor) || clientId.endsWith(":" + labelFor)) {
                    return c;
                }
            }
            if (c.getChildCount() > 0) {
                // look for the right label recursively
                UIComponent label = findLabelFor(c, clientId);
                if (label != null) {
                    return label;
                }
            }
        }
        return null;
    }

    /**
     * Visits each component in the component tree, detects if it has
     * "required=true" setting, and adds the styleclass STYLECLASS_REQUIRED to
     * it. Also, if it has a faces-message associated with it (higher than
     * INFO), then adds STYLECLASS_INVALID as well.
     */
    private static void visitComponent(FacesContext facesContext,
            UIComponent component) {
        if (component instanceof UIInput && ((UIInput) component).isRequired()) {
            addStyleClass(component, STYLECLASS_REQUIRED);
        }
        if (isInvalid(facesContext, component.getClientId(facesContext))) {
            addStyleClass(component, STYLECLASS_INVALID);
        }
        if (component.getChildCount() > 0) {
            for (UIComponent child : (List<UIComponent>) component
                .getChildren()) {
                visitComponent(facesContext, child);
            }
        }
    }

    private static void addStyleClass(UIComponent component,
            String newStyleClass) {
        String styleClass = (String) component.getAttributes().get(
            ATTR_STYLECLASS);
        if (StringUtils.isEmpty(styleClass)) {
            styleClass = newStyleClass;
        } else {
            styleClass += " " + newStyleClass;
        }
        component.getAttributes().put(ATTR_STYLECLASS, styleClass);
    }

    private static boolean isInvalid(FacesContext facesContext, String clientId) {
        Iterator<FacesMessage> messages = facesContext.getMessages(clientId);
        while (messages.hasNext()) {
            FacesMessage message = messages.next();
            if (message.getSeverity().getOrdinal() > FacesMessage.SEVERITY_INFO
                .getOrdinal()) {
                return true;
            }
        }
        return false;
    }

    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
