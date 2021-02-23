<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">

    <title>Your Payments</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("authorization.jsp");
    }

%>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <form action="HomePage" method="post">
                <input class="btn btn-secondary" type="submit" value="Home">
            </form>
        </div>

        <div class="navbar-nav ml-auto">
            <form action="LogOut" method="post">
                <input class="btn btn-primary" type="submit" value="LogOut">
            </form>
        </div>
    </div>
</nav>

<p>Your payments:</p>

<div class="btn-group" role="group" aria-label="Basic example">

    <form action=PaymentDetails>
        <p>Sort By Date</p>
        <input type="hidden" name="currentPage" value=${currentPage}>
        <select name="sorting">
            <option value="byDateNew">Newest to Oldest</option>
            <option value="byDateOld">Oldest to Newest</option>
        </select>
        <input class="btn btn-primary btn-s" type="submit" value="OK">
    </form>
</div>

<div class="btn-group" role="group" aria-label="Basic example">

    <form action=PaymentDetails>
        <p>Sort By Number</p>
        <input type="hidden" name="currentPage" value=${currentPage}>
        <input type="hidden" name="sorting" value=byNumber>
        <input class="btn btn-primary btn-s" type="submit" value="OK">
    </form>
</div>


<main class="m-3">
    <div class="row col-md-6">
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th>Payment Id</th>
                <th>Payment Amount</th>
                <th>Payment Status</th>
                <th>Creation Date</th>
                <th>Sender Card</th>
                <th>Receiver Card</th>
            </tr>

            <c:forEach items="${payments}" var="payment">
                <tr>
                    <td>${payment.id}</td>
                    <td>${payment.amount}UAH</td>
                    <td>${payment.status}</td>
                    <td>${payment.date}</td>
                    <td>${payment.sender_card}</td>
                    <td>${payment.receiver_card}</td>
                </tr>

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

                    <td>
                        <form action="PaymentAction" method="post">
                            <input type="submit" value="Cancel payment">
                            <input type="hidden" name="id" value="${payment.id}"/>
                            <input type="hidden" name="senderCard" value="${payment.sender_card}"/>
                            <input type="hidden" name="amount" value="${payment.amount}"/>
                            <input type="hidden" name="status" value="cancel"/>
                        </form>
                    </td>
                </c:if>
            </c:forEach>
        </table>
    </div>
    <nav aria-label="Navigation for payments">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link"
                                         href="PaymentDetails?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&sorting=${sorting}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">
                                ${i} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link"
                                                 href="PaymentDetails?recordsPerPage=${recordsPerPage}&currentPage=${i}&sorting=${sorting}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt noOfPages}">
                <li class="page-item"><a class="page-link"
                                         href="PaymentDetails?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&sorting=${sorting}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>


</main>
</body>
</html>




