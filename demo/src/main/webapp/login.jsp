<%@ page contentType="text/html;charset=UTF-8"%>
<html><head>
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head><body class="p-4">
<div class="container" style="max-width:480px">
  <h2>Login</h2>

  <form method="post" action="${pageContext.request.contextPath}/login">
    <div class="mb-3">
      <label class="form-label">Username</label>
      <input class="form-control" name="username" required/>
    </div>
    <div class="mb-3">
      <label class="form-label">Password</label>
      <input class="form-control" type="password" name="password" required/>
    </div>
    <button class="btn btn-primary">Login</button>
    <a class="btn btn-link" href="${pageContext.request.contextPath}/register">Create account</a>
  </form>
</div>
</body></html>
