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
<title>Selections</title>
</head>
<body onload="">
<f:view>
	<h1>Slosh Bucket Demo</h1>
	<h:form id="frm1">
		<input type='button' value='Show Popup'
			onclick="FX4Web.showPopup('frm1:mypopup')" />
		<h:panelGrid id='mypopup' styleClass="fx4web-popup">
			<h:selectManyListbox styleClass="fx4web-sloshbucket" size="5"
				value="#{SharedBean.approvedEmployees}">
				<f:selectItems value="#{SharedBean.employees}" />
			</h:selectManyListbox>
			<f:verbatim>
				<input type='button' value='OK'
					onclick="FX4Web.hidePopup('frm1:mypopup')" />
			</f:verbatim>
		</h:panelGrid>
		<h:commandButton value="Submit" />
	</h:form>
</f:view>
</body>
</html>
