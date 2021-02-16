<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">

    <title>User Welcome</title>
    <style>
        table {
            width: 50%;
        }

        table, th, td {
            border: 1px solid #000000;
            border-collapse: collapse;
        }

        th, td {
            padding: 15px;
            text-align: left;
        }

        inline {
            display: inline-block;
        }

    </style>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("authorization.jsp");
    }

%>

<div class="btn-group" role="group" aria-label="Basic example">
    <form action="LogOut" method="post">
        <input class="btn btn-primary btn-sm" type="submit" value="LogOut">
    </form>
</div>

<div class="btn-group" role="group" aria-label="Basic example">
    <form action="PaymentDetails">
        <input type="hidden" name="currentPage" value="1">
        <input type="hidden" name="sorting" value="byStatus">
        <input class="btn btn-secondary" type="submit" value="My payments">
    </form>
</div>

<h1>Welcome,<%=request.getAttribute("login")%>
</h1>

<p>Your cards:</p>
<c:forEach items="${cards}" var="card">

    <table>
        <tr>
            <th>Card Name</th>
            <th>Card Number</th>
            <th>Card Balance</th>
            <th>Card Status</th>
        </tr>
        <tr>
            <td>${card.name}</td>
            <td>${card.number}</td>
            <td>${card.balance}UAH</td>
            <td><c:out value="${card.status>=1 ? 'active': 'blocked'}"/></td>
            <td>
                <form action="Replenishment">
                    <input type="submit" value="Deposit">
                    <input type="hidden" name="id" value="${card.id}"/>
                    <input type="hidden" name="status" value="${card.status}"/>
                </form>

                <c:if test="${card.status>=1}">

                    <form action="CardBlock" method="post">
                        <input type="submit" value="Block card">
                        <input type="hidden" name="action" value="submit"/>
                        <input type="hidden" name="id" value="${card.id}"/>
                    </form>

                </c:if>

                <c:if test="${card.status<1}">

                    <form action="CardBlock" method="post">
                        <input type="submit" value="Unblock card">
                        <input type="hidden" name="id" value="${card.id}"/>
                        <input type="hidden" name="action" value="unblock"/>

                    </form>

                </c:if>
            </td>
        </tr>
    </table>
</c:forEach>
<br>

<%
    session.setAttribute("cards", request.getAttribute("cards"));

%>

<div class="btn-group" role="group" aria-label="Basic example">
    <form action="${pageContext.request.contextPath}/Payment" method="get">
        <input type="submit" value="Create new payment">
    </form>
</div>

<div class="btn-group" role="group" aria-label="Basic example">
    <form action="AddingCard" method="get">
        <input type="submit" value="Add new card">
    </form>
</div>
</body>
</html>
