<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>New Payment</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("authorization.jsp");
    }

%>

<form action="LogOut" method="post">
    <input type="submit" value="LogOut">
</form>

<form action="PaymentDetails">
    <input type="hidden" name="currentPage" value="1">
    <input type="hidden" name="sorting" value="byStatus">
    <input type="submit" value="My payments">
</form>
<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input type="submit" value="Home">
</form>

<h1>New payment</h1> <br>


<form action="${pageContext.request.contextPath}/Payment" method="post">
    <p>Sender card
    </p> <br/>
    <select name="number">
        <c:forEach items="${cards}" var="card">
            <c:if test="${card.status>=1}">
                <option value="${card.number}">${card.number} (${card.balance}UAH)</option>
            </c:if>
        </c:forEach>
    </select>
    <br/>
    <p>Type amount</p>
    <input type="text" name="amount"/>

    <p>Receiver card</p>
    <input type="text" name="receiver"/> <br>
    <input type="submit" value="Submit"/>
</form>


</body>
</html>
