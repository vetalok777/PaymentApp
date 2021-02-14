<%--
  Created by IntelliJ IDEA.
  User: Metalolom
  Date: 12.02.2021
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>You cannot send money to the sender's card</h1>

<form action="${pageContext.request.contextPath}/Payment" method="get">
    <input type="submit" value="Back">
</form>
</body>
</html>
