<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
        <title>LogIn</title>
</head>
<body>
<div align="center">
    <h1>Registration</h1>
    <form action="<%= request.getContextPath() %>/SignUp" method="post">
        <table style="width: 30%">
            <tr>
                <td>firstName</td>
                <td><input type="text" name="firstname"/></td>
            </tr>
            <tr>
                <td>email</td>
                <td><input type="text" name="email"/></td>
            </tr>
            <tr>
                <td>password</td>
                <td><input type="password" name="password"/></td>
            </tr>
        </table>
        <input type="submit" value="Submit"/>
        <br>
        <br>
        <a href="/PaymentApp">Home Page</a>
    </form>
</div>
</body>
</html>