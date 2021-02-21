<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            border-bottom: 1px solid #2f4f85;
        }

        .styled-table tbody tr:nth-of-type(even) {
            background-color: #f3f3f3;
        }

        .styled-table tbody tr:last-of-type {
            border-bottom: 2px solid #276d6d;
        }

        .styled-table tbody tr.active-row {
            font-weight: bold;
            color: #275d45;
        }

    </style>
    <title>Users</title>
</head>
<body>
<div class="btn-group" role="group" aria-label="Basic example">
    <form action="LogOut" method="post">
        <input class="btn btn-primary btn-sm" type="submit" value="LogOut">
    </form>

    <div class="btn-group" role="group" aria-label="Basic example">
        <form action="AdminHome" method="post">
            <input class="btn btn-secondary" type="submit" value="HomePage">
        </form>
    </div>
</div>

<h1>Users List</h1>


<div class="row col-md-6">

    <table class="styled-table">
        <tr class="active-row">
            <th>User ID</th>
            <th>User Login</th>
            <th>User Name</th>
            <th>User Status</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.firstName}</td>
                <td><c:out value="${user.status>=1 ? 'active': 'blocked'}"/></td>
                <td>
                    <c:if test="${user.status>=1}">
                        <form action="UserAction" method="post">
                            <input type="submit" value="Block User">
                            <input type="hidden" name="action" value="block"/>
                            <input type="hidden" name="userId" value="${user.id}"/>
                        </form>
                    </c:if>
                </td>

                <td>
                    <c:if test="${user.status<1}">
                        <form action="UserAction" method="post">
                            <input type="submit" value="Unblock User">
                            <input type="hidden" name="action" value="unblock"/>
                            <input type="hidden" name="userId" value="${user.id}"/>
                        </form>
                    </c:if>
                </td>

                <td>
                        <form action="UserCards" method="post">
                            <input type="submit" value="Cards">
                            <input type="hidden" name="login" value="${user.login}"/>
                        </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
