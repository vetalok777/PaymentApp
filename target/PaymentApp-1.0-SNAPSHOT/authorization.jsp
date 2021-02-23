<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .login-form {
            width: 340px;
            margin: 50px auto;
        }

        .login-form form {
            margin-bottom: 15px;
            background: #f7f7f7;
            box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }

        .login-form h2 {
            margin: 0 0 15px;
        }

        .form-control, .btn {
            min-height: 38px;
            border-radius: 2px;
        }

        .btn {
            font-size: 15px;
            font-weight: bold;
        }
    </style>

    <title>Authorization</title>
</head>
<body>
<div class="login-form">
    <form action="<%= request.getContextPath() %>/SignIn" method="post">
        <h2 class="text-center">Log in</h2>

        <div class="form-group">
            <input type="text" class="form-control" name="email" placeholder="Username"/>
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="password" placeholder="Password"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Log in</button>
        </div>
    </form>
    <h1 class="text-center">New here?</h1>
    <p class="text-center"><a href="/PaymentApp/SignUp">Create an account</a></p>
</div>
</body>
</html>
