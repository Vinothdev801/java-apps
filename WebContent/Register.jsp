<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Register Page</title>
  </head>
  <body>
    <div class="container">
      <div class="card">
        <form action="reg" method="post">
          <div class="form-input">
            <label for="uname">Username: </label>
            <input type="text" name="uname" id="uname" minlength="5" required />
          </div>

          <div class="form-input">
            <label for="email">Email: </label>
            <input type="email" name="email" id="email" required />
          </div>

          <div class="form-input">
            <label for="pass">Password: </label>
            <input type="password" name="pass" id="pass" />
          </div>

          <div class="sub">
            <button type="submit">Register</button>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
