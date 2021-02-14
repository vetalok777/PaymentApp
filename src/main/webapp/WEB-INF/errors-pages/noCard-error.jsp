<%--
  Created by IntelliJ IDEA.
  User: Metalolom
  Date: 12.02.2021
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>No Card Error</title>
</head>
<body>
<h1>The card you are trying to send funds to does not exist in the system</h1>
</body>

<form action="${pageContext.request.contextPath}/Payment" method="get">
    <input type="submit" value="Back">
</form>
</html>
