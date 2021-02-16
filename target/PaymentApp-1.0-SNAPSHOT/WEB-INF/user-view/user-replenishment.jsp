
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Card Replenishment</title>
</head>
<body>
<form action="<%= request.getContextPath() %>/Replenishment" method="post">
    <h1>Type sum you want to deposit</h1>
    <input type="text" name="sum"/>
    <input type="submit" value="Submit"/>
</form>

<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input type="submit" value="Back">
</form>
</body>
</html>
