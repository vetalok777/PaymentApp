
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<div align="center">
    <h1>User Authorization</h1>
    <form action="<%= request.getContextPath() %>/Authorization" method="post">
        <table style="width: 30%">
            <tr>
                <td>username</td>
                <td><input type="text" name="username"/></td>
            </tr>
            <tr>
                <td>password</td>
                <td><input type="password" name="password"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit"/>
    </form>
</div>
</body>
</html>
