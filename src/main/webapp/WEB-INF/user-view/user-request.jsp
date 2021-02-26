<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<html lang="${language}">
<head>
    <title>Request</title>
</head>
<body>
<h1>
    <fmt:message
            key="msg.requestFor"> </fmt:message>
</h1>
<form action="${pageContext.request.contextPath}/HomePage">
    <input class="btn btn-secondary" type="submit" value="<fmt:message
            key="msg.back"> </fmt:message>">
</form>
</body>
</html>
