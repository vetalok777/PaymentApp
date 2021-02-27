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
    <style>

        body {
            background: -webkit-linear-gradient(left, #0072ff, #00c6ff);
        }

        .contact-form {
            background: #fff;
            margin-top: 10%;
            margin-bottom: 5%;
            width: 70%;
        }

        .contact-form .form-control {
            border-radius: 1rem;
        }

        .contact-image {
            text-align: center;
        }

        .contact-image img {
            border-radius: 6rem;
            width: 11%;
            margin-top: -3%;
        }

        .contact-form form {
            padding: 14%;
        }

        .contact-form form .row {
            margin-bottom: -7%;
        }

        .contact-form h3 {
            margin-bottom: 8%;
            margin-top: -10%;
            text-align: center;
            color: #0062cc;
        }

        .contact-form .btnContact {
            width: 50%;
            border: none;
            border-radius: 1rem;
            padding: 1.5%;
            background: #dc3545;
            font-weight: 600;
            color: #fff;
            cursor: pointer;
        }

        .btnContactSubmit {
            width: 50%;
            border-radius: 1rem;
            padding: 1.5%;
            color: #fff;
            background-color: #0062cc;
            border: none;
            cursor: pointer;
        }
    </style>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>New Payment</title>
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if (session.getAttribute("username") == null) {
        response.sendRedirect("authorization.jsp");
    }

%>

<nav class="navbar navbar-expand-md navbar-dark bg-dark">

    <div class="navbar-nav">
        <form action="PaymentDetails">
            <input type="hidden" name="currentPage" value="1">
            <input type="hidden" name="sorting" value="byStatus">
            <input class="btn btn-secondary" type="submit" value="<fmt:message
                key="msg.myPayments"> </fmt:message>">
        </form>

        <form action="${pageContext.request.contextPath}/HomePage">
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
</nav>

<div class="btn-group" role="group" aria-label="Basic example">
    <form>
        <select name="language" class="form-control" id="ExampleFormControlSelect1" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>UK</option>
        </select>

    </form>
</div>

<div class="container contact-form">
    <div class="contact-image">
        <img src="https://t3.ftcdn.net/jpg/01/00/85/72/360_F_100857287_vJD8X5JMSFxwvmls0W2A0ZY7xfVOYH9J.jpg" alt=""/>
    </div>
    <form action="${pageContext.request.contextPath}/Payment" method="post">
        <h3><fmt:message
                key="msg.newPayment"> </fmt:message></h3>

        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <p><fmt:message
                            key="msg.senderCard"> </fmt:message>
                    </p> <br/>
                    <select class="browser-default custom-select" name="number">
                        <c:forEach items="${cards}" var="card">
                            <c:if test="${card.status>=1}">
                                <option value="${card.number}">${card.number} (${card.balance}UAH)</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <br/>
                </div>
                <div class="form-group">
                    <input type="text" name="amount" class="form-control" placeholder="<fmt:message
            key="msg.typeAmount"> </fmt:message>" value=""/>
                </div>

                <div class="form-group">
                    <input type="text" name="receiver" class="form-control" placeholder="<fmt:message
                        key="msg.receiverCard"> </fmt:message>" value=""/>
                </div>
                <div class="form-group">
                    <input type="submit" class="btnContact" value="<fmt:message
            key="msg.submit"> </fmt:message>"/>
                </div>
            </div>
        </div>

    </form>
</div>

</body>
</html>
