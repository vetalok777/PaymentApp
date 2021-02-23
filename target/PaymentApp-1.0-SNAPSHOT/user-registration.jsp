<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .login-form {
            width: 340px;
            margin: 50px auto;
        }

        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }

        .login-form h2 {
            margin: 0 0 15px;
        }

        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }

        .btn {
            font-size: 15px;
            font-weight: bold;
        }
    </style>


    <title>LogIn</title>
</head>
<body>
<div class="btn-group" role="group" aria-label="Basic example">
    <form>
        <select name="language" class="form-control" id="ExampleFormControlSelect1" onchange="submit()">
            <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
            <option value="uk_UA" ${language == 'uk_UA' ? 'selected' : ''}>UK</option>
        </select>

    </form>
</div>

<div class="login-form">

    <form action="<%= request.getContextPath() %>/SignUp" method="post">
        <h2 class="text-center"><fmt:message
                key="msg.registration"> </fmt:message></h2>

        <div class="form-group">
            <input type="text" class="form-control" name="firstname" placeholder="<fmt:message
                    key="msg.firstName"> </fmt:message>"/>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="email" placeholder="<fmt:message
                    key="msg.email"> </fmt:message>"/>
        </div>

        <div class="form-group">
            <input type="password" class="form-control" name="password" placeholder="<fmt:message
                    key="msg.password"> </fmt:message>"/>
        </div>

        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block"><fmt:message
                    key="msg.register"> </fmt:message></button>
        </div>

        <p class="text-center"><a href="/PaymentApp"><fmt:message
                key="msg.homePage"> </fmt:message></a></p>
    </form>
</div>
</body>
</html>