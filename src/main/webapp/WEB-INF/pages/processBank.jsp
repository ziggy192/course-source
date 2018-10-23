<%@page contentType="text/html; ISO-8859-1" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Process</title>
</head>
<body>
<h1>Processing</h1>
<c:set var="usernanme"/>

<c:if test="${not empty username and not empty password}">
    <c:import var="xmlDoc" url="WEB-INF/accountATM.xml"/>
    <x:parse var="doc" doc="${xmlDoc}"/>
    <x:if
            select="$doc//*[local-name()='allowed' and @username=$username and pin=$password">
        <jsp:forward page="transactionView.jsp"/>
    </x:if>
</c:if>
<h2>
    <font color="red">
        Invalid username or password

    </font>
</h2>
</body>
</html>