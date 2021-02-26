<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<html lang="${language}">
<head>
    <title>New Card</title>
</head>
<body>
<form action=AddingCard method="post">
    <h1><fmt:message
            key="msg.typeCardName"> </fmt:message></h1>
    <input type="text" name="name"/>
    <input type="submit" value="<fmt:message
            key="msg.submit"> </fmt:message>"/>
</form>

<form action="${pageContext.request.contextPath}/HomePage">
    <input type="submit" value="<fmt:message
            key="msg.back"> </fmt:message>">
</form>
</body>
</html>
