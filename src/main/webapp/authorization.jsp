
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Authorization</title>
</head>
<body>
<div align="center">
    <h1>Sign In</h1>
    <form action="<%= request.getContextPath() %>/SignIn" method="post">
        <table style="width: 30%">
            <tr>
                <td>username</td>
                <td><input type="text" name="email"/></td>
            </tr>
            <tr>
                <td>password</td>
                <td><input type="password" name="password"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit"/>
    </form>
    <h1>New here?</h1>
    <a href="/PaymentApp/SignUp">Create an account</a>
</div>
</body>
</html>
