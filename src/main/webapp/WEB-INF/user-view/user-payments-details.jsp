<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
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

    <title>Your Payments</title>
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


<form action="${pageContext.request.contextPath}/HomePage" method="post">
    <input type="submit" value="Home">
</form>

<p>Your payments:</p>
<c:forEach items="${payments}" var="payment">

    <table>
        <tr>
            <th>Payment Id</th>
            <th>Payment Amount</th>
            <th>Payment Status</th>
            <th>Creation Date</th>
            <th>Sender Card</th>
            <th>Receiver Card</th>
        </tr>
        <tr>
            <td>${payment.id}</td>
            <td>${payment.amount}</td>
            <td>${payment.status}</td>
            <td>${payment.date}</td>
            <td>${payment.sender_card}</td>
            <td>${payment.receiver_card}</td>
            <c:if test="${payment.status == 'in process' }">
                <td>
                    <form action="PaymentAction" method="post">
                        <input type="submit" value="Submit payment">
                        <input type="hidden" name="id" value="${payment.id}"/>
                        <input type="hidden" name="receiverCard" value="${payment.receiver_card}"/>
                        <input type="hidden" name="amount" value="${payment.amount}"/>
                        <input type="hidden" name="status" value="submit"/>
                    </form>
                </td>
            </c:if>
        </tr>
    </table>
</c:forEach>
<br>
</body>
</html>
