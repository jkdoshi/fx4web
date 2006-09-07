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
<title>Main Window</title>
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
	<table>
		<caption>Select an employee to assign work item to</caption>
		<tr>
			<td><a href="#"
				onclick="window.open('subwin.jsp', 'emp1', 'height=200,width=400,status=1,scrollbars=1,toolbar=0,menubar=0,location=0')">emp1</a></td>
		</tr>
		<tr>
			<td><a href="#"
				onclick="window.open('subwin.jsp', 'emp2', 'height=200,width=400,status=1,scrollbars=1,toolbar=0,menubar=0,location=0')">emp2</a></td>
		</tr>
		<tr>
			<td><a href="#"
				onclick="window.open('subwin.jsp', 'emp3', 'height=200,width=400,status=1,scrollbars=1,toolbar=0,menubar=0,location=0')">emp3</a></td>
		</tr>
		<tr>
			<td><a href="#"
				onclick="window.open('subwin.jsp', 'emp4', 'height=200,width=400,status=1,scrollbars=1,toolbar=0,menubar=0,location=0')">emp4</a></td>
		</tr>
		<tr>
			<td><a href="#"
				onclick="window.open('subwin.jsp', 'emp5', 'height=200,width=400,status=1,scrollbars=1,toolbar=0,menubar=0,location=0')">emp5</a></td>
		</tr>
	</table>
</f:view>
</body>
</html>
