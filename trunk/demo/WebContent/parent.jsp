<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles-1.1"
	prefix="tiles"%>
<f:verbatim>
	<script type="text/javascript">
	function myOpen(winName) {
		window.open('child.jsp', winName, 'height=200,width=400,status=1,scrollbars=1,toolbar=0,menubar=0,location=0');
	}
	</script>
</f:verbatim>
<f:subview id="cidform">
	<tiles:insert definition="cidform" flush="false" />
</f:subview>
<f:subview id="sharedform">
	<tiles:insert definition="sharedform" flush="false" />
</f:subview>
<f:verbatim>
	<hr />
	<table>
		<caption>Select an employee to assign work item to</caption>
		<tr>
			<td><a href="#" onclick="myOpen('emp1')">emp1</a></td>
		</tr>
		<tr>
			<td><a href="#" onclick="myOpen('emp2')">emp2</a></td>
		</tr>
		<tr>
			<td><a href="#" onclick="myOpen('emp3')">emp3</a></td>
		</tr>
		<tr>
			<td><a href="#" onclick="myOpen('emp4')">emp4</a></td>
		</tr>
		<tr>
			<td><a href="#" onclick="myOpen('emp5')">emp5</a></td>
		</tr>
	</table>
</f:verbatim>
