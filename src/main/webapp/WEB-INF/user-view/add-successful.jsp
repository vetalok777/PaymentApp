
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
    <title>Successfully</title>
</head>
<body>
    <h1><fmt:message
            key="msg.addingIsSuccess"> </fmt:message>: <%=request.getAttribute("number")%></h1>

    <form action="${pageContext.request.contextPath}/HomePage">
        <input type="submit" value="<fmt:message
            key="msg.back"> </fmt:message>">
    </form>
</body>
</html>
