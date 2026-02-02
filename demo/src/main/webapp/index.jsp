<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"/>
  <title>Home</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="p-4" style="background: url('/images/image.jpg'); background-size: cover;">
<style>
    body {
      min-height: 80vh; /* zauzima celu visinu ekrana */
      display: flex;
      flex-direction: column;
      justify-content: center; /* centriraj po vertikali */
      align-items: center;
    }

    h1, p {
      color: white;
      text-shadow: 2px 2px 8px rgba(0,0,0,0.7); /* da se bolje vidi preko slike */
    }
  </style>
<div class="container text-center mt-5">
  <h1>Dobrodošli na sajt za rezervaciju terena!</h1>
  <p class="mb-4">Prijavite se da biste rezervisali teren ili pogledali vaše rezervacije.</p>


  <c:choose>
    <%-- NIJE ULOGOVAN --%>
    <c:when test="${empty sessionScope.user}">
      <a class="btn btn-success me-2" href="/login">Uloguj se</a>
      <a class="btn btn-success me-2" href="/register">Registruj se</a>
      <a class="btn btn-success" href="/fields">Pogledaj terene</a>
    </c:when>

    <%-- ULOGOVAN --%>
    <c:otherwise>
      <%-- ADMIN --%>
      <c:if test="${sessionScope.user.role eq 'ADMIN'}">
        <a class="btn btn-warning me-2" href="/fields/new">Dodaj novi teren</a>
        <a class="btn btn-warning me-2" href="/fields">Pogledaj terene</a>
        <a class="btn btn-warning me-2" href="/reservations/admin/pending">Potvrda rezervacije</a>
        <a class="btn btn-warning me-2" href="/admin/reports/reservations/pdf">Dnevna statistika rezervacija</a>
      </c:if>

      <%-- USER (svako ko nije ADMIN) --%>
      <c:if test="${sessionScope.user.role ne 'ADMIN'}">
        <a class="btn btn-success me-2" href="/reservations/mine">Moje rezervacije</a>
        <a class="btn btn-success me-2" href="/fields">Pogledaj terene</a>
        <a class="btn btn-success me-2" href="/friends">Prijatelji</a>
      </c:if>

      <a class="btn btn-secondary" href="/logout">Odjavi se</a>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
