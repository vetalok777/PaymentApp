<%--
  Created by IntelliJ IDEA.
  User: Metalolom
  Date: 12.02.2021
  Time: 17:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error with payment</title>
</head>
<body>
    <h1>Not enough funds on the card.</h1> <br/>

    <form action="${pageContext.request.contextPath}/Payment" method="get">
        <input type="submit" value="Back">
    </form>
</body>


</html>
