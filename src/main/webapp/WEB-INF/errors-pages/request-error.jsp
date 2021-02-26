<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/mytags.tld" prefix="m" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<html lang="${language}">

<head>
    <title>Request Error</title>
</head>
<body>
<h1><fmt:message
        key="msg.requestAlready"> </fmt:message></h1>
</body>

<form action="${pageContext.request.contextPath}/HomePage">
    <input class="btn btn-secondary" type="submit" value="<fmt:message
        key="msg.back"> </fmt:message>">
</form>
</html>
