<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles-1.1"
	prefix="tiles"%>
<f:subview id="cidform">
	<tiles:insert definition="cidform" flush="false" />
</f:subview>
<f:subview id="sharedform">
	<tiles:insert definition="sharedform" flush="false" />
</f:subview>
<hr />
<h:form id="unshared">
	<h:panelGrid columns="2">
		<h:outputLabel for="text" value="Enter new work item text" />
		<h:inputText id="text" value="#{UnsharedBean.text}" />
		<h:commandButton value="Add" action="#{UnsharedBean.addRow}" />
		<h:panelGroup />
	</h:panelGrid>
</h:form>
<hr />
<h:dataTable value="#{UnsharedBean.list}" var="row">
	<h:column>
		<h:outputText value="#{row}" />
	</h:column>
</h:dataTable>
