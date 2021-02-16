<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Card</title>
</head>
<body>
<form action=AddingCard method="post">
    <h1>Type card name</h1>
    <input type="text" name="name"/>
    <input type="submit" value="Submit"/>
</form>

<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input type="submit" value="Back">
</form>
</body>
</html>
