<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles-1.1"
	prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/default.css"/>" />
<script type="text/javascript" src="<c:url value="/conversation.js"/>"></script>
<title><tiles:getAsString name="title" /></title>
</head>
<body>
<f:view>
	<h:panelGrid columns="2">
		<f:facet name="header">
			<f:subview id="header">
				<tiles:insert attribute="header" flush="false" />
			</f:subview>
		</f:facet>

		<f:subview id="menu">
			<tiles:insert attribute="menu" flush="false" />
		</f:subview>

		<f:subview id="body">
			<tiles:insert attribute="body" flush="false" />
		</f:subview>

		<f:facet name="footer">
			<f:subview id="footer">
				<tiles:insert attribute="footer" flush="false" />
			</f:subview>
		</f:facet>

	</h:panelGrid>
</f:view>
</body>
</html>
