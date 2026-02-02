<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Prijatelji</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>
<body class="p-4">
	<div class="container">
		<c:if test="${not empty msg}">
			<div class="alert alert-success">${msg}</div>
		</c:if>
		<c:if test="${not empty err}">
			<div class="alert alert-danger">${err}</div>
		</c:if>

		<div class="d-flex justify-content-between align-items-center mb-3">
			<h2 class="mb-0">Prijatelji</h2>
			<a class="btn btn-link" href="/">←
				Početna</a>
		</div>

		<!-- Slanje zahteva po username-u -->
		<form method="post"
			action="/friends/request"
			class="row g-2 mb-4">
			<div class="col-auto">
				<input class="form-control" type="text" name="username"
					placeholder="Korisničko ime..." required />
			</div>
			<div class="col-auto">
				<button class="btn btn-primary">Pošalji zahtev</button>
			</div>
		</form>

		<div class="row g-4">
			<!-- Moji prijatelji -->
			<div class="col-12 col-lg-4">
				<h5 class="mb-2">Moji prijatelji</h5>
				<c:if test="${empty friends}">
					<div class="alert alert-light border small">Nema prijatelja.</div>
				</c:if>
				<ul class="list-group">
					<c:forEach var="u" items="${friends}">
						<li
							class="list-group-item d-flex justify-content-between align-items-center">
							<span>${u.username}</span> <a
							class="btn btn-sm btn-outline-secondary"
							href="/messages/${u.id}">
								Poruke </a>
						</li>
					</c:forEach>
				</ul>
			</div>

			<!-- Pristigli zahtevi -->
			<div class="col-12 col-lg-4">
				<h5 class="mb-2">Pristigli zahtevi</h5>
				<c:if test="${empty pendingForMe}">
					<div class="alert alert-light border small">Nema pristiglih
						zahteva.</div>
				</c:if>
				<div>
					<c:forEach var="f" items="${pendingForMe}">
						<div
							class="border rounded p-2 d-flex justify-content-between align-items-center mb-2">
							<div>
								<div class="fw-semibold">${f.sender.username}</div>
								<div class="small text-muted">želi da te doda</div>
							</div>
							<div class="d-flex gap-2">
								<form method="post"
									action="/friends/${f.id}/accept">
									<button class="btn btn-sm btn-success">Prihvati</button>
								</form>
								<form method="post"
									action="/friends/${f.id}/decline">
									<button class="btn btn-sm btn-outline-danger">Odbij</button>
								</form>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

			<!-- Poslati zahtevi -->
			<div class="col-12 col-lg-4">
				<h5 class="mb-2">Moji poslati zahtevi</h5>
				<c:if test="${empty myPending}">
					<div class="alert alert-light border small">Nema poslatih
						zahteva.</div>
				</c:if>
				<ul class="list-group">
					<c:forEach var="f" items="${myPending}">
						<li class="list-group-item d-flex justify-content-between"><span>${f.receiver.username}</span>
							<span class="badge text-bg-warning">PENDING</span></li>
					</c:forEach>
				</ul>
			</div>
		</div>

	</div>
</body>
</html>
