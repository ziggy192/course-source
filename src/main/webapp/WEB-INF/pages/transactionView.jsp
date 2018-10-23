<%@page contentType="text/html; ISO-8859-1" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<html>
<head>
    <title>eBanking</title>
</head>
<body>
<font color="red">
    Wellcome,<x:out select="$doc//*[@username=$username]/fullname"/>

</font>
<h1>Hello bank</h1>
</body>
</html>