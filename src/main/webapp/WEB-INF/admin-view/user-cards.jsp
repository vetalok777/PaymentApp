<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">

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
            background-color: #181c1b;
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
            border-bottom: 2px solid #1f2121;
        }

        .styled-table tbody tr.active-row {
            font-weight: bold;
            color: #050505;
        }

    </style>

    <title>User Cards</title>
</head>
<body>
<div class="btn-group" role="group" aria-label="Basic example">
    <form action="LogOut" method="post">
        <input class="btn btn-primary btn-sm" type="submit" value="LogOut">
    </form>
</div>

<div class="btn-group" role="group" aria-label="Basic example">
    <form action="Users" method="post">
        <input class="btn btn-secondary" type="submit" value="Users List">
    </form>
</div>

<div class="btn-group" role="group" aria-label="Basic example">
    <form action="AdminHome" method="post">
        <input class="btn btn-secondary" type="submit" value="HomePage">
    </form>
</div>

<p><%=request.getAttribute("login")%> Cards</p>


<div class="row col-md-6">

    <table class="styled-table">
        <tr class="active-row">
            <th>Card ID</th>
            <th>Card Status</th>
            <th>Card Name</th>
            <th>Card Number</th>
            <th>Card Balance</th>
        </tr>
        <c:forEach items="${cards}" var="card">
            <tr>
                <td>${card.id}</td>
                <td>${card.status}</td>
                <td>${card.name}</td>
                <td>${card.number}</td>
                <td>${card.balance}</td>
                <td>
                    <c:if test="${card.status>=1}">
                        <form action="CardAction" method="post">
                            <input type="submit" value="Block card">
                            <input type="hidden" name="action" value="block"/>
                            <input type="hidden" name="login" value="${login}"/>
                            <input type="hidden" name="cardId" value="${card.id}"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
