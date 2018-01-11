<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%@ page import="java.util.Map" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
Map<String, Object> context = (Map<String, Object>)request.getAttribute("context");

String abtesting = (String)context.get("abtesting");
%>
<p>This is a <b>custom</b> AB testing rule...
<aui:fieldset>
	<aui:select name="abtesting" value="<%= abtesting %>">
		<aui:option label="The session ID is even" value="even" />
		<aui:option label="The session ID is odd" value="odd" />
	</aui:select>
</aui:fieldset>