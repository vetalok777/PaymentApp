<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<div align="center">
    <h1>User Register Form</h1>
    <form action="<%= request.getContextPath() %>/register" method="post">
        <table style="width: 80%">
            <tr>
                <td>username</td>
                <td><input type="text" name="username" /></td>
            </tr>
            <tr>
                <td>password</td>
                <td><input type="password" name="password" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
        </form>
</div>
</body>
</html>