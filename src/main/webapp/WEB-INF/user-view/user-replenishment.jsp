<%--
  Created by IntelliJ IDEA.
  User: Metalolom
  Date: 11.02.2021
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Card Replenishment</title>
</head>
<body>
<form action="<%= request.getContextPath() %>/Replenishment" method="post">
    <h1>Type sum you want to top-up</h1>
    <input type="text" name="sum"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
