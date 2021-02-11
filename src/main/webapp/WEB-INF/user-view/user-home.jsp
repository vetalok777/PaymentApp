<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
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
<div class="inline">
    <form action="LogOut" method="post">
        <input type="submit" value="LogOut">
    </form>


    <form action="Info">
        <input type="submit" value="Info">
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
                    <input type="submit" value="Top-up">
                    <input type="hidden" name="id" value="${card.id}"/>
                    <input type="hidden" name="status" value="${card.status}"/>
                </form>
            </td>
        </tr>
    </table>

</c:forEach>

</body>
</html>
