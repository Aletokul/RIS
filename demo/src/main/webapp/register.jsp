<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Register</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="p-4">

<div class="container" style="max-width:560px;">
  <h2 class="mb-3">Napravi nalog</h2>

  <form method="post" action="/register" novalidate>
    <div class="mb-3">
      <label class="form-label">Username</label>
      <input class="form-control" name="username" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Email</label>
      <input class="form-control" type="email" name="email" required/>

    </div>

    <div class="mb-3">
      <label class="form-label">Password</label>
      <input class="form-control" type="password" name="password" minlength="4" required/>
    </div>

    <div class="mb-3">
      <label class="form-label">Confirm password</label>
      <input class="form-control" type="password" name="confirm" minlength="4" required/>
    </div>

    <div class="d-flex gap-2">
      <button class="btn btn-success" type="submit">Register</button>
      <a class="btn btn-secondary" href="/">Cancel</a>
    </div>
  </form>
</div>

</body>
</html>
