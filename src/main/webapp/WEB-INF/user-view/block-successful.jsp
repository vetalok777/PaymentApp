<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Blocking Card</title>
</head>
<body>
<h1>Your card is blocked successfully</h1>

<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input class="btn btn-secondary" type="submit" value="Back">
</form>
</body>
</html>
