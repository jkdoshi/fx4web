<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css" href="default.css" />
<title>Child Window</title>
</head>
<body>
<f:view>
	<h1>Conversation Demo</h1>
	<div>
	<form>
	<label for="_cid">Coversation ID:</label>
	<input name='_cid' type='text' />
	</form>
	</div>
	<hr />
	<f:subview id="sharedForm">
		<%@ include file="sharedform.jsp"%>
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
</f:view>
</body>
</html>
