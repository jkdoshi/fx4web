<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<f:view>
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<t:stylesheet path="/default.css" />
	<script src="dom-drag.js"></script>
	<title>Employee</title>
	</head>
	<body>

	<h:messages styleClass="floating">
		<f:attribute name="header" value="Validation Errors" />
	</h:messages>

	<h:form>
		<h:panelGrid columns="2">
			<h:outputLabel for="txt1" value="One" />
			<h:inputText id="txt1" required="true" />
			<h:outputLabel for="txt2" value="Two" />
			<h:inputText id="txt2" required="true" />
			<h:outputLabel for="txt3" value="Three" />
			<h:inputText id="txt3" required="true" />
			<h:outputLabel for="txt4" value="Four" />
			<h:inputText id="txt4" required="true" />
		</h:panelGrid>
		<h:commandButton />
	</h:form>
	</body>
	</html>
</f:view>
