
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en' }"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<fmt:requestEncoding value="UTF-8"/>

<html lang="${language}">
<head>
    <title>Submit</title>
</head>
<body>
<h1><fmt:message
        key="msg.areYouSureYouWant"> </fmt:message></h1> <br/>

<form action="CardBlock" method="post">
    <input type="submit" value="<fmt:message
                key="msg.yes"> </fmt:message>">
    <input type="hidden" name="action" value="block">
</form>

<form action="${pageContext.request.contextPath}/HomePage">
    <input type="submit" value="<fmt:message
                key="msg.no"> </fmt:message>">
</form>
</body>
</html>
