<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<h:form id="shared">
	<h:panelGrid columns="2">
		<h:outputLabel for="username" value="User Name" />
		<h:inputText id="username" value="#{SharedBean.username}" />
		<h:outputLabel for="department" value="Department" />
		<h:inputText id="department" value="#{SharedBean.department}" />
		<h:commandButton value="Save"/>
		<h:commandButton value="Don't Save" immediate="true"/>
		<h:panelGroup/>
	</h:panelGrid>
</h:form>
