<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Request</title>
</head>
<body>
<h1>
    Request for unblocking has been sent for consideration
</h1>
<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input class="btn btn-secondary" type="submit" value="Back">
</form>
</body>
</html>
