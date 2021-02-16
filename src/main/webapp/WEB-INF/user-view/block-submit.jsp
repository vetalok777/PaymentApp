<%--
  Created by IntelliJ IDEA.
  User: Metalolom
  Date: 16.02.2021
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Submit</title>
</head>
<body>
<h1>Are you sure you want to block your card?</h1> <br/>
<h1>Card can be unblocked only by admin.</h1>

<form action="CardBlock" method="post">
    <input type="submit" value="Yes">
    <input type="hidden" name="action" value="block">
</form>

<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input type="submit" value="No">
</form>
</body>
</html>
