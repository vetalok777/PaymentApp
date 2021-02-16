
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Successfully</title>
</head>
<body>
    <h1>Adding is succesfully. Your new card number is: <%=request.getAttribute("number")%></h1>

    <form action="${pageContext.request.contextPath}/HomePage" method="post">
        <input type="submit" value="Back">
    </form>
</body>
</html>
