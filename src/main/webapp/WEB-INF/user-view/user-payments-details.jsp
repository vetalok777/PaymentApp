<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<html lang="${language}">
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
            <form action="HomePage">
                <input class="btn btn-secondary" type="submit" value="<fmt:message
                key="msg.home"> </fmt:message>">
            </form>
        </div>

        <div class="navbar-nav ml-auto">
            <form action="LogOut" method="post">
                <input class="btn btn-primary" type="submit" value="<fmt:message
                key="msg.logout"> </fmt:message>">
            </form>
        </div>
    </div>
</nav>


<div class="btn-group" role="group" aria-label="Basic example">

    <form>
        <select name="language" class="form-control" id="ExampleFormControlSelect1" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>UK</option>
            <input type="hidden" name="currentPage" value=${currentPage}>
            <input type="hidden" name="sorting" value=byStatus>
        </select>

    </form>

    <p><fmt:message
            key="msg.myPayments"> </fmt:message></p>

    <form action=PaymentDetails>
        <p><fmt:message
                key="msg.sortByDate"> </fmt:message></p>
        <input type="hidden" name="currentPage" value=${currentPage}>
        <select name="sorting">
            <option value="byDateNew"><fmt:message
                    key="msg.newOld"> </fmt:message></option>
            <option value="byDateOld"><fmt:message
                    key="msg.oldNew"> </fmt:message></option>
        </select>
        <input class="btn btn-primary btn-s" type="submit" value="OK">
    </form>
</div>

<div class="btn-group" role="group" aria-label="Basic example">

    <form action=PaymentDetails>
        <p><fmt:message
                key="msg.sortByNumber"> </fmt:message></p>
        <input type="hidden" name="currentPage" value=${currentPage}>
        <input type="hidden" name="sorting" value=byNumber>
        <input class="btn btn-primary btn-s" type="submit" value="OK">
    </form>
</div>


<main class="m-3">
    <div class="row col-md-6">
        <table class="table table-striped table-bordered table-sm">
            <tr>
                <th><fmt:message
                        key="msg.paymentId"> </fmt:message></th>
                <th><fmt:message
                        key="msg.paymentAmount"> </fmt:message></th>
                <th><fmt:message
                        key="msg.paymentStatus"> </fmt:message></th>
                <th><fmt:message
                        key="msg.creationDate"> </fmt:message></th>
                <th><fmt:message
                        key="msg.senderCard"> </fmt:message></th>
                <th><fmt:message
                        key="msg.receiverCard"> </fmt:message></th>
            </tr>

            <c:forEach items="${payments}" var="payment">
                <tr>
                    <td>${payment.id}</td>
                    <td>${payment.amount}UAH</td>
                    <td><c:if test="${payment.status == 'in process' }">
                        <fmt:message
                                key="msg.inProcess"> </fmt:message>
                    </c:if>
                    <c:if test="${payment.status == 'canceled' }">
                        <fmt:message
                                key="msg.canceled"> </fmt:message>
                    </c:if>

                    <c:if test="${payment.status == 'successful' }">
                        <fmt:message
                                key="msg.success"> </fmt:message>
                    </c:if> </td>
                    <td>${payment.date}</td>
                    <td>${payment.sender_card}</td>
                    <td>${payment.receiver_card}</td>
                </tr>

                <c:if test="${payment.status == 'in process' }">
                    <td>
                        <form action="PaymentAction" method="post">
                            <input type="submit" value="<fmt:message
                        key="msg.submitPay"> </fmt:message>">
                            <input type="hidden" name="id" value="${payment.id}"/>
                            <input type="hidden" name="receiverCard" value="${payment.receiver_card}"/>
                            <input type="hidden" name="amount" value="${payment.amount}"/>
                            <input type="hidden" name="status" value="submit"/>
                        </form>
                    </td>

                    <td>
                        <form action="PaymentAction" method="post">
                            <input type="submit" value="<fmt:message
                        key="msg.cancelPay"> </fmt:message>">
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
                                         href="PaymentDetails?recordsPerPage=${recordsPerPage}&currentPage=${currentPage-1}&sorting=${sorting}"><fmt:message
                        key="msg.previous"> </fmt:message></a>
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
                                         href="PaymentDetails?recordsPerPage=${recordsPerPage}&currentPage=${currentPage+1}&sorting=${sorting}"><fmt:message
                        key="msg.next"> </fmt:message></a>
                </li>
            </c:if>
        </ul>
    </nav>


</main>
</body>
</html>




