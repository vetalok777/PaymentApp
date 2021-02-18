<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">

    <title>Home Page</title>
    <style>
        .styled-table {
            border-collapse: collapse;
            margin: 25px 0;
            font-size: 0.9em;
            font-family: sans-serif;
            min-width: 400px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
        }

        .styled-table thead tr {
            background-color: #009879;
            color: #ffffff;
            text-align: left;
        }

        .styled-table th,
        .styled-table td {
            padding: 12px 15px;
        }

        .styled-table tbody tr {
            border-bottom: 1px solid #dddddd;
        }

        .styled-table tbody tr:nth-of-type(even) {
            background-color: #f3f3f3;
        }

        .styled-table tbody tr:last-of-type {
            border-bottom: 2px solid #009879;
        }

        .styled-table tbody tr.active-row {
            font-weight: bold;
            color: #009879;
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

<form action=HomePage method="post">
    <p>Sort By </p>
    <select name="sorting">
        <option value="byBalance">Balance</option>
        <option value="byNumber">Number</option>
        <option value="byName">Name</option>
    </select>
    <input class="btn btn-primary btn-s" type="submit" value="OK">
</form>
<main class="m-3">
    <div class="row col-md-6">

        <table class="styled-table">
            <tr class="active-row">
                <th>Card Name</th>
                <th>Card Number</th>
                <th>Card Balance</th>
                <th>Card Status</th>
            </tr>
            <c:forEach items="${cards}" var="card">
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
            </c:forEach>
        </table>
    </div>
</main>
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
