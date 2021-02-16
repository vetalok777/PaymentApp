
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successfully</title>
</head>
<body>
    <h1>Your payment is in process. Check it in your payments.</h1>

    <form action="PaymentDetails" >
        <input type="hidden" name="currentPage" value="1">
        <input type="hidden" name="sorting" value="byStatus">
        <input type="submit" value="My payments">
    </form>
</body>
</html>
