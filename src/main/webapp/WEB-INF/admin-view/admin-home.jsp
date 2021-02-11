<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WELCOME ADMIN</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("authorization.jsp");
    }

%>

<h1>Welcome, ADMIN</h1>

<form action="LogOut" method="post">
    <input type="submit" value="LogOut">
</form>

</body>
</html>
