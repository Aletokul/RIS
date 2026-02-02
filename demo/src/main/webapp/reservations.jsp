<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
  <title>Moje rezervacije</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="p-4">
  <h2>Moje rezervacije</h2>


  <div class="text-muted small mb-2">Ukupno: <c:out value="${fn:length(reservations)}"/></div>

  <c:choose>
    <c:when test="${empty reservations}">
      <div class="alert alert-info">Nema rezervacija.</div>
    </c:when>
    <c:otherwise>
      <table class="table table-striped align-middle">
        <thead>
          <tr>
            <th>Datum</th>
            <th>Vreme</th>
            <th>Teren</th>
            <th>Status</th>
            <th>Ukupno</th>
            <th>Akcija</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="r" items="${reservations}">
            <tr>
              <td>${r.date}</td>
              <td>${r.startTime}–${r.endTime}</td>
              <td>${r.field.name}</td>
              <td>
                <span class="badge
                  ${r.status.name() == 'CONFIRMED' ? 'bg-success'
                    : (r.status.name() == 'PENDING' ? 'bg-warning text-dark' : 'bg-secondary')}">
                  ${r.status}
                </span>
              </td>
              <td>${r.totalPrice}</td>
              <td>
                <!-- Prikazi "Otkaži" ako još nije CANCELLED -->
                <c:if test="${r.status.name() ne 'CANCELLED'}">
                  <form method="post"
                        action="/reservations/${r.id}/cancel"
                        onsubmit="return confirm('Da li ste sigurni da želite da otkažete rezervaciju?');">
                    <button class="btn btn-sm btn-outline-danger">Otkaži</button>
                  </form>
                </c:if>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>
</body>
</html>
