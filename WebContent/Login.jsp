<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
</head>
<body>
    <div class="container">
        <div class="card">
            <form action="LoginServlet" method="post">
                <div class="form-input">
                    <label for="uname">Username: </label>
                    <input type="text" name="uname" id="uname" required>
                </div>
                <div class="form-input">
                    <label for="pass">Password: </label>
                    <input type="password" name="pass" id="pass" required>
                </div>
                <div class="btn">
                    <button type="submit">Login</button>
                </div>
            </form>
            <div class="reg">
                <span>Not a User? <a href="register">SignUp</a></span>
            </div>
        </div>
    </div>
</body>
</html>