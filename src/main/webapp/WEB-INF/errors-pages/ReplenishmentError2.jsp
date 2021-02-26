<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<html lang="${language}">
<html>
<head>
    <title>ReplenishmentError</title>
</head>
<body>
<h1><fmt:message
        key="msg.cardYouAre"> </fmt:message></h1>
<form action="${pageContext.request.contextPath}/HomePage">
    <input type="submit" value="<fmt:message
                key="msg.back"> </fmt:message>">
</form>

</body>
</html>

