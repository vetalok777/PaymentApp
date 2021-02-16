<%--
  Created by IntelliJ IDEA.
  User: Metalolom
  Date: 16.02.2021
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Request Error</title>
</head>
<body>
<h1>Request has already been sent</h1>
</body>

<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input class="btn btn-secondary" type="submit" value="Back">
</form>
</html>
