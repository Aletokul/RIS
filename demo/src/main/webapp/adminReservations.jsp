<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Admin – Pending rezervacije</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body class="p-4">
	<a class="btn btn-link mb-3" href="/">← Početna</a>
	<h3 class="mb-3">Pending rezervacije</h3>

	<c:choose>
		<c:when test="${empty pending}">
			<div class="alert alert-info">Nema pending rezervacija.</div>
		</c:when>
		<c:otherwise>
			<table class="table table-striped align-middle">
				<thead>
					<tr>
						<th>ID</th>
						<th>Korisnik</th>
						<th>Teren</th>
						<th>Datum</th>
						<th>Vreme</th>
						<th>Ukupno</th>
						<th>Akcije</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="r" items="${pending}">
						<tr>
							<td>${r.id}</td>
							<td>${r.user.username}</td>
							<td>${r.field.name}</td>
							<td>${r.date}</td>
							<td>${r.startTime}–${r.endTime}</td>
							<td>${r.totalPrice}</td>
							<td class="d-flex gap-2">
								<form method="post"
									action="/reservations/${r.id}/approve">
									<button class="btn btn-success btn-sm">Potvrdi</button>
								</form>
								<form method="post"
									action="$/reservations/${r.id}/reject">
									<button class="btn btn-outline-danger btn-sm">Odbij</button>
								</form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>
