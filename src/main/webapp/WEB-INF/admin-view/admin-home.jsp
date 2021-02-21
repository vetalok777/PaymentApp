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

    <title>ADMIN Home</title>


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
    <form action="Users" method="post">
        <input class="btn btn-secondary" type="submit" value="Users List">
    </form>
</div>

<h1>Welcome, ADMIN</h1>
<br/>

<h1>Requests for unblocking</h1>
<div class="row col-md-6">

    <table class="styled-table">
        <tr class="active-row">
            <th>Request ID</th>
            <th>Request Status</th>
            <th>Request Creation Date</th>
            <th>Card id</th>
        </tr>
        <c:forEach items="${requests}" var="request">
            <tr>
                <td>${request.id}</td>
                <td>${request.status}</td>
                <td>${request.creationDate}</td>
                <td>${request.cardId}</td>
                <td>
                    <c:if test="${request.status=='not considered'}">
                        <form action="RequestAction" method="post">
                            <input type="submit" value="Unblock card">
                            <input type="hidden" name="action" value="considered"/>
                            <input type="hidden" name="cardId" value="${request.cardId}"/>
                            <input type="hidden" name="Id" value="${request.id}"/>
                        </form>

                        <form action="RequestAction" method="post">
                            <input type="submit" value="Cancel request">
                            <input type="hidden" name="action" value="cancel"/>
                            <input type="hidden" name="cardId" value="${request.cardId}"/>
                            <input type="hidden" name="Id" value="${request.id}"/>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
